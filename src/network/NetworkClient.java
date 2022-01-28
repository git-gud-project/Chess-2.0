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
    private Socket _socket;
    private InputStream _in;
    private OutputStream _out;
    private Thread _messageThread;
    private boolean _running;
    private HashMap<Type, Delegate<Message>> _messageDelegates;

    public NetworkClient(String ip, int port) {
        try {
            _socket = new Socket(ip, port);
            _messageDelegates = new HashMap<>();
            _in = _socket.getInputStream();
            _out = _socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(_out);
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the delegate that will be called when a message is received.
     * 
     * @param type The type of message to listen for. Use <Message>.class.
     * @param delegate The delegate to call when a message of the specified type is received.
     */
    public void setMessageDelegate(Type type, Delegate<Message> messageDelegate) {
        _messageDelegates.put(type, messageDelegate);
    }

    private void receiveLoop() {
        while (_running) {
            try {
                ObjectInputStream in = new ObjectInputStream(_in);
                Message message = (Message) in.readObject();

                if (message == null) {
                    break;
                }
                
                if (_messageDelegates.containsKey(message.getClass())) {
                    _messageDelegates.get(message.getClass()).invoke(message);
                }

            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void start() {
        _running = true;
        _messageThread = new Thread(() -> receiveLoop());
        _messageThread.start();
    }
}
