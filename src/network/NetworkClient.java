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
    private int _port;
    private String _ip;
    private Socket _socket;
    private InputStream _in;
    private OutputStream _out;
    private Thread _messageThread;
    private boolean _running;
    private Runnable _onDisconnectDelegate;
    private HashMap<Type, Delegate<Message>> _messageDelegates;

    /**
     * Creates a new client.
     * 
     * @param ip The ip to connect to.
     * @param port The port to connect to.
     */
    public NetworkClient(String ip, int port) {
        _ip = ip;
        _port = port;
        _messageDelegates = new HashMap<>();
    }

    /**
     * Send a message to the server.
     * 
     * @param message The message to send.
     */
    public void sendMessage(Message message) {
        try {
            // Create an object output stream
            ObjectOutputStream out = new ObjectOutputStream(_out);
            
            // Write the message
            out.writeObject(message);

            // Flush the stream
            out.flush();
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
        _messageDelegates.put(type, messageDelegate);
    }

    /**
     * Sets the delegate that will be called when the client disconnects.
     * 
     * @param delegate The delegate to call when the client disconnects.
     */
    public void setOnDisconnectDelegate(Runnable delegate) {
        _onDisconnectDelegate = delegate;
    }

    public void stop() {
        _running = false;

        try {
            if (!_socket.isClosed()) {
                _socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (_onDisconnectDelegate != null) {
            _onDisconnectDelegate.run();
        }
    }

    public void start() throws IOException {        
        // Connect to the server
        _socket = new Socket(_ip, _port);

        // Collect the streams
        _in = _socket.getInputStream();
        _out = _socket.getOutputStream();
        _running = true;

        // Start the message thread.
        _messageThread = new Thread(() -> receiveLoop());
        _messageThread.start();
    }

    /**
     * Loop that reads messages from the server and calls the delegates.
     */
    private void receiveLoop() {
        while (_running) {
            try {
                // Create an object input stream
                ObjectInputStream in = new ObjectInputStream(_in);

                // Read the message
                Message message = (Message) in.readObject();

                if (message == null) {
                    break;
                }

                // Call the delegate
                synchronized (this) {
                    System.out.println("Received message: " + message.getClass().getName());
    
                    if (_messageDelegates.containsKey(message.getClass())) {
                        _messageDelegates.get(message.getClass()).invoke(message);
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
