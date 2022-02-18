package com.chess.control;

import com.chess.view.*;
import com.chess.model.*;
import com.chess.control.messages.*;
import com.chess.utils.Event;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.KeyStroke;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ChessControl {
    private ChessModel model;
    private ChessView view;

    /**
     * The network control.
     */
    private NetworkControl networkControl;

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
    private Team ourTeam;

    /**
     * Returns true if it is our turn. Always true if we are not playing online.
     * 
     * @return true if it is our turn
     */
    public boolean isMyTurn() {
        return networkControl.isSinglePlayer() || model.getCurrentTeam() == ourTeam;
    }

    /**
     * Returns true if we have authority over a team.
     * 
     * @param team the team to check
     * @return true if we have authority over the team
     */
    public boolean hasAuthorityOver(Team team) {
        return networkControl.isSinglePlayer() || this.ourTeam == team;
    }

    public void setOurTeam(Team team) {
        this.ourTeam = team;
    }

    public Team getOurTeam() {
        return this.ourTeam;
    }

    public ChessModel getModel() {
        return model;
    }

    public ChessView getView() {
        return view;
    }

    public void promotePawn(int row, int col, PieceType type, boolean isElimination) {
        Board board = model.getBoard();
        Piece piece = board.getCell(row, col).getPiece();
        Cell cell = piece.getCell();
        Team team = piece.getTeam();
        cell.setPiece(null);
        Piece promoted = model.createPiece(type, team, cell);
        cell.setPiece(promoted);

        Team otherTeam = model.getOtherTeam(model.getCurrentTeam());

        Move move = new Move(cell, type);

        //model.registerMove(false, move);

        otherTeam.clearEnPassant();

        playSound("pawnPromotion");
    }

    /**
     * Moves the piece on the board.
     * 
     * @param move The move that is being performed.
     *
     */
    public void executeMove(Move move) {
        if (!model.getStarted()) {
            model.setStarted(true);
            setPaused(false);
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

                if (!networkControl.isSinglePlayer()) {
                    networkControl.sendMessage(new PromotePawnMessage(cell.getRow(), cell.getCol(), type, isElimination));
                } else {
                    promotePawn(cell.getRow(), cell.getCol(), type, isElimination);
                }
            }

            //return;
        }

        checkHighlight(piece);

        model.registerMove(halfMove, move);

        if(model.getBoard().isGameOver(model.getCurrentTeam()) != 0){
            playSound("checkmate");
            gameOver(model.getBoard().isGameOver(model.getCurrentTeam()));
        }
        else if(model.getBoard().isCheck(model.getCurrentTeam())) {
            playSound("check");
        }
        else if(move.getIsCastleKingSide() || move.getIsCastleQueenSide()) {
            playSound("castling");
        }
        else if(move.isEliminatable()) {
            playSound("pieceCapture");
        }
        else playSound("pieceMove");

        otherTeam.clearEnPassant();
    }

    /**
     * The game over process that announces the winner or stalemate. Also handles if the user wants to start a new game
     * or exit.
     * @param endingState - contains the information if the game ends in with a win (= 1) or a stalemate (= 2).
     */
    private void gameOver(int endingState){

        Object[] options = {"New game.", "Exit"};
        switch (endingState){
            case 1:
            int n = JOptionPane.showOptionDialog(view.getOwner(), "Game over!\nThe game ended in a stalemate.", "Game over!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (n == JOptionPane.CLOSED_OPTION) {
                System.exit(0);
            }
            switch (n) {
                case 0:
                    checkHighlight(model.getBoard().getKingCell(model.getCurrentTeam()).getPiece());
                    model.resetState();
                    view.getBoardGridPanel().unHighlightAll();
                    break;
                case 1:
                    System.exit(0);
            }
                break;
            case 2:
                n = JOptionPane.showOptionDialog(view.getOwner(), "Game over!\n" + model.getOtherTeam(model.getCurrentTeam()) + " has won!", "Game over!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (n == JOptionPane.CLOSED_OPTION) {
                    System.exit(0);
                }
                switch (n) {
                    case 0:
                        checkHighlight(model.getBoard().getKingCell(model.getCurrentTeam()).getPiece());
                        model.resetState();
                        view.getBoardGridPanel().unHighlightAll();
                        break;
                    case 1:
                        System.exit(0);
                }
                break;
        }

    }


    /**
     * Highlights and unhighlights the cell containing the teams king piece.
     * @param piece - the moved piece.
     */
    private void checkHighlight(Piece piece){
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

    }

    /**
     * Request to move a piece.
     * 
     * Forwards to the network client if the game is in network mode.
     */
    public void movePiece(Move move) {
        int fromRow = move.getFromCell().getRow();
        int fromCol = move.getFromCell().getCol();
        int toRow = move.getToCell().getRow();
        int toCol = move.getToCell().getCol();
        boolean isElimination = move.isEliminatable();

        // Forward to executeMove if no network.
        if (networkControl.isSinglePlayer()) {
            executeMove(move);
            return;
        }
        
        // If we are not the host, send a request to the host.
        if (!networkControl.isHost()) {
            networkControl.sendMessage(new MovePieceMessage(fromRow, fromCol, toRow, toCol, isElimination));

            return;
        }
        
        // If we are the host, broadcast the move to all clients.
        networkControl.broadcastMessage(new AffirmMoveMessage(fromRow, fromCol, toRow, toCol, isElimination));
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

        if (!networkControl.isHost() && !networkControl.isSinglePlayer()) {
            networkControl.sendMessage(new PauseGameMessage(!model.getPaused()));
            return;
        }

        setPaused(!model.getPaused());

        if (networkControl.isHost()) {
            networkControl.broadcastMessage(new PauseGameMessage(model.getPaused()));
        }
    }

    protected void setPaused(boolean paused) {
        model.setPaused(paused);

        JButton button = view.getInfoPanel().getPauseButton();
        button.setText(!model.getStarted() ? "Make a move to start" : paused ? "Resume" : "Pause");
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
            if(model.getBoard().getCell(selectedCell.getRow(),selectedCell.getCol()).getPiece()==null){
                selectedCell.unhighlight();
            } else if(!model.getBoard().getCell(selectedCell.getRow(),selectedCell.getCol()).getPiece().getPieceType().equals(PieceType.KING)){
                selectedCell.unhighlight();
            } else {
                selectedCell.unhighlight();
                if(model.getBoard().isCheck(model.getCurrentTeam())) {
                    selectedCell.highlight(Color.RED);
                }
            }
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
        if (!networkControl.isSinglePlayer()) {
            networkControl.sendMessage(new ChangeNameMessage(name, team == model.getTeamWhite()));
        } else {
            team.setName(name);
        }
    }

    public ChessControl() {
        model = new ChessModel();
        view = new ChessView(model);
        highlightedCells = new ArrayList<>();

        //
        // Create control subcomponents.
        //

        // Create network control.
        networkControl = new NetworkControl(this);

        networkControl.SetupViewHooks();

        // Setup listener when clicking on a cell.
        view.getBoardGridPanel().setClickDelegate((BoardCell boardCell) -> handleClick(boardCell));
        
        view.getMenu().getOnLoadGameEvent().addDelegate((serialModel) -> {
            model.loadModel(serialModel);
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

    private void playSound(String sound) {
        String soundMap = view.getSoundMap();
        SoundPlayer.playSound(soundMap,sound);
    }

}
