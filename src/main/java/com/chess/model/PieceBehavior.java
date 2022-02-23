package com.chess.model;

import java.util.Iterator;

/**
 * Interface for how a piece behaves.
 */
public interface PieceBehavior {

    /**
     * Get the type identifier of the piece.
     * @return the type identifier of the piece
     */
    public Identifier getTypeIdentifier();

    /**
     * Get an iterator of all moves that can be performed by this piece from a specific position
     * @param board The board that the piece is on
     * @param position The position of the piece
     * @return An iterator of all possible moves
     */
    public Iterator<Move> getPossibleMoves(Rule rule, Position position, Identifier teamIdentifier);

    /**
     * Called when the piece is moved.
     * 
     * @param from the cell that the piece was in before it was moved
     * @param to the cell that the piece is now in
     */
    public default void afterMove(Rule rule, Position from, Position to) {
        //
    }

    /**
     * Called before the piece is moved.
     * 
     * @param from the cell that the piece was in before it was moved
     * @param to the cell that the piece is now in
     */
    public default void beforeMove(Rule rule, Position from, Position to) {
        //
    }
}
