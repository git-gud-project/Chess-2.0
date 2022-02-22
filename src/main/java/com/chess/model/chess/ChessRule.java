package com.chess.model.chess;

import com.chess.model.*;
import jdk.tools.jlink.internal.PostProcessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChessRule implements Rule{
    private BoardInformation BoardInfo;

    private Identifier currentTeam;

    private final int gameSize;

    public ChessRule(BoardInformation BoardInfo){
        this.BoardInfo = BoardInfo;
        gameSize = this.BoardInfo.getBoardSize();
        currentTeam = new Identifier("w"); //todo Might be better to take as input to get identifier
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
        Identifier pieceIdentifier = BoardInfo.getTypeIdentifier(position);

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

            if (!BoardInfo.isValid(nextPosition)) {
                break;
            }


            if (BoardInfo.isEmpty(nextPosition)) {
                // If the cell is both empty and we require a capture, then we can't move here
                if (requireCapture) {
                    break;
                }

                registry.add(new Move(nextPosition, position,pieceIdentifier,false));
                continue;
            }

            if (BoardInfo.getTeamIdentifier(nextPosition) != teamIdentifier) {
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
        Identifier originPiece = BoardInfo.getTypeIdentifier(move.getToCell());
        Identifier originTeam = BoardInfo.getTeamIdentifier(move.getToCell());

        BoardInfo.setPiece(move.getToCell(),piece,team,false); // 1. Flyttar cellen
        BoardInfo.clearPiece(move.getFromCell(),false);

        if(isCheck(team)){ // 2. Kollar om det är schack efter flytten.
            BoardInfo.setPiece(move.getFromCell(),piece,team,false); // Om det är schack så flyttar vi tillbaks pjäserna.
            BoardInfo.setPiece(move.getToCell(),originPiece,originTeam,false);

            return false; // returnerar att det är falsk (en illegal move).
        } else {
            BoardInfo.setPiece(move.getFromCell(),piece,team,false); // Om det är inte schack så flyttar vi tillbaks pjäserna.
            BoardInfo.setPiece(move.getToCell(),originPiece,originTeam,false);

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
     * @param playerTeam the team of the player
     * @return the list of all enemy moves
     */
    private List<Move> allEnemyMoves(Team playerTeam) {
        List<Move> enemyMovesList = new ArrayList<>();
        for (int row = 0; row < gameSize; row++) {
            for (int col = 0; col < gameSize; col++) {
                if (cellMatrix[row][col].getPiece() != null
                        && cellMatrix[row][col].getPiece().getTeam() != playerTeam) {
                    Iterator<Move> it = cellMatrix[row][col].getPiece().getPossibleMoves(this);
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
     * @param playerTeam the team of the player
     * @return the list of all moves
     */
    private List<Move> allTeamMoves(Team playerTeam) {
        List<Move> teamMovesList = new ArrayList<>();
        for (int row = 0; row < gameSize; row++) {
            for (int col = 0; col < gameSize; col++) {
                if (cellMatrix[row][col].getPiece() != null
                        && cellMatrix[row][col].getPiece().getTeam() == playerTeam) {
                    Iterator<Move> it = cellMatrix[row][col].getPiece().getPossibleMoves(this);
                    while (it.hasNext()) {
                        teamMovesList.add(it.next());
                    }
                }
            }
        }
        return teamMovesList;
    }

    public boolean isCheck(Identifier team) {
        if (isGameOver(model.getOtherTeam(team)) != 0) {
            return false;
        }
        List<Move> allEnemyMoves = allEnemyMoves(team);
        for (Move m : allEnemyMoves) {
            if (m.getToCell().getPiece() != null && m.getToCell().getPiece().getTeam().equals(team)
                    && m.getToCell().getPiece().getTypeIdentifier().equals(PieceType.KING)) {
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
                if (!BoardInfo.isEmpty(row,col) &&
                    BoardInfo.getTypeIdentifier(row,col).equals(PieceType.KING) &&
                    BoardInfo.getTeamIdentifier(row,col).equals(teamIdentifier)) {
                        kingCell = new Position(row,col);
                }
            }
        }
        return kingCell;
    }

    private void fakeMove(Move move, Identifier pieceIdentifier, Identifier teamIdentifier){
        BoardInfo.clearPiece(move.getFromCell(),false);
        BoardInfo.setPiece(move.getToCell(),pieceIdentifier,teamIdentifier,false);
    }
}



