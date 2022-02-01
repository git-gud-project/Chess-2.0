package control;
import view.BoardCell;
import view.BoardGridPanel;
import view.ChessView;
import model.*;
import network.*;

import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;
import java.util.*;

public class ChessControl {
    private ChessModel _model;
    private ChessView _view;

    /**
     * The selected cell, or null if no cell is selected.
     */
    private BoardCell _selectedCell;

    /**
     * The currently highlighted cells.
     */
    private ArrayList<BoardCell> _highlightedCells;

    /**
     * Network server
     */
    private NetworkServer _networkServer;

    /**
     * Network client
     */
    private NetworkClient _networkClient;

    /**
     * Returns true if the game is in simple player mode.
     * 
     * @return true if the game is in simple player mode.
     */
    public boolean isSinglePlayer() {
        return _networkServer == null && _networkClient == null;
    }

    /**
     * Returns true if this application is running as a server.
     * 
     * @return true if this application is running as a server.
     */
    public boolean isHost() {
        return _networkServer != null;
    }

    /**
     * Moves the piece on the board.
     * 
     * @param fromRow the row of the piece to move.
     * @param fromCol the column of the piece to move.
     * @param toRow the row to move the piece to.
     * @param toCol the column to move the piece to.
     * @param isElimination true if a piece is being eliminated.
     */
    private void executeMove(int fromRow, int fromCol, int toRow, int toCol, boolean isElimination) {
        Board board = _model.getBoard();

        Piece piece = board.getCell(fromRow, fromCol).getPiece();

        piece.move(board.getCell(toRow, toCol));

        Team otherTeam = _model.getOtherTeam(_model.getCurrentTeam());

        System.out.println("Elimination: " + isElimination);

        // Halfmove clock: The number of halfmoves since the last capture or pawn advance, used for the fifty-move rule.
        boolean halfMove = piece.getPieceType() != PieceType.PAWN && !isElimination;

        MoveNotation mN = new MoveNotation(toRow,toCol,piece,isElimination, _model.getBoard());

        _model.registerMove(halfMove, mN);

        //CHECK HIGHLIGHT
        Cell c = _model.getBoard().getKingCell(piece.getTeam());
        BoardCell check = _view.getBoardGridPanel().getCell(c.getRow(),c.getCol());
        check.unhighlight();
        if(piece.getCell().getBoard().isCheck(_model.getOtherTeam(piece.getTeam()))){
            System.out.print("TESTYTEST");
            System.out.println(piece.getTeam());
            c = _model.getBoard().getKingCell(_model.getOtherTeam(piece.getTeam()));
            check =  _view.getBoardGridPanel().getCell(c.getRow(),c.getCol());
            check.highlight(Color.RED);
        }
        _view.updateModel();

        otherTeam.clearEnPassant();
    }

    /**
     * Request to move a piece.
     * 
     * Forwards to the network client if the game is in network mode.
     */
    private void movePiece(int fromRow, int fromCol, int toRow, int toCol, boolean isElimination) {
        // Forward to executeMove if no network.
        if (isSinglePlayer()) {
            executeMove(fromRow, fromCol, toRow, toCol, isElimination);
            
            return;
        }
        
        // If we are not the host, send a request to the host.
        if (!isHost()) {
            _networkClient.sendMessage(new MovePieceMessage(fromRow, fromCol, toRow, toCol, isElimination));

            return;
        }

        // TODO: Validate move.
        
        // If we are the host, broadcast the move to all clients.
        _networkServer.broadcastMessage(new AffirmMoveMessage(fromRow, fromCol, toRow, toCol, isElimination));
    }
    
