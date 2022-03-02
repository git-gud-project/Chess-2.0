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
    /**
     * The port we are listening on.
     */
    private int port;

    /**
     * The ip we are listening on.
     */
    private String ip;

    /**
     * The server socket used to listen for connections.
     */
    private ServerSocket serverSocket;

    /**
     * Indicates if the server is listening for connections.
     */
    private boolean running;

    /**
     * The thread used to listen for connections.
     */
    private Thread acceptionThread;

    /**
     * List of connected clients.
     */
    private List<Client> clients;

    /**
     * Delegete for when a client connects.
     */
    private Delegate<Client> onClientConnectedDelegate;

    /**
     * Delegate for when a client disconnects.
     */
    private Delegate<Client> onClientDisconnectedDelegate;
    
    /**
     * Delegete for when the server closes.
     */
    private Runnable onCloseDelegate;

    /**
     * HashMap of delegates for each message type.
     * 
     * The key is the type of the message, the value is the delegate to call when we receive a message of that type.
     */
    private HashMap<Type, MessageDelegate> messageDelegates;

    /**
     * Creates a new server.
     * 
     * @param port The port to listen on.
     * @param ip The ip to listen on.
     */
    public NetworkServer(String ip, int port) {
        this.port = port;
        this.ip = ip;

        running = false;
        clients = new ArrayList<>();
        messageDelegates = new HashMap<>();
    }

    /**
     * Creates a new server from host details.
     * 
     * @param host The host to connect to.
     */
    public NetworkServer(HostDetails host) {
        this(host.getIp(), host.getPort());
    }

    /**
     * Starts the server.
     * 
     * @throws IOException Can be thrown when opening a socket.
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
        if (!running) {
            return;
        }

        // Copy the clients list
        List<Client> clientsSnapshot = new ArrayList<>(clients);
        for (Client client : clientsSnapshot) {
            client.stop();
        }

        running = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (onCloseDelegate != null) {
            onCloseDelegate.run();
        }
    }

    /**
     * Sets the delegate that will be called when a client connects.
     * @param onClientConnectedDelegate The delegate that will be colled when the client connects.
     */
    public synchronized void setOnClientConnectedDelegate(Delegate<Client> onClientConnectedDelegate) {
        this.onClientConnectedDelegate = onClientConnectedDelegate;
    }

    /**
     * Sets the delegate that will be called when a client disconnects.
     * @param onClientDisconnectedDelegate The delegate that will be called when a client disconnects.
     */
    public synchronized void setOnClientDisconnectedDelegate(Delegate<Client> onClientDisconnectedDelegate) {
        this.onClientDisconnectedDelegate = onClientDisconnectedDelegate;
    }

    /**
     * Sets the delegate that will be called when the server closes.
     * @param onCloseDelegate The delegate that will be called when the server closes.
     */
    public void setOnCloseDelegate(Runnable onCloseDelegate) {
        this.onCloseDelegate = onCloseDelegate;
    }

    /**
     * Sets the delegate that will be called when a message is received.
     * 
     * @param type The type of message to listen for.
     * @param messageDelegate The delegate to call when a message of the specified type is received.
     */
    public synchronized void setMessageDelegate(Type type, MessageDelegate messageDelegate) {
        messageDelegates.put(type, messageDelegate);
    }

    /**
     * Sets the delegate that will be called when a message is received, not including the client that sent it.
     * 
     * @param type The type of message to listen for.
     * @param messageDelegate The delegate to call when a message of the specified type is received.
     */
    public synchronized void setMessageDelegate(Type type, Delegate<Message> messageDelegate) {
        messageDelegates.put(type, (client, message) -> messageDelegate.trigger(message));
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
            messageDelegates.get(message.getClass()).onMessage(client, message);
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
            onClientDisconnectedDelegate.trigger(client);
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
                        onClientConnectedDelegate.trigger(client);
                    }
                }
            } catch (SocketException e) {
                // Server was closed.
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

        // Stop the server.
        stop();
    }
}
