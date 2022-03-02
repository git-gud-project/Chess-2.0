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

    @Override
    public int getRows() {
        return gameSize;
    }

    @Override
    public int getColumns() {
        return gameSize;
    }

    @Override
    public Cell getCell(int row, int col) throws IllegalArgumentException {
        if (row < 0 || row >= gameSize || col < 0 || col >= gameSize) {
            throw new IllegalArgumentException("Invalid board position (" + row + ", " + col + "), board size is " + gameSize);
        }

        // Row is reversed
        return cellMatrix[gameSize - 1 - row][col];
    }

    @Override
    public Cell getCell(Position pos) throws IllegalArgumentException {
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
     * @throws IllegalArgumentException if the position is invalid
     */
    public Cell getCell(String position) throws IllegalArgumentException {
        int row = position.charAt(1) - '1';
        int col = position.charAt(0) - 'a';

        return getCell(row, col);
    }
}
