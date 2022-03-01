package com.chess.network;

import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.util.*;

import com.chess.utils.Delegate;

/**
 * A TCP client.
 * 
 * The client can be configured to connect to a specific ip and port.
 */
public class NetworkClient {
    /**
     * The port we are connected to.
     */
    private int port;

    /**
     * The ip we are connected to.
     */
    private String ip;

    /**
     * The socket used to communicate with the server.
     */
    private Socket socket;

    /**
     * The input stream used to read messages from the server.
     */
    private InputStream in;

    /**
     * The output stream used to send messages to the server.
     */
    private OutputStream out;

    /**
     * The thread used to listen for messages from the server.
     */
    private Thread messageThread;

    /**
     * Indicates if the receiver thread is running and we are connected to the server.
     */
    private boolean running;

    /**
     * Callback for when we are disconnected from the server.
     */
    private Runnable onDisconnectDelegate;

    /**
     * HashMap of delegates for each message type.
     * 
     * The key is the type of the message, the value is the delegate to call when we receive a message of that type.
     */
    private HashMap<Type, Delegate<Message>> messageDelegates;

    /**
     * Creates a new client.
     * 
     * @param ip The ip to connect to.
     * @param port The port to connect to.
     */
    public NetworkClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.messageDelegates = new HashMap<>();
    }

    /**
     * Creates a new client from host details.
     * 
     * @param host The host to connect to.
     */
    public NetworkClient(HostDetails host) {
        this(host.getIp(), host.getPort());
    }

    /**
     * Send a message to the server.
     * 
     * @param message The message to send.
     */
    public void sendMessage(Message message) {
        try {
            // Create an object output stream
            ObjectOutputStream objectStream = new ObjectOutputStream(out);
            
            // Write the message
            objectStream.writeObject(message);

            // Flush the stream
            objectStream.flush();
        } catch (SocketException e) {
            // The socket was closed
            stop();
        } catch (IOException e) {
            stop();
            e.printStackTrace();
        }
    }

    /**
     * Sets the delegate that will be called when a message is received.
     * 
     * @param type The type of message to listen for.
     * @see com.chess.network.Message
     * @param messageDelegate The delegate to call when a message of the specified type is received.
     */
    public synchronized void setMessageDelegate(Type type, Delegate<Message> messageDelegate) {
        messageDelegates.put(type, messageDelegate);
    }

    /**
     * Sets the delegate that will be called when the client disconnects.
     * 
     * @param delegate The delegate to call when the client disconnects.
     */
    public void setOnDisconnectDelegate(Runnable delegate) {
        onDisconnectDelegate = delegate;
    }

    /**
     * Stop the client.
     * 
     * This will disconnect the client from the server if it is connected.
     */
    public void stop() {
        // If we are not running, return
        if (!running) {
            return;
        }

        // Set running to false, this will safely stop the receive loop
        running = false;

        // Attempt to close the socket
        try {
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            // Failed to close the socket, not much we can do about it
        }

        socket = null;
        
        // Run the disconnect delegate
        if (onDisconnectDelegate != null) {
            onDisconnectDelegate.run();
        }
    }

    /**
     * Start the client. This will start a receive loop that will receive messages from the server.
     * 
     * @throws IOException If the client could not connect to the server.
     */
    public void start() throws IOException {        
        // Connect to the server
        socket = new Socket(ip, port);

        // Collect the streams
        in = socket.getInputStream();
        out = socket.getOutputStream();
        running = true;

        // Start the message thread.
        messageThread = new Thread(() -> receiveLoop());
        messageThread.start();
    }

    /**
     * Loop that reads messages from the server and calls the delegates.
     */
    private void receiveLoop() {
        while (running) {
            try {
                // Create an object input stream
                ObjectInputStream objectStream = new ObjectInputStream(in);

                // Read the message
                Message message = (Message) objectStream.readObject();

                if (message == null) {
                    break;
                }

                // Call the delegate
                synchronized (this) {
                    System.out.println("Received message: " + message.getClass().getName());
    
                    if (messageDelegates.containsKey(message.getClass())) {
                        messageDelegates.get(message.getClass()).trigger(message);
                    }
                }

            } catch (SocketException e) {
                // Disconnected from the server
                break;
            } catch (IOException e) {
                // Here we should also disconnect from the server, can happen when the server is closed
                break;
            } catch (ClassNotFoundException e) {
                // Something went wrong, disconnect from the server
                e.printStackTrace();
                break;
            }
        }

        // Stop the client
        stop();
    }
}
