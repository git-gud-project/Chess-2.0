package control;
import view.BoardCell;
import view.BoardGridPanel;
import view.ChessView;
import view.PlayerPanel;
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
     * The team we are playing as.
     */
    private Team _team;

    /**
     * Has delegated white team.
     */
    private boolean _hasDelegatedWhiteTeam;

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

    public boolean isMyTurn() {
        return isSinglePlayer() || _model.getCurrentTeam() == _team;
    }

    public boolean hasAuthorityOver(Team team) {
        return isSinglePlayer() || _team == team;
    }

    private void promotePawn(int row, int col, PieceType type, boolean isElimination) {
        Board board = _model.getBoard();
        Piece piece = board.getCell(row, col).getPiece();
        Cell cell = piece.getCell();
        Team team = piece.getTeam();
        cell.setPiece(null);
        Piece promoted = _model.createPiece(type, team, cell);
        cell.setPiece(promoted);

        Team otherTeam = _model.getOtherTeam(_model.getCurrentTeam());

        MoveNotation mN = new MoveNotation(row, col, promoted, _model.getBoard());

        _model.registerMove(false, mN);

        otherTeam.clearEnPassant();
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

        // Halfmove clock: The number of halfmoves since the last capture or pawn advance, used for the fifty-move rule.
        boolean halfMove = piece.getPieceType() != PieceType.PAWN && !isElimination;

        if (piece instanceof PiecePawn pawn && pawn.getCell().getRow() == piece.getTeam().getPromotionRow()) {
            if (isMyTurn()) {
                PieceType type = _view.promotePawn();
                Cell cell = pawn.getCell();

                if (!isSinglePlayer()) {
                    _networkClient.sendMessage(new PromotePawnMessage(cell.getRow(), cell.getCol(), type, isElimination));
                } else {
                    promotePawn(cell.getRow(), cell.getCol(), type, isElimination);
                }
            }

            return;
        }
        
        MoveNotation mN = new MoveNotation(fromCol ,toRow,toCol,piece,isElimination, _model.getBoard());

        _model.registerMove(halfMove, mN);

        //CHECK HIGHLIGHT, DOESNT WORK WHEN KING MOVES.
        Cell c = _model.getBoard().getKingCell(piece.getTeam());
        BoardCell check = _view.getBoardGridPanel().getCell(c.getRow(),c.getCol()); //FAULT HERE.
        check.unhighlight();
        if(piece.getCell().getBoard().isCheck(_model.getOtherTeam(piece.getTeam()))){
            c = _model.getBoard().getKingCell(_model.getOtherTeam(piece.getTeam()));
            check =  _view.getBoardGridPanel().getCell(c.getRow(),c.getCol());
            check.highlight(Color.RED);
        }

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
        if (!isMyTurn()) {
            return;
        }

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
            _networkClient.sendMessage(new ChangeNameMessage(name, team == _model.getTeamWhite()));
        } else {
            team.setName(name);
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

        _networkClient.setMessageDelegate(SetTeamMessage.class, message -> {
            SetTeamMessage setTeamMessage = (SetTeamMessage) message;
            if (setTeamMessage.isWhite()) {
                _team = _model.getTeamWhite();
            } else {
                _team = _model.getTeamBlack();
            }
        });

        _networkClient.setMessageDelegate(AffirmMoveMessage.class, message -> {
            AffirmMoveMessage affirmMoveMessage = (AffirmMoveMessage) message;
            executeMove(affirmMoveMessage.getFromRow(), affirmMoveMessage.getFromCol(), affirmMoveMessage.getToRow(), affirmMoveMessage.getToCol(), affirmMoveMessage.isElimination());
        });

        _networkClient.setMessageDelegate(PromotePawnMessage.class, message -> {
            PromotePawnMessage promotePawnMessage = (PromotePawnMessage) message;
            promotePawn(promotePawnMessage.getRow(), promotePawnMessage.getCol(), promotePawnMessage.getPieceType(), promotePawnMessage.isElimination());
        });

        _networkClient.setMessageDelegate(ChangeNameMessage.class, message -> {
            ChangeNameMessage changeNameMessage = (ChangeNameMessage) message;
            if (changeNameMessage.isWhite()) {
                _model.getTeamWhite().setName(changeNameMessage.getName());
            } else {
                _model.getTeamBlack().setName(changeNameMessage.getName());
            }
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
        
        _networkServer.setMessageDelegate(ClientReadyMessage.class, (client, message) -> {
            ClientReadyMessage clientReadyMessage = (ClientReadyMessage) message;
            
            System.out.println("Client ready");

            client.sendMessage(new SetTeamMessage(!_hasDelegatedWhiteTeam));

            _hasDelegatedWhiteTeam = true;
        });

        _networkServer.setMessageDelegate(PromotePawnMessage.class, (client, message) -> {
            PromotePawnMessage promotePawnMessage = (PromotePawnMessage) message;

            _networkServer.broadcastMessage(message);
        });

        _networkServer.setMessageDelegate(ChangeNameMessage.class, (client, message) -> {
            ChangeNameMessage changeNameMessage = (ChangeNameMessage) message;

            _networkServer.broadcastMessage(message);
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
                _model.loadFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
                // TODO: Reset other variables
            }
        });

        _view.getInfoPanel().getPlayerPanel1().getOnPlayerNameChangedEvent().addDelegate(team -> {
            handleChangeName(team);
        });

        _view.getInfoPanel().getPlayerPanel2().getOnPlayerNameChangedEvent().addDelegate(team -> {
            handleChangeName(team);
        });
    }

    public class TimerListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            _model.getCurrentTeam().tickTime();
        }
    }
}
