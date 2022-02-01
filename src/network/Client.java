package network;

import java.io.*;
import java.net.*;

import utils.Delegate;

/**
 * Class representing a client communicating with our server.
 */
public class Client {
    private Socket _socket;
    private InputStream _in;
    private OutputStream _out;
    private Thread _messageThread;
    private boolean _running;
    private Delegate<Message> _messageDelegate;
    private Delegate<Client> _onDisconnectDelegate;

    /**
     * Creates a new client.
     * 
     * @param socket The socket to use for communication.
     */
    public Client(Socket socket) {
        _socket = socket;
        try {
            // Collect the streams
            _in = _socket.getInputStream();
            _out = _socket.getOutputStream();
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
            ObjectOutputStream out = new ObjectOutputStream(_out);

            // Write the message
            out.writeObject(message);

            // Flush the stream
            out.flush();
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
        _messageDelegate = messageDelegate;
    }

    /**
     * Sets the delegate that will be called when the client disconnects.
     * 
     * @param onDisconnectDelegate The delegate to call when the client disconnects.
     */
    public void setOnDisconnectDelegate(Delegate<Client> onDisconnectDelegate) {
        _onDisconnectDelegate = onDisconnectDelegate;
    }

    /**
     * Starts the client.
     */
    public void start() {
        _running = true;

        // Start the thread that will receive messages
        _messageThread = new Thread(() -> receiveLoop());
        _messageThread.start();
    }

    /**
     * Stops the client.
     */
    public void stop() {
        _running = false;

        try {
            // If the client is still connected, close the socket
            if (!_socket.isClosed()) {
                _socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Call the disconnect delegate
        if (_onDisconnectDelegate != null) {
            _onDisconnectDelegate.invoke(this);
        }
    }
    
    /**
     * Loop that receives messages from the client.
     */
    private void receiveLoop() {
        while (_running) {
            try {
                // Create an object input stream
                ObjectInputStream in = new ObjectInputStream(_in);

                // Read the message
                Message message = (Message) in.readObject();

                // If the message is null, the client has disconnected
                if (message == null) {
                    break;
                }

                // If there is a delegate for this message type, call it
                if (_messageDelegate != null) {
                    _messageDelegate.invoke(message);
                }

            } catch (SocketException e) {
                // The socket was closed
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                break;
            }
        }

        // Close the client
        stop();
    }
}
