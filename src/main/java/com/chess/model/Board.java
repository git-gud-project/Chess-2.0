package com.chess.model;

/**
 * Represents a board.
 * A board has an integer size in rows and columns.
 * @author Wincent Stålbert Holm
 * @version 2022-03-02
 */
public interface Board {
    /**
     * Gets the number of rows in the board.
     * 
     * @return The number of rows in the board.
     */
    public int getRows();

    /**
     * Gets the number of columns in the board.
     * 
     * @return The number of columns in the board.
     */
    public int getColumns();

    /**
     * Gets the cell at the specified row and column.
     * 
     * @param row The row of the cell.
     * @param col The column of the cell.
     * @return The cell at the specified row and column.
     * @throws IllegalArgumentException if either the row or column is out of bounds.
     */
    public Cell getCell(int row, int col) throws IllegalArgumentException;

    /**
     * Gets the cell at the specified position.
     * 
     * @param pos Contains the row and column for the cell.
     * @return The cell at the specified position.
     * @throws IllegalArgumentException if the position is out of bounds.
     */
    public Cell getCell(Position pos) throws IllegalArgumentException;
}
