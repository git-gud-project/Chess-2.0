package com.chess.control;

import java.io.*;
import java.net.*;

import com.chess.network.*;
import com.chess.control.messages.*;
import com.chess.model.*;
import com.chess.view.*;

public class NetworkControl {
    private ChessControl control;

    /**
     * Has delegated white team.
     */
    private boolean hasDelegatedWhiteTeam;

    /**
     * Has delegated black team.
     */
    private boolean hasDelegatedBlackTeam;

    /**
     * Network server
     */
    private NetworkServer networkServer;

    /**
     * Network client
     */
    private NetworkClient networkClient;

    /**
     * Returns true if the game is in simple player mode.
     * 
     * @return true if the game is in simple player mode.
     */
    public boolean isSinglePlayer() {
        return networkServer == null && networkClient == null;
    }

    /**
     * Returns true if this application is running as a server.
     * 
     * @return true if this application is running as a server.
     */
    public boolean isHost() {
        return networkServer != null;
    }

    /**
     * Returns true if we are either a client or a server.
     * 
     * @return true if we are either a client or a server.
     */
    public boolean networked() {
        return networkServer != null || networkClient != null;
    }

    /**
     * Returns the model of the control.
     * 
     * @return the model of the control.
     */
    private ChessModel getModel() {
        return control.getModel();
    }

    /**
     * Returns the view of the control.
     * 
     * @return the view of the control.
     */
    private ChessView getView() {
        return control.getView();
    }

    /**
     * Creates a new network control.
     * 
     * @param control The model.
     */
    public NetworkControl(ChessControl chessControl) {
        this.control = chessControl;
    }

    public void SetupViewHooks() {
        ChessView view = getView();

        Menu menu = view.getMenu();

        // Setup listeners on the menu items
        menu.getOnStartServerEvent().addDelegate((ipAndPort) -> {
            HostDetails hostDetails = new HostDetails(ipAndPort);
            
            this.startServer(hostDetails);
        });
        
        menu.getOnConnectToServerEvent().addDelegate((ipAndPort) -> {
            HostDetails hostDetails = new HostDetails(ipAndPort);
            
            this.startClient(hostDetails);
        });

        menu.getOnDisconnectEvent().addDelegate((jButton) -> {
            if (networkClient != null) {
                networkClient.stop();
            }
            
            if (networkServer != null) {
                networkServer.stop();
            }
        });

        getModel().getOnModelLoadedEvent().addDelegate((serialModel) -> {
            if (networkServer == null) {
                return;
            }

            broadcastMessage(new LoadGameMessage(serialModel));
        });
    }

    private void setMenuState(boolean enabled) {
        ChessView view = getView();

        Menu menu = view.getMenu();
        
        menu.getStartServer().setEnabled(enabled);
        menu.getConnectToServer().setEnabled(enabled);
        menu.getDisconnect().setEnabled(!enabled);

        menu.getNewGame().setEnabled(isHost() || enabled);
    }

    /**
     * Send a message to the server.
     * 
     * This works both as a client and a server.
     * 
     * @param message The message to send.
     */
    public void sendMessage(Message message) throws IllegalStateException {
        if (networkClient != null) {
            networkClient.sendMessage(message);

            return;
        }

        throw new IllegalStateException("Not connected to a server.");
    }

    /**
     * Broadcasts a message to all clients.
     * 
     * This works only as a server.
     */
    public void broadcastMessage(Message message) throws IllegalStateException {
        if (networkServer != null) {
            networkServer.broadcastMessage(message);

            return;
        }

        throw new IllegalStateException("Server not running.");
    }

