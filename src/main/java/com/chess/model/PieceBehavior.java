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
     * 
     * @param rule the rule that is being used
     * @param position The position of the piece
     * @param teamIdentifier The identifier of the team that the piece belongs to
     * @return An iterator of all possible moves
     * @throws IllegalArgumentException if the position is invalid
     */
    public Iterator<Move> getPossibleMoves(Rule rule, Position position, Identifier teamIdentifier) throws IllegalArgumentException;

    /**
     * Method that is called after a piece has been moved.
     * 
     * @param rule the rule that is being used
     * @param from the position the piece has moved from
     * @param to the position the piece has moved to
     */
    public default void afterMove(Rule rule, Position from, Position to) {
        // Do nothing by default
    }

    /**
     * Method that is called after a piece has been moved.
     * 
     * @param rule the rule that is being used
     * @param from the position the piece has moved from
     * @param to the position the piece has moved to
     */
    public default void beforeMove(Rule rule, Position from, Position to) {
        // Do nothing by default
    }
}
