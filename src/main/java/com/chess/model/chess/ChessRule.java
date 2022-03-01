package com.chess.model.chess;

import com.chess.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * The rules for the chess game.
 */
public class ChessRule implements Rule {
    /**
     * The board information.
     */
    private final BoardInformation boardInfo;

    /**
     * The game size.
     */
    private final int gameSize;

    /**
     * The team manager.
     */
    private final TeamManager teamManager;

    /**
     * Creates a new chess rule.
     * 
     * @param boardInfo the board information
     * @param teamManager the team manager
     */
    public ChessRule(BoardInformation boardInfo, TeamManager teamManager) {
        this.boardInfo = boardInfo;
        this.teamManager = teamManager;

        gameSize = this.boardInfo.getBoardSize();
    }

    @Override
    public boolean requestClear(
        Position position
    ) throws IllegalArgumentException {
        boardInfo.clearPiece(position, true);

        return true;
    }

    @Override
    public boolean requestMove(
        Position from,
        Position to
    ) throws IllegalArgumentException {
        if (isEmpty(from)) {
            return false;
        }

        Identifier typeIdentifier = this.getTypeIdentifier(from);
        Identifier teamIdentifier = this.getTeamIdentifier(from);

        boardInfo.clearPiece(from, true);
        boardInfo.setPiece(to, typeIdentifier, teamIdentifier, true);

        return true;
    }

    @Override
    public Identifier getTypeIdentifier(
        Position position
    ) throws IllegalArgumentException {
        return boardInfo.getTypeIdentifier(position);
    }

    @Override
    public Identifier getTeamIdentifier(
        Position position
    ) throws IllegalArgumentException {
        return boardInfo.getTeamIdentifier(position);
    }

    @Override
    public boolean isEmpty(
        Position position
    ) throws IllegalArgumentException {
        return boardInfo.isEmpty(position);
    }

    @Override
    public void calculateMoves(
            Position position,
            Identifier teamIdentifier,
            Collection<Move> registry,
            int directionRow,
            int directionCol,
            int maxSteps,
            boolean skipOwn,
            boolean cantCapture,
            boolean requireCapture
    ) {
        // Collect details
        int row = position.getRow();
        int col = position.getCol();
        Identifier pieceIdentifier = boardInfo.getTypeIdentifier(position);

        // If maxSteps is 0, then the piece can move infinitely in that direction
        if (maxSteps == 0) {
            maxSteps = gameSize;
        }

        // Step through the board
        int step = 1;

        while (step <= maxSteps) {
            final int nextRow = row + step * directionRow;
            final int nextCol = col + step * directionCol;
            Position nextPosition = new Position(nextRow,nextCol);

            step++;

            if (!boardInfo.isValid(nextPosition)) {
                break;
            }


            if (boardInfo.isEmpty(nextPosition)) {
                // If the cell is both empty and we require a capture, then we can't move here
                if (requireCapture) {
                    break;
                }

                registry.add(new Move(nextPosition, position,pieceIdentifier,false));
                continue;
            }

            if (boardInfo.getTeamIdentifier(nextPosition) != teamIdentifier) {
                // If we can't capture, then we can't move here
                if (cantCapture) {
                    break;
                }

                registry.add(new Move(nextPosition, position,pieceIdentifier, true));
                break;
            }

            // If we can't skip our own, then we can't move here
            if (!skipOwn) {
                break;
            }
        }

        if (teamManager.getCurrentTeamIdentifier().equals(teamIdentifier)) {
            validateMoves(pieceIdentifier, teamIdentifier, registry);
        }

    }

    private void validateMoves(Identifier pieceIdentifier, Identifier teamIdentifier, Collection<Move> moves) {
        // Remove all illegal moves from the list
        Iterator<Move> it = moves.iterator();
        while (it.hasNext()) {
            Move move = it.next();
            if (!isLegalMove(pieceIdentifier, teamIdentifier, move)) {
                it.remove();
            }
        }
    }

