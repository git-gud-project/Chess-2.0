package com.chess.model.chess;

import com.chess.model.Board;
import com.chess.model.Cell;
import com.chess.model.Position;

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
    private final int gameSize;

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
    public Cell getCell(int row, int col) throws IllegalArgumentException {
        if (row < 0 || row >= gameSize || col < 0 || col >= gameSize) {
            throw new IllegalArgumentException("Invalid board position (" + row + ", " + col + "), board size is " + gameSize);
        }
        
        // Row is reversed
        return cellMatrix[gameSize - 1 - row][col];
    }

    /**
     *
     * @param pos Contains the row and column for the cell.
     * @return The cell at the specified position.
     */
    @Override
    public Cell getCell(Position pos) {
        return getCell(pos.getRow(), pos.getCol());
    }

    /**
     * Initializes the cell matrix.
     * 
     * @param gameSize the size of the board
     */
    private void initCellMatrix(int gameSize) {
        cellMatrix = new Cell[gameSize][gameSize];
        for (int row = 0; row < gameSize; row++) {
            for (int col = 0; col < gameSize; col++) {
                cellMatrix[row][col] = new Cell(new Position(gameSize - 1 - row, col));
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
        return piece.getTeamIdentifier() != getCell(row, col).getPiece().getTeamIdentifier();
    }
}
