package com.chess.model.chess;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.chess.model.Board;
import com.chess.model.Cell;
import com.chess.model.Identifier;
import com.chess.model.Move;
import com.chess.model.Piece;
import com.chess.model.Position;
import com.chess.model.ChessTeam;

/**
 * The board that the game is played on.
 */
public class ChessBoard implements Board {

    /**
     * Matrix of cells.
     */
    private Cell[][] cellMatrix;

    /**
     * The size of the board.
     */
    private int gameSize;

    /**
     * Constructs a new board.
     *
     * @param gameSize the size of the board
     */
    public ChessBoard(int gameSize) {
        this.gameSize = gameSize;
        initCellMatrix(gameSize);
    }

    /**
     * Gets the number of rows in the board.
     *
     * @return The number of rows in the board.
     */
    @Override
    public int getRows() {
        return gameSize;
    }

    /**
     * Gets the number of columns in the board.
     * @return The number of columns in the board.
     */
    @Override
    public int getColumns() {
        return gameSize;
    }

    /**
     * Get a cell from the cell matrix.
     * 
     * @param row The row of the cell.
     * @param col The column of the cell.
     * @return The cell at the specified row and column.
     */
    @Override
    public Cell getCell(int row, int col) {
        return cellMatrix[row][col];
    }

    /**
     *
     * @param pos Contains the row and column for the cell.
     * @return The cell at the specified position.
     */
    @Override
    public Cell getCell(Position pos) { return cellMatrix[pos.getRow()][pos.getCol()]; }

    /**
     * Initializes the cell matrix.
     * 
     * @param gameSize the size of the board
     */
    private void initCellMatrix(int gameSize) {
        cellMatrix = new Cell[gameSize][gameSize];
        for (int row = 0; row < gameSize; row++) {
            for (int col = 0; col < gameSize; col++) {
                cellMatrix[gameSize - row][col] = new Cell(new Position(row, col));
            }
        }
    }

    /**
     * Get a cell from the cell matrix from a string, like "a1".
     * 
     * @param position the position of the cell
     * @return the cell at the specified position
     */
    public Cell getCell(String position) {
        int row = position.charAt(1) - '1';
        int col = position.charAt(0) - 'a';

        return getCell(row, col);
    }

    /**
     * If a cell is empty.
     * 
     * @param row the row of the cell
     * @param col the column of the cell
     * @return if the cell is empty
     */
    public boolean isEmpty(int row, int col) {
        return (cellMatrix[row][col].getPiece() == null);
    }

    /**
     * If a position is valid.
     * 
     * @param row the row of the position
     * @param col the column of the position
     * @return if the position is valid
     */
    public boolean isValid(int row, int col) {
        return ((row < gameSize && row >= 0) && (col < gameSize && col >= 0));
    }

    /**
     * If a piece can capture in this position.
     * 
     * @param piece the piece
     * @param row   the row of the position
     * @param col   the column of the position
     */
    public boolean canCapture(ChessPiece piece, int row, int col) {
        if (!isValid(row, col))
            return false;
        if (isEmpty(row, col))
            return false;
        return piece.getTeamIdentifier() != cellMatrix[row][col].getPiece().getTeamIdentifier();
    }

    /**
     * If a certain move is legal.
     * 
     * @param piece the piece
     * @param move  the move
     * @return if the move is legal
     */
    public boolean isLegalMove(ChessPiece piece, Move move) {

        Cell tempCell = piece.getCell();
        Piece originPiece = move.getToCell().getPiece();
        boolean hasMoved = piece.hasMoved();
        piece.fakeMove(move.getToCell()); // 1. Flyttar cellen

        if (isCheck(piece.getTeam())) { // 2. Kollar om det är schack efter flytten.
            piece.fakeMove(tempCell); // Om det är schack så flyttar vi tillbaks pjäsen.
            move.getToCell().setPiece(originPiece);
            piece.setHasMoved(hasMoved);
            return false; // returnerar att det är falsk (en illegal move).
        } else {
            piece.fakeMove(tempCell); // Om det inte är schack så flyttar vi tillbaks pjäs
            move.getToCell().setPiece(originPiece);
            piece.setHasMoved(hasMoved);
            return true;
        }
    }

