package network;

import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.util.*;

import utils.Delegate;

/**
 * A TCP client.
 * 
 * The client can be configured to connect to a specific ip and port.
 */
public class NetworkClient {
    private int port;
    private String ip;
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private Thread messageThread;
    private boolean running;
    private Runnable onDisconnectDelegate;
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
     * @param type The type of message to listen for. Use <Message>.class.
     * @param delegate The delegate to call when a message of the specified type is received.
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

    public void stop() {
        running = false;

        try {
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (onDisconnectDelegate != null) {
            onDisconnectDelegate.run();
        }
    }

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
                        messageDelegates.get(message.getClass()).invoke(message);
                    }
                }

            } catch (SocketException e) {
                // Disconnected from the server
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                break;
            }
        }

        // Stop the client
        stop();
    }
}
