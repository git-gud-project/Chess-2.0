package network;

import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.util.*;

import utils.Delegate;

/**
 * A TCP server.
 * 
 * The server can be configured to listen on a specific port and which ip to listen on.
 */
public class NetworkServer {
    private int _port;
    private String _ip;
    private ServerSocket _serverSocket;
    private boolean _running;
    private Thread _acceptionThread;
    private List<Client> _clients;
    private Delegate<Client> _onClientConnectedDelegate;
    private Delegate<Client> _onClientDisconnectedDelegate;
    private HashMap<Type, MessageDelegate> _messageDelegates;

    /**
     * Creates a new server.
     * 
     * @param port The port to listen on.
     * @param ip The ip to listen on.
     */
    public NetworkServer(int port, String ip) {
        _port = port;
        _ip = ip;

        _running = false;
        _clients = new ArrayList<>();
        _messageDelegates = new HashMap<>();
    }

    /**
     * Creates a new server.
     * 
     * @param ip The ip to listen on.
     */
    public NetworkServer(int port) {
        _port = port;
        _ip = null;

        _running = false;
        _clients = new ArrayList<>();
        _messageDelegates = new HashMap<>();
    }

    /**
     * Starts the server.
     * 
     * @throws IOException
     */
    public void start() throws IOException {
        // Allows for running on a more restrictive network.
        if (_ip == null) {
            _serverSocket = new ServerSocket(_port);
        } else {
            _serverSocket = new ServerSocket(_port, 0, InetAddress.getByName(_ip));
        }
        
        _running = true;

        _acceptionThread = new Thread(() -> acceptLoop());

        _acceptionThread.start();
    }

    /**
     * Stops the server.
     */
    public synchronized void stop() {
        for (Client client : _clients) {
            client.stop();
        }

        _running = false;
        try {
            _serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the delegate that will be called when a client connects.
     */
    public synchronized void setOnClientConnectedDelegate(Delegate<Client> onClientConnectedDelegate) {
        _onClientConnectedDelegate = onClientConnectedDelegate;
    }

    /**
     * Sets the delegate that will be called when a client disconnects.
     */
    public synchronized void setOnClientDisconnectedDelegate(Delegate<Client> onClientDisconnectedDelegate) {
        _onClientDisconnectedDelegate = onClientDisconnectedDelegate;
    }

    /**
     * Sets the delegate that will be called when a message is received.
     * 
     * @param type The type of message to listen for. Use <Message>.class.
     * @param delegate The delegate to call when a message of the specified type is received.
     */
    public synchronized void setMessageDelegate(Type type, MessageDelegate messageDelegate) {
        _messageDelegates.put(type, messageDelegate);
    }

    /**
     * Sets the delegate that will be called when a message is received, not including the client that sent it.
     * 
     * @param type The type of message to listen for. Use <Message>.class.
     * @param delegate The delegate to call when a message of the specified type is received.
     */
    public synchronized void setMessageDelegate(Type type, Delegate<Message> messageDelegate) {
        _messageDelegates.put(type, (client, message) -> messageDelegate.invoke(message));
    }

    /**
     * Sends a message to all clients.
     * 
     * @param message The message to send.
     */
    public synchronized void broadcastMessage(Message message) {
        for (Client client : _clients) {
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
        if (_messageDelegates.containsKey(message.getClass())) {
            // Invoke the delegate.
            _messageDelegates.get(message.getClass()).invoke(client, message);
        }
    }

    /**
     * Handle a disconnection.
     * 
     * @param client The client that disconnected.
     */
    private synchronized void handleDisconnect(Client client) {
        _clients.remove(client);
        if (_onClientDisconnectedDelegate != null) {
            _onClientDisconnectedDelegate.invoke(client);
        }
    }

    /**
     * Loop to accept new clients.
     */
    private void acceptLoop() {
        // Run while the server is running.
        while (_running) {
            try {
                // Accept the socket. This blocks until a client connects.
                Socket socket = _serverSocket.accept();
                
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
                    _clients.add(client);

                    // Invoke the delegate for new connections.
                    if (_onClientConnectedDelegate != null) {
                        _onClientConnectedDelegate.invoke(client);
                    }
                }
            } catch (IOException e) {
                _running = false;
                e.printStackTrace();
            }
        }

        // Stop the server.
        stop();
    }
}
