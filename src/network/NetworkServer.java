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
    public class Client {
        private Socket _socket;
        private InputStream _in;
        private OutputStream _out;
        private Thread _messageThread;
        private boolean _running;
        private Delegate<Message> _messageDelegate;

        public Client(Socket socket) {
            _socket = socket;
            try {
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

        public void setMessageDelegate(Delegate<Message> messageDelegate) {
            _messageDelegate = messageDelegate;
        }

        private void receiveLoop() {
            while (_running) {
                try {
                    ObjectInputStream in = new ObjectInputStream(_in);
                    Message message = (Message) in.readObject();

                    if (message == null) {
                        break;
                    }

                    if (_messageDelegate != null) {
                        _messageDelegate.invoke(message);
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

        public void stop() {
            _running = false;
            try {
                _socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int _port;
    private String _ip;
    private ServerSocket _serverSocket;
    private boolean _running;
    private Thread _acceptionThread;
    private List<Client> _clients;
    private Delegate<Client> _onClientConnectedDelegate;
    private HashMap<Type, Delegate<Message>> _messageDelegates;

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
    public void stop() {
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
    public void setOnClientConnectedDelegate(Delegate<Client> onClientConnectedDelegate) {
        _onClientConnectedDelegate = onClientConnectedDelegate;
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

    private void handleMessage(Client client, Message message) {
        if (_messageDelegates.containsKey(message.getClass())) {
            _messageDelegates.get(message.getClass()).invoke(message);
        }
    }

    public void broadcastMessage(Message message) {
        for (Client client : _clients) {
            client.sendMessage(message);
        }
    }

    private void acceptLoop() {
        while (_running) {
            try {
                Socket socket = _serverSocket.accept();
                
                Client client = new Client(socket);

                client.setMessageDelegate(message -> handleMessage(client, message));

                client.start();
                _clients.add(client);

                if (_onClientConnectedDelegate != null) {
                    _onClientConnectedDelegate.invoke(client);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
