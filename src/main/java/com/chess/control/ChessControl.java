package com.chess.control;

import com.chess.view.*;
import com.chess.model.*;
import com.chess.network.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.KeyStroke;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.SocketException;
import java.util.*;

public class ChessControl {
    private ChessModel model;
    private ChessView view;

    /**
     * The selected cell, or null if no cell is selected.
     */
    private BoardCell selectedCell;

    /**
     * The currently highlighted cells.
     */
    private ArrayList<BoardCell> highlightedCells;

    /**
     * The current list of available moves for selected piece
     */
    private HashMap<BoardCell, Move> currentMoveMap;

    /**
     * The team we are playing as.
     */
    private Team team;

    /**
     * Has delegated white team.
     */
    private boolean hasDelegatedWhiteTeam;

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

    public boolean isMyTurn() {
        return isSinglePlayer() || model.getCurrentTeam() == team;
    }

    public boolean hasAuthorityOver(Team team) {
        return isSinglePlayer() || this.team == team;
    }

    private void promotePawn(int row, int col, PieceType type, boolean isElimination) {
        Board board = model.getBoard();
        Piece piece = board.getCell(row, col).getPiece();
        Cell cell = piece.getCell();
        Team team = piece.getTeam();
        cell.setPiece(null);
        Piece promoted = model.createPiece(type, team, cell);
        cell.setPiece(promoted);

        Team otherTeam = model.getOtherTeam(model.getCurrentTeam());

        Move move = new Move(cell, type);

        model.registerMove(false, move);

        otherTeam.clearEnPassant();
    }

    /**
     * Moves the piece on the board.
     * 
     * @param move The move that is being performed.
     *
     */
    private void executeMove(Move move) {
        if (!model.getStarted()) {
            setPaused(false);
            model.setStarted(true);
        }

        boolean isElimination = move.isEliminatable();

        Piece piece = move.getPiece();

        piece.move(move.getToCell());

        Team otherTeam = model.getOtherTeam(model.getCurrentTeam());

        // Halfmove clock: The number of halfmoves since the last capture or pawn advance, used for the fifty-move rule.
        boolean halfMove = piece.getPieceType() != PieceType.PAWN && !isElimination;

        if (piece.getPieceType() == PieceType.PAWN && piece.getCell().getRow() == piece.getTeam().getPromotionRow()) {
            if (isMyTurn()) {
                PieceType type = view.promotePawn();
                Cell cell = piece.getCell();

                if (!isSinglePlayer()) {
                    networkClient.sendMessage(new PromotePawnMessage(cell.getRow(), cell.getCol(), type, isElimination));
                } else {
                    promotePawn(cell.getRow(), cell.getCol(), type, isElimination);
                }
            }

            return;
        }

        model.registerMove(halfMove, move);

        //CHECK HIGHLIGHT, DOESNT WORK WHEN KING MOVES.
        Cell c = model.getBoard().getKingCell(piece.getTeam());
        BoardCell check = view.getBoardGridPanel().getCell(c.getRow(),c.getCol());
        if(check.getBackground().equals(new Color(191, 64,64)) || check.getBackground().equals(new Color(223, 96, 96))){
            check.unhighlight();
        }

        if(piece.getCell().getBoard().isCheck(model.getOtherTeam(piece.getTeam()))){
            c = model.getBoard().getKingCell(model.getOtherTeam(piece.getTeam()));
            check =  view.getBoardGridPanel().getCell(c.getRow(),c.getCol());
            check.highlight(Color.RED);
        }


        otherTeam.clearEnPassant();
    }

    /**
     * Request to move a piece.
     * 
     * Forwards to the network client if the game is in network mode.
     */
    private void movePiece(Move move) {
        int fromRow = move.getFromCell().getRow();
        int fromCol = move.getFromCell().getCol();
        int toRow = move.getToCell().getRow();
        int toCol = move.getToCell().getCol();
        boolean isElimination = move.isEliminatable();

        // Forward to executeMove if no network.
        if (isSinglePlayer()) {
            executeMove(move);
            return;
        }
        
        // If we are not the host, send a request to the host.
        if (!isHost()) {
            networkClient.sendMessage(new MovePieceMessage(fromRow, fromCol, toRow, toCol, isElimination));

            return;
        }

        // TODO: Validate move.
        
        // If we are the host, broadcast the move to all clients.
        networkServer.broadcastMessage(new AffirmMoveMessage(fromRow, fromCol, toRow, toCol, isElimination));
    }

    private void handleTimeTick() {
        if (model.getPaused()) {
            return;
        }

        model.getCurrentTeam().tickTime();
    }

