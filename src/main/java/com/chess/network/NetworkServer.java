package com.chess.network;

import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.util.*;

import com.chess.utils.Delegate;

/**
 * A TCP server.
 * 
 * The server can be configured to listen on a specific port and which ip to listen on.
 */
public class NetworkServer {
    private int port;
    private String ip;
    private ServerSocket serverSocket;
    private boolean running;
    private Thread acceptionThread;
    private List<Client> clients;
    private Delegate<Client> onClientConnectedDelegate;
    private Delegate<Client> onClientDisconnectedDelegate;
    private HashMap<Type, MessageDelegate> messageDelegates;

    /**
     * Creates a new server.
     * 
     * @param port The port to listen on.
     * @param ip The ip to listen on.
     */
    public NetworkServer(int port, String ip) {
        this.port = port;
        this.ip = ip;

        running = false;
        clients = new ArrayList<>();
        messageDelegates = new HashMap<>();
    }

    /**
     * Creates a new server.
     * 
     * @param ip The ip to listen on.
     */
    public NetworkServer(int port) {
        this.port = port;
        this.ip = null;

        running = false;
        clients = new ArrayList<>();
        messageDelegates = new HashMap<>();
    }

    /**
     * Starts the server.
     * 
     * @throws IOException
     */
    public void start() throws IOException {
        // Allows for running on a more restrictive network.
        if (ip == null) {
            serverSocket = new ServerSocket(port);
        } else {
            serverSocket = new ServerSocket(port, 0, InetAddress.getByName(ip));
        }
        
        running = true;

        acceptionThread = new Thread(() -> acceptLoop());

        acceptionThread.start();
    }

    /**
     * Stops the server.
     */
    public synchronized void stop() {
        for (Client client : clients) {
            client.stop();
        }

        running = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the delegate that will be called when a client connects.
     */
    public synchronized void setOnClientConnectedDelegate(Delegate<Client> onClientConnectedDelegate) {
        this.onClientConnectedDelegate = onClientConnectedDelegate;
    }

    /**
     * Sets the delegate that will be called when a client disconnects.
     */
    public synchronized void setOnClientDisconnectedDelegate(Delegate<Client> onClientDisconnectedDelegate) {
        this.onClientDisconnectedDelegate = onClientDisconnectedDelegate;
    }

    /**
     * Sets the delegate that will be called when a message is received.
     * 
     * @param type The type of message to listen for. Use <Message>.class.
     * @param delegate The delegate to call when a message of the specified type is received.
     */
    public synchronized void setMessageDelegate(Type type, MessageDelegate messageDelegate) {
        messageDelegates.put(type, messageDelegate);
    }

    /**
     * Sets the delegate that will be called when a message is received, not including the client that sent it.
     * 
     * @param type The type of message to listen for. Use <Message>.class.
     * @param delegate The delegate to call when a message of the specified type is received.
     */
    public synchronized void setMessageDelegate(Type type, Delegate<Message> messageDelegate) {
        messageDelegates.put(type, (client, message) -> messageDelegate.invoke(message));
    }

    /**
     * Sends a message to all clients.
     * 
     * @param message The message to send.
     */
    public synchronized void broadcastMessage(Message message) {
        for (Client client : clients) {
            client.sendMessage(message);
        }
    }

    /**
     * Close a client.
     * 
     * @param client The client to close.
     */
    public synchronized void closeClient(Client client) {
        client.stop();
    }

    /**
     * Handle an incoming message.
     * 
     * @param client The client that sent the message.
     * @param message The message.
     */
    private synchronized void handleMessage(Client client, Message message) {
        // Check if a delegate is registered for this message type.
        if (messageDelegates.containsKey(message.getClass())) {
            // Invoke the delegate.
            messageDelegates.get(message.getClass()).invoke(client, message);
        }
    }

    /**
     * Handle a disconnection.
     * 
     * @param client The client that disconnected.
     */
    private synchronized void handleDisconnect(Client client) {
        clients.remove(client);
        if (onClientDisconnectedDelegate != null) {
            onClientDisconnectedDelegate.invoke(client);
        }
    }

    /**
     * Loop to accept new clients.
     */
    private void acceptLoop() {
        // Run while the server is running.
        while (running) {
            try {
                // Accept the socket. This blocks until a client connects.
                Socket socket = serverSocket.accept();
                
                // Create a new client.
                Client client = new Client(socket);

                // Setup handling of incoming messages for this client.
                client.setMessageDelegate(message -> handleMessage(client, message));
                
                // Setup handling of disconnection for this client.
                client.setOnDisconnectDelegate((c) -> handleDisconnect(c));

                // The rest has to be done synchronously
                synchronized(this) {
                    // Start the client thread.
                    client.start();

                    // Add the client to the list of clients.
                    clients.add(client);

                    // Invoke the delegate for new connections.
                    if (onClientConnectedDelegate != null) {
                        onClientConnectedDelegate.invoke(client);
                    }
                }
            } catch (IOException e) {
                running = false;
                e.printStackTrace();
            }
        }

        // Stop the server.
        stop();
    }
}