    /**
     * Checks if the move is legal.
     * 
     * A legal move is one that does not leave the king in check.
     * 
     * @param pieceIdentifier the piece identifier for the piece that is moving
     * @param teamIdentifier the team identifier for the team that is moving
     * @param move the move details
     * @return true if the move is legal
     */
    public boolean isLegalMove(Identifier pieceIdentifier, Identifier teamIdentifier, Move move) {
        // Collect relevant details
        final Position from = move.getFromCell();
        final Position to = move.getToCell();
        final boolean isEmpty = boardInfo.isEmpty(to);

        // If the cell is not empty store the details of the piece which is theoretically being captured
        Identifier originPiece = null;
        Identifier originTeam = null;

        if (!isEmpty) {
            originPiece = boardInfo.getTypeIdentifier(to);
            originTeam = boardInfo.getTeamIdentifier(to);
        }

        // Do a theoretical move
        boardInfo.setPiece(
            to,
            pieceIdentifier,
            teamIdentifier,
            false
        );
        
        boardInfo.clearPiece(
            from,
            false
        );

        // Check if it results in us being in check
        final boolean isCheck = isCheck(teamIdentifier);

        // It is only a legal move if it does not result in us being in check
        final boolean isLegalMove = !isCheck;

        // Revert the theoretical move
        boardInfo.setPiece(
            from,
            pieceIdentifier,
            teamIdentifier,
            false
        );

        if (!isEmpty) {
            boardInfo.setPiece(
                to,
                originPiece,
                originTeam,
                false
            );
        } else {
            boardInfo.clearPiece(
                to,
                false
            );
        }

        return isLegalMove;
    }

    /**
     * Get a list of all enemy moves.
     *
     * @param playerTeamIdentifier the team of the player
     * @return the list of all enemy moves
     */
    private List<Move> allEnemyMoves(Identifier playerTeamIdentifier) {
        List<Move> enemyMovesList = new ArrayList<>();
        for (int row = 0; row < gameSize; row++) {
            for (int col = 0; col < gameSize; col++) {
                if (!boardInfo.isEmpty(row, col) && !boardInfo.getTeamIdentifier(row, col).equals(playerTeamIdentifier)) {
                    
                    // Get the moves calculator
                    MovesCalculator calculator = boardInfo.getPossibleMovesIterator(new Position(row, col));
                    
                    // Calculate the moves
                    Iterator<Move> it = calculator.getPossibleMoves(this, new Position(row, col));

                    while (it.hasNext()) {
                        enemyMovesList.add(it.next());
                    }
                }
            }
        }
        return enemyMovesList;
    }

    private boolean canMove(Identifier playerTeamIdentifier) {
        for (int row = 0; row < gameSize; row++) {
            for (int col = 0; col < gameSize; col++) {
                if (!boardInfo.isEmpty(row, col) && boardInfo.getTeamIdentifier(row, col).equals(playerTeamIdentifier)) {

                    // Get the moves calculator
                    MovesCalculator calculator = boardInfo.getPossibleMovesIterator(new Position(row, col));
                    
                    // Calculate the moves
                    Iterator<Move> it = calculator.getPossibleMoves(this, new Position(row, col));
                    
                    if (it.hasNext()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int isGameOver(Identifier enemyTeamIdentifier) {
        final boolean canMove = canMove(enemyTeamIdentifier);

        if (canMove) {
            return 0;
        }

        final boolean isCheck = isCheck(enemyTeamIdentifier);

        return isCheck ? 2 : 1;
    }

    /**
     * Returns true if the team is in check.
     * 
     * @param teamIdentifier the team identifier
     * @return true if the team is in check
     */
    public boolean isCheck(Identifier teamIdentifier) {
        List<Move> allEnemyMoves = allEnemyMoves(teamIdentifier);
        for (Move m : allEnemyMoves) {
            Position toCell = m.getToCell();
            if (boardInfo.isEmpty(toCell)) {
                continue;
            }

            if (boardInfo.getTeamIdentifier(toCell).equals(teamIdentifier) &&
                boardInfo.getTypeIdentifier(toCell).equals(ChessTypeIdentifier.KING)) {
                return true;
            }
        }
        
        return false;
    }
}