    private void handlePause() {
        if (!model.getStarted()) {
            return;
        }

        if (!isHost() && !isSinglePlayer()) {
            networkClient.sendMessage(new PauseGameMessage(!model.getPaused()));
            return;
        }

        setPaused(!model.getPaused());

        if (isHost()) {
            networkServer.broadcastMessage(new PauseGameMessage(model.getPaused()));
        }
    }

    private void setPaused(boolean paused) {
        model.setPaused(paused);

        JButton button = view.getInfoPanel().getPauseButton();
        button.setText(paused ? "Resume" : "Pause");
        button.setEnabled(true);
    }
    
    private void handleClick(BoardCell boardCell) {
        if (!isMyTurn() || model.getPaused() && model.getStarted()) {
            return;
        }

        ChessModel model = view.getModel();
        BoardGridPanel grid = view.getBoardGridPanel();

        // If the cell was highlighted, move the selected piece to the cell
        if (highlightedCells.contains(boardCell)) {
            movePiece(currentMoveMap.get(boardCell));
        }

        if (selectedCell != null) {
            selectedCell.unhighlight();
        }

        for (BoardCell cell : highlightedCells) {
            cell.unhighlight();
            cell.setElimination(false);
        }

        highlightedCells.clear();

        Piece piece = model.getBoard().getCell(boardCell.getRow(), boardCell.getCol()).getPiece();

        if (piece == null) {
            return;
        }

        if (piece.getTeam() != model.getCurrentTeam()) {
            return;
        }

        selectedCell = boardCell;

        selectedCell.highlight(ChessView.HIGHLIGHT_COLOR_PIECE);


        Iterator<Move> moves = piece.getPossibleMoves();
        currentMoveMap = new HashMap<>();
        while (moves.hasNext()) {
            Move move = moves.next();

            Cell cell = move.getToCell();

            BoardCell possibleMove = grid.getCell(cell.getRow(), cell.getCol());

            currentMoveMap.put(possibleMove, move);

            possibleMove.highlight(move.isEliminatable() ? ChessView.HIGHLIGHT_COLOR_ATTACK : ChessView.HIGHLIGHT_COLOR_MOVE);
            possibleMove.setElimination(move.isEliminatable());

            highlightedCells.add(possibleMove);
        }
    }

    private void handleChangeName(Team team) {
        if (!hasAuthorityOver(team)) {
            return;
        }

        // Open a dialog to get the new name.
        String name = JOptionPane.showInputDialog(null, "Enter a new name for " + team.getName() + ":", "Change Name", JOptionPane.PLAIN_MESSAGE);

        if (name == null || name.isEmpty()) {
            return;
        }

        // Change the name.
        if (!isSinglePlayer()) {
            networkClient.sendMessage(new ChangeNameMessage(name, team == model.getTeamWhite()));
        } else {
            team.setName(name);
        }
    }

    /**
     * Start a client.
     */
    public void startClient(String host, int port) {
        if (networkClient != null) {
            return;
        }

        model.setPaused(true);

        networkClient = new NetworkClient(host, port);
        
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

        networkClient.setOnDisconnectDelegate(() -> {
            System.out.println("Disconnected from server.");
            networkClient = null;
        });

        networkClient.setMessageDelegate(SetTeamMessage.class, message -> {
            SetTeamMessage setTeamMessage = (SetTeamMessage) message;
            if (setTeamMessage.isWhite()) {
                team = model.getTeamWhite();
            } else {
                team = model.getTeamBlack();
            }

            team.setHasAuthority(true);
            model.getOtherTeam(team).setHasAuthority(false);
        });

        networkClient.setMessageDelegate(AffirmMoveMessage.class, message -> {
            AffirmMoveMessage affirmMoveMessage = (AffirmMoveMessage) message;
            Cell fromCell = model.getBoard().getCell(affirmMoveMessage.getFromRow(), affirmMoveMessage.getFromCol());
            Cell toCell = model.getBoard().getCell(affirmMoveMessage.getToRow(), affirmMoveMessage.getToCol());
            Move move = new Move(toCell, fromCell, affirmMoveMessage.isElimination());
            executeMove(move);
        });

        networkClient.setMessageDelegate(PromotePawnMessage.class, message -> {
            PromotePawnMessage promotePawnMessage = (PromotePawnMessage) message;
            promotePawn(promotePawnMessage.getRow(), promotePawnMessage.getCol(), promotePawnMessage.getPieceType(), promotePawnMessage.isElimination());
        });

        networkClient.setMessageDelegate(ChangeNameMessage.class, message -> {
            ChangeNameMessage changeNameMessage = (ChangeNameMessage) message;
            if (changeNameMessage.isWhite()) {
                model.getTeamWhite().setName(changeNameMessage.getName());
            } else {
                model.getTeamBlack().setName(changeNameMessage.getName());
            }
        });

        networkClient.setMessageDelegate(PauseGameMessage.class, message -> {
            PauseGameMessage pauseGameMessage = (PauseGameMessage) message;

            setPaused(pauseGameMessage.isPaused());
        });

        networkClient.setMessageDelegate(LoadGameMessage.class, message -> {
            LoadGameMessage loadGameMessage = (LoadGameMessage) message;

            model.loadModel(loadGameMessage.getModel());
            setPaused(model.getPaused());
        });
    }

