package com.chess.model.chess;

import com.chess.model.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChessRule implements Rule {
    private final BoardInformation boardInfo;

    private final int gameSize;

    private Identifier currentTeam;

    public ChessRule(BoardInformation BoardInfo) {
        this.boardInfo = BoardInfo;
        gameSize = this.boardInfo.getBoardSize();
        currentTeam = new Identifier("w"); //todo Might be better to take as input to get identifier
    }

    /**
     * Check if a move is an elimination move.
     * 
     * @param position       The position to check.
     * @param typeIdentifier The type identifier of the piece.
     * @param teamIdentifier The team identifier of the piece.
     * @return               True if the move is an elimination move, false otherwise.
     * @throws IllegalArgumentException if the position is invalid.
     */
    @Override
    public boolean isEliminationMove(
        Position position,
        Identifier typeIdentifier,
        Identifier teamIdentifier
    ) throws IllegalArgumentException {
        return boardInfo.isElimination(position, typeIdentifier, teamIdentifier);
    }

    /**
     * Request that a position be cleared.
     * 
     * @param position       The position to clear.
     * @return               True if the move was successful, false otherwise.
     * @throws IllegalArgumentException if the position is invalid.
     */
    @Override
    public boolean requestClear(
        Position position
    ) throws IllegalArgumentException {
        boardInfo.clearPiece(position, true);

        return true;
    }

    /**
     * Request that a piece be moved from one position to another.
     * 
     * @param from          The position to move from.
     * @param to            The position to move to.
     * @return              True if the move was successful, false otherwise.
     */
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

    /**
     * Get the type identifier of the piece at the specified position.
     * 
     * @param position      The position to get the type identifier of the piece at.
     * @return              The type identifier of the piece at the specified position.
     */
    @Override
    public Identifier getTypeIdentifier(
        Position position
    ) throws IllegalArgumentException {
        return boardInfo.getTypeIdentifier(position);
    }

    /**
     * Get the team identifier of the piece at the specified position.
     * 
     * @param position      The position to get the team identifier of the piece at.
     * @return              The team identifier of the piece at the specified position.
     */
    @Override
    public Identifier getTeamIdentifier(
        Position position
    ) throws IllegalArgumentException {
        return boardInfo.getTeamIdentifier(position);
    }

    /**
     * Check if a position is empty.
     * 
     * @param position      The position to check.
     * @return              True if the position is empty, false otherwise.
     */
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
            List<Move> registry,
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

        if (currentTeam == teamIdentifier) {
            validateMoves(pieceIdentifier, teamIdentifier, registry);
        }

    }

    public void validateMoves(Identifier pieceIdentifier, Identifier teamIdentifier, List<Move> moves) {
        // Remove all illegal moves from the list
        Iterator<Move> it = moves.iterator();
        while (it.hasNext()) {
            Move move = it.next();
            if (!isLegalMove(pieceIdentifier, teamIdentifier, move)) {
                it.remove();
            }
        }
    }

    public boolean isLegalMove(Identifier piece,Identifier team, Move move) {
        Identifier originPiece = boardInfo.getTypeIdentifier(move.getToCell());
        Identifier originTeam = boardInfo.getTeamIdentifier(move.getToCell());

        boardInfo.setPiece(move.getToCell(),piece,team,false); // 1. Flyttar cellen
        boardInfo.clearPiece(move.getFromCell(),false);

        if(isCheck(team)){ // 2. Kollar om det är schack efter flytten.
            boardInfo.setPiece(move.getFromCell(),piece,team,false); // Om det är schack så flyttar vi tillbaks pjäserna.
            boardInfo.setPiece(move.getToCell(),originPiece,originTeam,false);

            return false; // returnerar att det är falsk (en illegal move).
        } else {
            boardInfo.setPiece(move.getFromCell(),piece,team,false); // Om det är inte schack så flyttar vi tillbaks pjäserna.
            boardInfo.setPiece(move.getToCell(),originPiece,originTeam,false);

            return true;
        }
    }

    /** To update this
     * @param currentTeam
     */
    public void setCurrentTeam(Identifier currentTeam){
        this.currentTeam = currentTeam;
    }

    /**
     * Get a list of all enemy moves.
     *
     * @param playerTeamIdentifier the team of the player
     * @return the list of all enemy moves
     */
    public List<Move> allEnemyMoves(Identifier playerTeamIdentifier) {
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

    /**
     * Get a list of all moves.
     *
     * @param playerTeamIdentifier the team of the player
     * @return the list of all moves
     */
    public List<Move> allTeamMoves(Identifier playerTeamIdentifier) {
        List<Move> teamMovesList = new ArrayList<>();
        for (int row = 0; row < gameSize; row++) {
            for (int col = 0; col < gameSize; col++) {
                if (!boardInfo.isEmpty(row, col) && boardInfo.getTeamIdentifier(row, col).equals(playerTeamIdentifier)) {

                    // Get the moves calculator
                    MovesCalculator calculator = boardInfo.getPossibleMovesIterator(new Position(row, col));
                    
                    // Calculate the moves
                    Iterator<Move> it = calculator.getPossibleMoves(this, new Position(row, col));
                    
                    while (it.hasNext()) {
                        teamMovesList.add(it.next());
                    }
                }
            }
        }
        return teamMovesList;
    }

    public boolean isCheck(Identifier teamIdentifier) {
        List<Move> allEnemyMoves = allEnemyMoves(teamIdentifier);
        for (Move m : allEnemyMoves) {
            Position toCell = m.getToCell();
            if (boardInfo.isEmpty(toCell)) {
                continue;
            }

            if (boardInfo.getTeamIdentifier(toCell).equals(teamIdentifier) &&
                boardInfo.getTypeIdentifier(toCell).equals(PieceType.KING)) {
                return true;
            }
        }
        
        return false;
    }

    /**
     * Get the cell where the king is.
     *
     * @param team the team of the player
     * @return the cell where the king is
     */
    public Position getKingCell(Identifier teamIdentifier) {
        Position kingCell = null;
        for (int row = 0; row < gameSize; row++) {
            for (int col = 0; col < gameSize; col++) {
                if (!boardInfo.isEmpty(row,col) &&
                    boardInfo.getTypeIdentifier(row,col).equals(PieceType.KING) &&
                    boardInfo.getTeamIdentifier(row,col).equals(teamIdentifier)) {
                        kingCell = new Position(row,col);
                }
            }
        }
        return kingCell;
    }

    private void fakeMove(Move move, Identifier pieceIdentifier, Identifier teamIdentifier){
        boardInfo.clearPiece(move.getFromCell(),false);
        boardInfo.setPiece(move.getToCell(),pieceIdentifier,teamIdentifier,false);
    }
}