    /**
     * Start a client.
     * 
     * @param hostDetails The host details.
     */
    private void startClient(HostDetails hostDetails) {
        if (networkClient != null) {
            return;
        }

        control.setPaused(true);

        networkClient = new NetworkClient(hostDetails);
        
        try {
            networkClient.start();
        } catch (SocketException e) {
            System.out.println("Could not connect to server.");

            networkClient = null;
            return;
        } catch (IOException e) {
            e.printStackTrace();
            
            networkClient = null;
            return;
        }

        networkClient.sendMessage(new ClientReadyMessage());

        //
        // Update the menu state, to reflect the fact that we are now connected
        //
        setMenuState(false);

        networkClient.setOnDisconnectDelegate(() -> {
            System.out.println("Disconnected from server.");

            //
            // Update the menu state, to reflect the fact that we are now disconnected
            //
            setMenuState(true);

            // Set the network client to null, so that we can start a new client
            networkClient = null;

            // Pause the game
            control.setPaused(true);

            // Reset team authority
            getModel().getTeamWhite().setHasAuthority(true);
            getModel().getTeamBlack().setHasAuthority(true);
            control.setOurTeam(null);
        });

        // Setup message delegates.

        networkClient.setMessageDelegate(SetTeamMessage.class, message -> {
            SetTeamMessage setTeamMessage = (SetTeamMessage) message;

            // Decide which team to use.
            ChessTeam ourTeam;

            if (setTeamMessage.isWhite()) {
                ourTeam = getModel().getTeamWhite();
            } else {
                ourTeam = getModel().getTeamBlack();
            }

            // Set out current team and its authority.
            control.setOurTeam(ourTeam);

            ourTeam.setHasAuthority(true);

            // Set the opponent's authority.
            getModel().getOtherTeam(ourTeam).setHasAuthority(false);
        });

        networkClient.setMessageDelegate(AffirmMoveMessage.class, message -> {
            AffirmMoveMessage affirmMoveMessage = (AffirmMoveMessage) message;
            
            // Collect variables.
            Cell fromCell = getModel().getBoard().getCell(affirmMoveMessage.getFromRow(), affirmMoveMessage.getFromCol());
            Cell toCell = getModel().getBoard().getCell(affirmMoveMessage.getToRow(), affirmMoveMessage.getToCol());
            Boolean isElimination = affirmMoveMessage.isElimination();

            // Create the move.
            Move move = new Move(toCell, fromCell, isElimination);
            
            // Apply the move.
            control.executeMove(move);
        });

        networkClient.setMessageDelegate(PromotePawnMessage.class, message -> {
            PromotePawnMessage promotePawnMessage = (PromotePawnMessage) message;
            
            // Forward to control.
            control.promotePawn(promotePawnMessage.getRow(), promotePawnMessage.getCol(), promotePawnMessage.getPieceType(), promotePawnMessage.isElimination());
        });

        networkClient.setMessageDelegate(ChangeNameMessage.class, message -> {
            ChangeNameMessage changeNameMessage = (ChangeNameMessage) message;

            // Decide which team to use.
            ChessTeam team;

            if (changeNameMessage.isWhite()) {
                team = getModel().getTeamWhite();
            } else {
                team = getModel().getTeamBlack();
            }

            // Set the team's name.
            team.setName(changeNameMessage.getName());
        });

        networkClient.setMessageDelegate(PauseGameMessage.class, message -> {
            PauseGameMessage pauseGameMessage = (PauseGameMessage) message;

            // Set the game's paused state.
            control.setPaused(pauseGameMessage.isPaused());
        });

        networkClient.setMessageDelegate(LoadGameMessage.class, message -> {
            LoadGameMessage loadGameMessage = (LoadGameMessage) message;

            if (isHost()) {
                return;
            }

            // Load the game.
            getModel().loadModel(loadGameMessage.getModel());

            // Pause the game.
            control.setPaused(true);
        });
    }

    /**
     * Start a server.
     * 
     * Also starts a client which is connected to the server.
     * 
     * @param hostDetails The host details.
     */
    private void startServer(HostDetails hostDetails) {
        if (networkServer != null) {
            return;
        }

        control.setPaused(true);

        networkServer = new NetworkServer(hostDetails);

        try {
            networkServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        networkServer.setOnClientConnectedDelegate((client) -> {
            System.out.println("Client connected");
        });

        networkServer.setOnClientDisconnectedDelegate((client) -> {
            System.out.println("Client disconnected");

            //
            // As we only handle at most one client, we can stop the server.
            //

            if (networkServer != null) {
                networkServer.stop();
            }
        });

        networkServer.setOnCloseDelegate(() -> {
            System.out.println("Server closed");

            // Set the network server to null, so that we can start a new server
            networkServer = null;

            // Pause the game.
            control.setPaused(true);

            // Reset delegate authority
            hasDelegatedWhiteTeam = false;
            hasDelegatedBlackTeam = false;
        });

        // Setup message delegates.

        networkServer.setMessageDelegate(MovePieceMessage.class, (message) -> {
            MovePieceMessage movePieceMessage = (MovePieceMessage) message;

            // Collect variables.
            Cell from = new Cell(movePieceMessage.getToRow(), movePieceMessage.getToCol());
            Cell to = new Cell(movePieceMessage.getFromRow(), movePieceMessage.getFromCol());
            Boolean isElimination = movePieceMessage.isElimination();

            // Create the move.
            Move move = new Move(from, to, isElimination);

            // Apply the move.
            control.movePiece(move);
        });
        
        networkServer.setMessageDelegate(ClientReadyMessage.class, (client, message) -> {
            // Delegate team to client.
            if (!hasDelegatedWhiteTeam) {
                // Make the client white.
                client.sendMessage(new SetTeamMessage(true));
                
                hasDelegatedWhiteTeam = true;
            } else if (!hasDelegatedBlackTeam) {
                // Make the client black.
                client.sendMessage(new SetTeamMessage(false));
                
                hasDelegatedBlackTeam = true;
            } else {
                // Client is spectator.
            }

            // Pause the game.
            control.setPaused(true);
            
            // Send a load message to the client.
            client.sendMessage(new LoadGameMessage(new SerialModel(getModel())));
        });

        networkServer.setMessageDelegate(PromotePawnMessage.class, (client, message) -> {
            // Broadcast the message.
            networkServer.broadcastMessage(message);
        });

        networkServer.setMessageDelegate(ChangeNameMessage.class, (client, message) -> {
            // Broadcast the message.
            networkServer.broadcastMessage(message);
        });

        networkServer.setMessageDelegate(PauseGameMessage.class, (client, message) -> {
            // Broadcast the message.
            networkServer.broadcastMessage(message);
        });

        // Also start up a client. This is done after the server is started so that the client can connect to the server.

        startClient(hostDetails);
    }
}
