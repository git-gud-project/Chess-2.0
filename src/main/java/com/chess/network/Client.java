package com.chess.network;

import java.io.*;
import java.net.*;

import com.chess.utils.Delegate;

/**
 * Class representing a client communicating with our server.
 */
public class Client {
    /**
     * The socket used to communicate with the client.
     */
    private Socket socket;

    /**
     * The input stream used to read messages from the client.
     */
    private InputStream in;

    /**
     * The output stream used to send messages to the client.
     */    
    private OutputStream out;

    /**
     * The thread used to listen for messages from the client.
     */
    private Thread messageThread;

    /**
     * Indicates if the receiver thread is running and we are connected to the client.
     */
    private boolean running;

    /**
     * The delegate to call when the server receives a message from the client.
     */
    private Delegate<Message> messageDelegate;

    /**
     * The delegate to call when the client disconnects.
     */
    private Delegate<Client> onDisconnectDelegate;

    /**
     * Creates a new client.
     * 
     * @param socket The socket to use for communication.
     */
    public Client(Socket socket) {
        this.socket = socket;
        try {
            // Collect the streams
            in = socket.getInputStream();
            out = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a message to the client.
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
            e.printStackTrace();
        } 
    }

    /**
     * Sets the delegate that will be called when a message is received.
     * 
     * @param messageDelegate The delegate to call when a message is received.
     */
    protected synchronized void setMessageDelegate(Delegate<Message> messageDelegate) {
        this.messageDelegate = messageDelegate;
    }

    /**
     * Sets the delegate that will be called when the client disconnects.
     * 
     * @param onDisconnectDelegate The delegate to call when the client disconnects.
     */
    protected synchronized void setOnDisconnectDelegate(Delegate<Client> onDisconnectDelegate) {
        this.onDisconnectDelegate = onDisconnectDelegate;
    }

    /**
     * Starts the client.
     * 
     * This will start a thread that will listen for messages from the client.
     */
    protected void start() {
        running = true;

        // Start the thread that will receive messages
        messageThread = new Thread(() -> receiveLoop());
        messageThread.start();
    }

    /**
     * Stops the client.
     */
    protected void stop() {
        // If we are not running, return
        if (!running) {
            return;
        }

        running = false;

        try {
            // If the client is still connected, close the socket
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Call the disconnect delegate
        if (onDisconnectDelegate != null) {
            onDisconnectDelegate.trigger(this);
        }
    }
    
    /**
     * Loop that receives messages from the client.
     */
    private void receiveLoop() {
        while (running) {
            try {
                // Create an object input stream
                ObjectInputStream objectStream = new ObjectInputStream(in);

                // Read the message
                Message message = (Message) objectStream.readObject();

                // If the message is null, the client has disconnected
                if (message == null) {
                    break;
                }

                // If there is a delegate for this message type, call it
                if (messageDelegate != null) {
                    messageDelegate.trigger(message);
                }

            } catch (SocketException e) {
                // The socket was closed
                break;
            } catch (IOException e) {
                // Can occur when the client disconnects, close it
                break;
            } catch (ClassNotFoundException e) {
                // Something went wrong, client out of date, close it
                e.printStackTrace();
                break;
            }
        }

        // Close the client
        stop();
    }
}
