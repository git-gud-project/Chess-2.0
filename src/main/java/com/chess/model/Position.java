package com.chess.model;

/**
 * Represents a position on a board.
 * Has a row and a column component.
 * @author Rasmus Standar
 * @version 2022-03-02
 */
public class Position {
    /**
     * Indicates that the position is invalid.
     */
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
     * Adds units number of rows to the current position.
     * @param units Number of rows to be added to the current position.
     * @return A new position with units number of rows added to it.
     */
    public Position addRow(int units) {
        return new Position(row + units, col);
    }

    /**
     * Adds units number of columns to the current position.
     * @param units Number of columns to be added to the current position.
     * @return A new position with units number of columns added to it.
     */
    public Position addCol(int units) {
        return new Position(row, col + units);
    }

    /**
     * Move a position towards another position by a given number of units.
     * 
     * @param position The position to move towards.
     * @param units The number of units to move.
     * @return The new position.
     */
    public Position moveTowards(Position position, int units) {
        int rowDiff = position.getRow() - row;
        int colDiff = position.getCol() - col;

        if (rowDiff == 0) {
            return addCol(units);
        } else if (colDiff == 0) {
            return addRow(units);
        } else {
            int rowUnit = rowDiff / Math.abs(rowDiff);
            int colUnit = colDiff / Math.abs(colDiff);

            return addRow(rowUnit * units).addCol(colUnit * units);
        }
    }

    /**
     * Compute the distance in rows.
     * @param position The position being compared to.
     * @return The distance in rows to the compared position.
     */
    public int distanceRow(Position position) {
        return Math.abs(this.row - position.getRow());
    }

    /**
     * Computes the distance in columns.
     * @param position The position being compared to.
     * @return The distance in columns to the comapred position.
     */
    public int distanceCol(Position position) {
        return Math.abs(this.col - position.getCol());
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
        // Special case for invalid position
        if (row == -1 && col == -1) {
            return "INVALID";
        }
        return Character.toString('a' + getCol()) + Character.toString('1' + getRow());
    }
}