    /**
     * Get a list of all enemy moves.
     * 
     * @param playerTeam the team of the player
     * @return the list of all enemy moves
     */
    private List<Move> allEnemyMoves(ChessTeam playerTeam) {
        List<Move> enemyMovesList = new ArrayList<>();
        for (int row = 0; row < gameSize; row++) {
            for (int col = 0; col < gameSize; col++) {
                if (cellMatrix[row][col].getPiece() != null
                        && cellMatrix[row][col].getPiece().getTeamIdentifier() != playerTeam.getIdentifier()) {
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
    private List<Move> allTeamMoves(ChessTeam playerTeam) {
        List<Move> teamMovesList = new ArrayList<>();
        for (int row = 0; row < gameSize; row++) {
            for (int col = 0; col < gameSize; col++) {
                if (cellMatrix[row][col].getPiece() != null
                        && cellMatrix[row][col].getPiece().getTeamIdentifier() == playerTeam.getIdentifier()) {
                    Iterator<Move> it = cellMatrix[row][col].getPiece().getPossibleMoves(this);
                    while (it.hasNext()) {
                        teamMovesList.add(it.next());
                    }
                }
            }
        }
        return teamMovesList;
    }

    /**
     * Checks if it is checkmate or stalemate. If it is checkmate it returns 2. If
     * it is stalemate it return 1. Else 0.
     * 
     * @param enemyPlayerTeam the team of the enemy player
     */
    public int isGameOver(ChessTeam enemyPlayerTeam) {
        if (allTeamMoves(enemyPlayerTeam).isEmpty() && isCheck(enemyPlayerTeam)) {
            return 2;
        } else if (allTeamMoves(enemyPlayerTeam).isEmpty() && !isCheck(enemyPlayerTeam)) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Check if it is check.
     * 
     * @param team the team of the player
     * @return if it is check
     */
    public boolean isCheck(ChessTeam team) {
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
     * @param team The team of the player.
     * @return The cell where the king is.
     */
    public Cell getKingCell(ChessTeam team) {
        Cell kingCell = null;
        for (int row = 0; row < gameSize; row++) {
            for (int col = 0; col < gameSize; col++) {
                if (cellMatrix[row][col].getPiece() != null
                        && cellMatrix[row][col].getPiece().getTypeIdentifier().equals(PieceType.KING)
                        && cellMatrix[row][col].getPiece().getTeamIdentifier().toString().equals(team.getIdentifier().toString())) {
                    kingCell = cellMatrix[row][col];
                }
            }
        }
        return kingCell;
    }

    /**
     * Validate a list of moves, filter out illegal moves.
     * 
     * @param piece the piece
     * @param moves the list of moves
     */
    public void validateMoves(Piece piece, List<Move> moves) {
        // Remove all illegal moves from the list
        Iterator<Move> it = moves.iterator();
        while (it.hasNext()) {
            Move move = it.next();
            if (!isLegalMove(piece, move)) {
                it.remove();
            }
        }
    }

    /**
     * Transform a row/col to a string.
     * 
     * @param row the row of the cell
     * @param col the column of the cell
     * @return the string
     */
    public String positionToString(int row, int col) {
        return "" + (char) ('a' + col) + (row + 1);
    }

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
        
        // If maxSteps is 0, then the piece can move infinitely in that direction
        if (maxSteps == 0) {
            maxSteps = gameSize;
        }

        // Step through the board
        int step = 1;

        while (step <= maxSteps) {
            final int nextRow = row + step * directionRow;
            final int nextCol = col + step * directionCol;
            
            step++;

            if (!isValid(nextRow, nextCol)) {
                break;
            }

            Cell nextCell = getCell(nextRow, nextCol);

            if (nextCell.isEmpty()) {
                // If the cell is both empty and we require a capture, then we can't move here
                if (requireCapture) {
                    break;
                }

                registry.add(new Move(nextCell.getPosition(), position, false));

                continue;
            }
            
            if (nextCell.getPiece().getTeamIdentifier() != teamIdentifier) {
                // If we can't capture, then we can't move here
                if (cantCapture) {
                    break;
                }

                registry.add(new Move(nextCell.getPosition(), position, true));

                break;
            }
            
            // If we can't skip our own, then we can't move here
            if (!skipOwn) {
                break;
            }
        }

        if (model.getCurrentTeam() == team) {
            validateMoves(piece, registry);
        }

    }
}
