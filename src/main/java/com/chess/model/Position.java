package com.chess.model;

/**
 * Represents a position on a board.
 * 
 * Has a row and a column component.
 */
public class Position {
    /**
     * The row of the position.
     */
    private final int row;

    /**
     * The column of the position.
     */
    private final int col;

    /**
     * Constructor for Position
     * 
     * @param row The row of the position.
     * @param col The column of the position.
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Get the row of the position.
     * 
     * @return The row of the position.
     */
    public int getRow() {
        return row;
    }

    /**
     * Get the column of the position.
     * 
     * @return The column of the position.
     */
    public int getCol() {
        return col;
    }

    /**
     * ToString override
     * @return The string representation of the cell
     */
    @Override
    public String toString() {
        return String.valueOf('a' + getCol()) + String.valueOf('1' + getRow());
    }
}