    /**
     * Start a server.
     * 
     * Also starts a client which is connected to the server.
     */
    public void startServer(String host, int port) {
        if (networkServer != null) {
            return;
        }

        model.setPaused(true);

        networkServer = new NetworkServer(port, host);
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
        });

        networkServer.setMessageDelegate(MovePieceMessage.class, (message) -> {
            MovePieceMessage movePieceMessage = (MovePieceMessage) message;

            Move move = new Move(new Cell(model.getBoard(), movePieceMessage.getToRow(), movePieceMessage.getToCol()), new Cell(model.getBoard(), movePieceMessage.getFromRow(), movePieceMessage.getFromCol()),movePieceMessage.isElimination());

            movePiece(move);
        });
        
        networkServer.setMessageDelegate(ClientReadyMessage.class, (client, message) -> {
            //ClientReadyMessage clientReadyMessage = (ClientReadyMessage) message;
            
            System.out.println("Client ready");

            client.sendMessage(new SetTeamMessage(!hasDelegatedWhiteTeam));

            hasDelegatedWhiteTeam = true;

            client.sendMessage(new LoadGameMessage(new SerialModel(model)));
        });

        networkServer.setMessageDelegate(PromotePawnMessage.class, (client, message) -> {
            //PromotePawnMessage promotePawnMessage = (PromotePawnMessage) message;

            networkServer.broadcastMessage(message);
        });

        networkServer.setMessageDelegate(ChangeNameMessage.class, (client, message) -> {
            //ChangeNameMessage changeNameMessage = (ChangeNameMessage) message;

            networkServer.broadcastMessage(message);
        });

        networkServer.setMessageDelegate(PauseGameMessage.class, (client, message) -> {
            //PauseGameMessage changeNameMessage = (PauseGameMessage) message;

            networkServer.broadcastMessage(message);
        });

        startClient(host, port);
    }

    public ChessControl() {
        model = new ChessModel();
        view = new ChessView(model);
        highlightedCells = new ArrayList<>();

        // Setup listener when clicking on a cell.
        view.getBoardGridPanel().setClickDelegate((BoardCell boardCell) -> handleClick(boardCell));

        // Setup listeners on the menu items
        view.getMenu().setStartServerDelegate((ipAndPort) -> {
            // Split the ip and port, if there is a :
            if (ipAndPort.contains(":")) {
                String[] ipAndPortSplit = ipAndPort.split(":");
                startServer(ipAndPortSplit[0], Integer.parseInt(ipAndPortSplit[1]));
            } else {
                startServer(null, Integer.parseInt(ipAndPort));
            }
        });
        view.getMenu().setConnectToServerDelegate((ipAndPort) -> {
            // Split the ip and port, if there is a :
            if (ipAndPort.contains(":")) {
                String[] ipAndPortSplit = ipAndPort.split(":");
                startClient(ipAndPortSplit[0], Integer.parseInt(ipAndPortSplit[1]));
            } else {
                startClient(null, Integer.parseInt(ipAndPort));
            }
        });

        view.getMenu().getNewGame().addActionListener((e) -> {
            JFrame f = new JFrame();
            int answer = JOptionPane.showConfirmDialog(f, "Are you sure you want to start a new game?\nAny unsaved changes to the current state will be lost.", "", JOptionPane.YES_NO_OPTION);
            if(answer == JOptionPane.YES_OPTION) {
                model.resetState();
                view.getInfoPanel().getMovesPanel().resetMovesPanel();
            }
        });

        view.getInfoPanel().getPlayerPanel1().getOnPlayerNameChangedEvent().addDelegate(team -> {
            handleChangeName(team);
        });

        view.getInfoPanel().getPlayerPanel2().getOnPlayerNameChangedEvent().addDelegate(team -> {
            handleChangeName(team);
        });

        view.getInfoPanel().getOnPauseButtonClickedEvent().addDelegate(button -> {
            handlePause();
        });

        Timer t = new Timer(100, (e) -> {
            handleTimeTick();
        });
        
        t.start();

        KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

        view.getBoardPanel().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(key, "escape");

        // Lambda expression for the escape key
        view.getBoardPanel().getActionMap().put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handlePause();
            }
        });

        setPaused(true);

        view.getInfoPanel().getPauseButton().setEnabled(false);
        view.getInfoPanel().getPauseButton().setText("Make a move to start");
        
        model.loadFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }
}
