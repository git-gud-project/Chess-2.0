package com.chess.model;

import java.util.Iterator;

public interface PieceBehavior {

    /**
     * Get the piece type associated for the piece behavior
     * @return The piece type of the behavior
     */
    public PieceType getPieceType();

    /**
     * Get an iterator of all moves that can be performed by this piece from a specific cell
     * @param cell The cell to check moves from
     * @return An iterator of all possible moves
     */
    public Iterator<Move> getPossibleMoves(Board board, Cell cell);

    /**
     * Get bool for if this the piece connected to this behavior has moved
     * @return True if piece has moved
     */
    public default boolean hasMoved() {
        return true;
    }

    /**
     * Set if this piece has moved
     * @param hasMoved The value for if this piece has moved
     */
    public default void setHasMoved(boolean hasMoved) {
        //
    }

    /**
     * Called when the piece is moved.
     * 
     * @param oldCell the cell that the piece was in before it was moved
     * @param newCell the cell that the piece is now in
     */
    public default void onMove(Board board, Cell oldCell, Cell newCell) {
        setHasMoved(true);
    }

    /**
     * Called before the piece is moved.
     * 
     * @param oldCell the cell that the piece was in before it was moved
     * @param newCell the cell that the piece is now in
     */
    public default void beforeMove(Board board, Cell oldCell, Cell newCell) {
        //
    }
}