    private void handleClick(BoardCell boardCell) {
        ChessModel model = _view.getModel();
        BoardGridPanel grid = _view.getBoardGridPanel();

        // If the cell was highlighted, move the selected piece to the cell
        if (_highlightedCells.contains(boardCell)) {
            movePiece(_selectedCell.getRow(), _selectedCell.getCol(), boardCell.getRow(), boardCell.getCol(), boardCell.isElimination());
        }

        if (_selectedCell != null) {
            _selectedCell.unhighlight();
        }

        for (BoardCell cell : _highlightedCells) {
            cell.unhighlight();
            cell.setElimination(false);
        }

        _highlightedCells.clear();

        Piece piece = model.getBoard().getCell(boardCell.getRow(), boardCell.getCol()).getPiece();

        if (piece == null) {
            return;
        }

        if (piece.getTeam() != model.getCurrentTeam()) {
            return;
        }

        _selectedCell = boardCell;

        _selectedCell.highlight(ChessView.HIGHLIGHT_COLOR_PIECE);


        Iterator<Move> moves = piece.getPossibleMoves();
        while (moves.hasNext()) {
            Move move = moves.next();

            Cell cell = move.getCell();

            BoardCell possibleMove = grid.getCell(cell.getRow(), cell.getCol());

            possibleMove.highlight(move.isEliminatable() ? ChessView.HIGHLIGHT_COLOR_ATTACK : ChessView.HIGHLIGHT_COLOR_MOVE);
            possibleMove.setElimination(move.isEliminatable());

            _highlightedCells.add(possibleMove);
        }
    }

    /**
     * Start a client.
     */
    public void startClient(String host, int port) {
        _networkClient = new NetworkClient(host, port);
        
        try {
            _networkClient.start();
        } catch (SocketException e) {
            System.out.println("Could not connect to server.");

            _networkClient = null;
            return;
        } catch (IOException e) {
            e.printStackTrace();
            
            _networkClient = null;
            return;
        }

        _networkClient.sendMessage(new ClientReadyMessage());

        _networkClient.setOnDisconnectDelegate(() -> {
            System.out.println("Disconnected from server.");
            _networkClient = null;
        });

        _networkClient.setMessageDelegate(AffirmMoveMessage.class, message -> {
            AffirmMoveMessage affirmMoveMessage = (AffirmMoveMessage) message;
            executeMove(affirmMoveMessage.getFromRow(), affirmMoveMessage.getFromCol(), affirmMoveMessage.getToRow(), affirmMoveMessage.getToCol(), affirmMoveMessage.isElimination());
        });
    }

    /**
     * Start a server.
     * 
     * Also starts a client which is connected to the server.
     */
    public void startServer(String host, int port) {
        _networkServer = new NetworkServer(port, host);
        try {
            _networkServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        _networkServer.setOnClientConnectedDelegate((client) -> {
            System.out.println("Client connected");
        });

        _networkServer.setOnClientDisconnectedDelegate((client) -> {
            System.out.println("Client disconnected");
        });

        _networkServer.setMessageDelegate(MovePieceMessage.class, (message) -> {
            MovePieceMessage movePieceMessage = (MovePieceMessage) message;

            movePiece(movePieceMessage.getFromRow(), movePieceMessage.getFromCol(), movePieceMessage.getToRow(), movePieceMessage.getToCol(), movePieceMessage.isElimination());
        });
        
        _networkServer.setMessageDelegate(ClientReadyMessage.class, (message) -> {
            ClientReadyMessage clientReadyMessage = (ClientReadyMessage) message;
            
            System.out.println("Client ready");
        });

        startClient(host, port);
    }

    public ChessControl() {
        _model = new ChessModel();
        _view = new ChessView(_model);
        Timer t = new Timer(100, new TimerListener());
        t.start();

        _highlightedCells = new ArrayList<>();

        // Setup listener when clicking on a cell.
        _view.getBoardGridPanel().setClickDelegate((BoardCell boardCell) -> handleClick(boardCell));

        // Setup listeners on the menu items
        _view.getMenu().setStartServerDelegate((port) -> startServer("localhost", port));
        _view.getMenu().setConnectToServerDelegate((port) -> startClient("localhost", port));

        _view.getMenu().getNewGame().addActionListener((e) -> {
            JFrame f = new JFrame();
            int answer = JOptionPane.showConfirmDialog(f, "Are you sure you want to start a new game?\nAny unsaved changes to the current state will be lost.", "", JOptionPane.YES_NO_OPTION);
            if(answer == JOptionPane.YES_OPTION) {
                _model = new ChessModel();
                _view.setModel(_model);
                _view.updateModel();
            }
        });
    }

    public class TimerListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            _model.getCurrentTeam().getTime().tick();
            _view.updateModel();
        }
    }
}
