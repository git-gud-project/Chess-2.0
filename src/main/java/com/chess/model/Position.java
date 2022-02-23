package com.chess.model;

/**
 * Represents a position on a board.
 * 
 * Has a row and a column component.
 */
public class Position {
    public static final Position INVALID = new Position(-1, -1);


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
     * Construct a position from a string.
     * 
     * @param position The string representation of the position.
     */
    public Position(String position) {
        this.row = Integer.parseInt(position.substring(1, 2)) - 1;
        this.col = position.charAt(0) - 'a';
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
     * Compute the distance between two positions.
     * 
     * @param position The position to compute the distance to.
     * @return The distance between the two positions.
     */
    public int distance(Position position) {
        return Math.abs(this.row - position.getRow()) + Math.abs(this.col - position.getCol());
    }

    /**
     * Equals method for Position
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position that = (Position) o;

        return that.row == this.row && that.col == this.col;
    }

    /**
     * Hashcode method for Position
     */
    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + col;
        return result;
    }

    /**
     * ToString override
     * @return The string representation of the cell
     */
    @Override
    public String toString() {
        return Character.toString('a' + getCol()) + Character.toString('1' + getRow());
    }
}
