package com.chess.control;

import com.chess.view.*;
import com.chess.model.*;
import com.chess.model.chess.ChessBoardInformation;
import com.chess.model.chess.ChessModel;
import com.chess.model.chess.ChessPieceFactory;
import com.chess.model.chess.ChessTeam;
import com.chess.model.chess.ChessTeamParameters;
import com.chess.model.chess.ChessTypeIdentifier;
import com.chess.control.messages.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.KeyStroke;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ChessControl implements ChessControlInterface {
    private final ChessModel model;

    private final ChessView view;

    private final ChessBoardInformation boardInformation;

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
    private HashMap<BoardCell, Move> currentMoveMap = new HashMap<>();

    /**
     * The team we are playing as.
     */
    private ChessTeam ourTeam;

    /**
     * Returns true if it is our turn. Always true if we are not playing online.
     * 
     * @return true if it is our turn
     */
    private boolean isMyTurn() {
        return networkControl.isSinglePlayer() || model.getCurrentTeam() == ourTeam;
    }

    /**
     * Returns true if we have authority over a team.
     * 
     * @param team the team to check
     * @return true if we have authority over the team
     */
    private boolean hasAuthorityOver(ChessTeam team) {
        return networkControl.isSinglePlayer() || this.ourTeam == team;
    }

    @Override
    public void setLocalTeam(ChessTeam team) {
        this.ourTeam = team;
    }

    @Override
    public void setPaused(boolean paused) {
        model.setPaused(paused);

        JButton button = view.getInfoPanel().getPauseButton();
        button.setText(!model.getStarted() ? "Make a move to start" : paused ? "Resume" : "Pause");
        button.setEnabled(true);
    }

    @Override
    public void promotePawn(int row, int col, Identifier typeIdentifier, boolean isElimination) throws IllegalArgumentException {
        final Board board = model.getBoard();

        final Cell cell = board.getCell(row, col);

        Piece piece = cell.getPiece();

        final Identifier teamIdentifier = piece.getTeamIdentifier();

        final ChessTeam team = model.getTeam(teamIdentifier);

        final Piece promoted = ChessPieceFactory.createPiece(typeIdentifier, team);

        cell.updatePiece(promoted, true);

        piece = promoted;

        final Move move = new Move(cell.getPosition(), typeIdentifier);

        model.registerMove(false, move);

        playSound("pawnPromotion");
    }

    /**
     * Moves the piece on the board.
     * 
     * @param move The move that is being performed.
     */
    @Override
    public void executeMove(Move move) {
        if (!model.getStarted()) {
            model.setStarted(true);
            setPaused(false);
        }

        boolean isElimination = move.isEliminatable();

        Cell cell = model.getBoard().getCell(move.getFromCell());

        Piece piece = cell.getPiece();

        Identifier typeIdentifier = piece.getTypeIdentifier();

        ChessTeam otherTeam = model.getOtherTeam(model.getCurrentTeam());

        final ChessTeamParameters otherTeamParameters = otherTeam.getTeamParameters();

        final Position to = move.getToCell();

        if (isElimination && !boardInformation.isEmpty(to)) {
            Identifier eliminatedTypeIdentifier = boardInformation.getTypeIdentifier(to);

            // If we eliminated a rook, we need to update the castling flags.
            if (eliminatedTypeIdentifier.equals(ChessTypeIdentifier.ROOK)) {
                final Position kingSide = otherTeamParameters.getCastlingKingSidePosition().addCol(1);
                final Position queenSide = otherTeamParameters.getCastlingQueenSidePosition().addCol(-2);
                if (to.equals(kingSide)) {
                    otherTeamParameters.setCanCastleKingside(false);
                } else if (to.equals(queenSide)) {
                    otherTeamParameters.setCanCastleQueenside(false);
                }
            }
        }

        model.movePiece(move.getFromCell(), move.getToCell());

        // Halfmove clock: The number of halfmoves since the last capture or pawn
        // advance, used for the fifty-move rule.
        boolean halfMove = !typeIdentifier.equals(ChessTypeIdentifier.PAWN) && !isElimination;

        if (typeIdentifier.equals(ChessTypeIdentifier.PAWN) && move.getToCell().getRow() == otherTeamParameters.getKingRow()) {
            if (isMyTurn()) {
                Identifier promotedTypeIdentifier = view.promotePawn();

                if (!networkControl.isSinglePlayer()) {
                    networkControl.sendMessage(new PromotePawnMessage(move.getToCell().getRow(),
                            move.getToCell().getCol(), promotedTypeIdentifier, isElimination));
                } else {
                    promotePawn(move.getToCell().getRow(), move.getToCell().getCol(), promotedTypeIdentifier,
                            isElimination);
                    gameOver(model.isGameOver(model.getCurrentTeam().getTeamIdentifier()));
                    checkHighlight(piece);
                }
            }

            return;
        }

        checkHighlight(piece);

        model.registerMove(halfMove, move);

        if (model.isGameOver(model.getCurrentTeam().getTeamIdentifier()) != 0) {
            playSound("checkmate");
            gameOver(model.isGameOver(model.getCurrentTeam().getTeamIdentifier()));
        } else if (model.isCheck(model.getCurrentTeam().getTeamIdentifier())) {
            playSound("check");
        } else if (move.getIsCastleKingSide() || move.getIsCastleQueenSide()) {
            playSound("castling");
        } else if (move.isEliminatable()) {
            playSound("pieceCapture");
        } else {
            playSound("pieceMove");
        }
    }

    /**
     * The game over process that announces the winner or stalemate. Also handles if
     * the user wants to start a new game
     * or exit.
     * 
     * @param endingState - contains the information if the game ends in with a win
     *                    (= 1) or a stalemate (= 2).
     */
    private void gameOver(int endingState) {

        Object[] options = { "New game.", "Exit" };
        switch (endingState) {
            case 1:
                int n = JOptionPane.showOptionDialog(view.getOwner(), "Game over!\nThe game ended in a stalemate.",
                        "Game over!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
                        options[0]);
                if (n == JOptionPane.CLOSED_OPTION) {
                    System.exit(0);
                }
                switch (n) {
                    case 0:
                        checkHighlight(model.getKingCell(model.getCurrentTeam().getTeamIdentifier()).getPiece());
                        int input = Integer.parseInt(JOptionPane.showInputDialog("Minutes:"));
                        GameTime newTime = new GameTime(input, 0, 0);
                        view.getModel().resetState(newTime);
                        model.resetState(newTime);
                        view.getInfoPanel().getMovesPanel().resetMovesPanel();
                        break;
                    case 1:
                        System.exit(0);
                }
                break;
            case 2:
                n = JOptionPane.showOptionDialog(view.getOwner(),
                        "Game over!\n" + model.getOtherTeam(model.getCurrentTeam()) + " has won!", "Game over!",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (n == JOptionPane.CLOSED_OPTION) {
                    System.exit(0);
                }
                switch (n) {
                    case 0:
                        checkHighlight(model.getKingCell(model.getCurrentTeam().getTeamIdentifier()).getPiece());
                        int input = Integer.parseInt(JOptionPane.showInputDialog("Minutes:"));
                        GameTime newTime = new GameTime(input, 0, 0);
                        view.getModel().resetState(newTime);
                        model.resetState(newTime);
                        view.getInfoPanel().getMovesPanel().resetMovesPanel();
                        break;
                    case 1:
                        System.exit(0);
                }
                break;
        }

    }

    /**
     * Highlights and unhighlights the cell containing the teams king piece.
     * 
     * @param piece - the moved piece.
     */
    private void checkHighlight(Piece piece) {
        Cell c = model.getKingCell(piece.getTeamIdentifier());
        Position position = c.getPosition();
        BoardCell check = view.getBoardGridPanel().getCell(position.getRow(), position.getCol());
        if (check.getBackground().equals(new Color(191, 64, 64))
                || check.getBackground().equals(new Color(223, 96, 96))) {
            check.unhighlight();
        }

        if (model.isCheck(model.getOtherTeamIdentifier(piece.getTeamIdentifier()))) {
            c = model.getKingCell(model.getOtherTeamIdentifier(piece.getTeamIdentifier()));
            position = c.getPosition();
            check = view.getBoardGridPanel().getCell(position.getRow(), position.getCol());
            check.highlight(Color.RED);
        }
    }

    /**
     * Request to move a piece.
     * 
     * Forwards to the network client if the game is in network mode.
     */
    @Override
    public void movePiece(Move move) {
        int fromRow = move.getFromCell().getRow();
        int fromCol = move.getFromCell().getCol();
        int toRow = move.getToCell().getRow();
        int toCol = move.getToCell().getCol();
        boolean isElimination = move.isEliminatable();
        Identifier typeIdentifier = move.getPieceType();

        // Forward to executeMove if no network.
        if (networkControl.isSinglePlayer()) {
            executeMove(move);
            return;
        }

        // If we are not the host, send a request to the host.
        if (!networkControl.isHost()) {
            networkControl.sendMessage(new MovePieceMessage(fromRow, fromCol, toRow, toCol, isElimination, typeIdentifier));

            return;
        }

        // TODO: Validate move.

        // If we are the host, broadcast the move to all clients.
        networkControl.broadcastMessage(new AffirmMoveMessage(fromRow, fromCol, toRow, toCol, isElimination, typeIdentifier));
    }

    private void handleTimeTick() {
        if (model.getPaused()) {
            return;
        }

        model.getCurrentTeam().tickTime();

        if(model.getCurrentTeam().getTime().toString().equals("00:00:00")){
            gameOver(2);
        }
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

    private void handleClick(BoardCell boardCell) {
        // Start a timer
        long startTime = System.currentTimeMillis();

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
            if (model.getBoard().getCell(selectedCell.getRow(), selectedCell.getCol()).getPiece() == null) {
                selectedCell.unhighlight();
            } else if (!model.getBoard().getCell(selectedCell.getRow(), selectedCell.getCol()).getPiece()
                    .getTypeIdentifier().equals(ChessTypeIdentifier.KING)) {
                selectedCell.unhighlight();
            } else {
                selectedCell.unhighlight();
                if (model.isCheck(model.getCurrentTeam().getTeamIdentifier())) {
                    selectedCell.highlight(Color.RED);
                }
            }
        }

        for (BoardCell cell : highlightedCells) {
            cell.unhighlight();
        }

        highlightedCells.clear();

        Piece piece = model.getBoard().getCell(boardCell.getRow(), boardCell.getCol()).getPiece();

        if (piece == null) {
            return;
        }

        if (!piece.getTeamIdentifier().equals(model.getCurrentTeam().getTeamIdentifier())) {
            return;
        }

        selectedCell = boardCell;

        selectedCell.highlight(ChessView.HIGHLIGHT_COLOR_PIECE);

        Iterator<Move> moves = piece.getPossibleMoves(model.getRule(), new Position(selectedCell.getRow(), selectedCell.getCol()));

        while (moves.hasNext()) {
            Move move = moves.next();

            Position position = move.getToCell();

            boolean isLegal = true;

            // Edge case for castling, all moves inbetween the king and rook must be legal positions for the king
            // and the king cannot be in check.
            if (move.getIsCastleKingSide() || move.getIsCastleQueenSide()) {
                if (model.isCheck(model.getCurrentTeam().getTeamIdentifier())) {
                    isLegal = false;
                } else {
                    Position next = move.getFromCell();
                    while (!next.equals(position)) {
                        next = next.moveTowards(position, 1);
                        
                        if (!model.getRule().isLegalMove(move.getPieceType(), model.getCurrentTeam().getTeamIdentifier(), new Move(next, move.getFromCell(), move.getPieceType()))) {
                            isLegal = false;
                            break;
                        }
                    }
                }
            }

            if (!isLegal) {
                continue;
            }

            BoardCell possibleMove = grid.getCell(position.getRow(), position.getCol());

            currentMoveMap.put(possibleMove, move);

            possibleMove.highlight(
                    move.isEliminatable() ? ChessView.HIGHLIGHT_COLOR_ATTACK : ChessView.HIGHLIGHT_COLOR_MOVE);


            highlightedCells.add(possibleMove);
        }

        // end timer
        long endTime = System.currentTimeMillis();

        // print the time it took to get the moves
        System.out.println("Time to get moves: " + (endTime - startTime) + "ms");
    }

    private void handleChangeName(ChessTeam team) {
        if (!hasAuthorityOver(team)) {
            return;
        }

        // Open a dialog to get the new name.
        String name = JOptionPane.showInputDialog(null, "Enter a new name for " + team.getName() + ":", "Change Name",
                JOptionPane.PLAIN_MESSAGE);

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

        boardInformation = model.getBoardInformation();

        //
        // Create control subcomponents.
        //

        // Create network control.
        networkControl = new NetworkControl(this, model, view);

        // Setup listener when clicking on a cell.
        view.getBoardGridPanel().setClickDelegate((BoardCell boardCell) -> handleClick(boardCell));

        view.getMenu().getOnLoadGameEvent().addDelegate((serialModel) -> {
            model.loadModel(serialModel);
            setPaused(true);
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
        SoundPlayer.playSound(soundMap, sound);
    }

}
