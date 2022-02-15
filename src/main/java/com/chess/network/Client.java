package com.chess.network;

import java.io.*;
import java.net.*;

import com.chess.utils.Delegate;

/**
 * Class representing a client communicating with our server.
 */
public class Client {
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private Thread messageThread;
    private boolean running;
    private Delegate<Message> messageDelegate;
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
    public void setMessageDelegate(Delegate<Message> messageDelegate) {
        this.messageDelegate = messageDelegate;
    }

    /**
     * Sets the delegate that will be called when the client disconnects.
     * 
     * @param onDisconnectDelegate The delegate to call when the client disconnects.
     */
    public void setOnDisconnectDelegate(Delegate<Client> onDisconnectDelegate) {
        this.onDisconnectDelegate = onDisconnectDelegate;
    }

    /**
     * Starts the client.
     */
    public void start() {
        running = true;

        // Start the thread that will receive messages
        messageThread = new Thread(() -> receiveLoop());
        messageThread.start();
    }

    /**
     * Stops the client.
     */
    public void stop() {
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
            onDisconnectDelegate.invoke(this);
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
                    messageDelegate.invoke(message);
                }

            } catch (SocketException e) {
                // The socket was closed
                break;
            } catch (IOException e) {
                // Can occur when the client disconnects, close it
                break;
            } catch (ClassNotFoundException e) {
                // Something went wrong, close the socket
                e.printStackTrace();
                break;
            }
        }

        // Close the client
        stop();
    }
}
