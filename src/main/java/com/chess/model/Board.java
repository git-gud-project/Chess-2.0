package com.chess.model;

import java.util.List;

/**
 * Represents a board.
 */
public interface Board {
    /**
     * Gets the number of rows in the board.
     * @return The number of rows in the board.
     */
    public int getRows();

    /**
     * Gets the number of columns in the board.
     * @return The number of columns in the board.
     */
    public int getColumns();

    /**
     * Gets the cell at the specified row and column.
     * @param row The row of the cell.
     * @param col The column of the cell.
     * @return The cell at the specified row and column.
     */
    public Cell getCell(int row, int col);
}
