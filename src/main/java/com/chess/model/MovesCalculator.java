package com.chess.model;

import java.util.Iterator;

/**
 * Interface for a moves calculator.
 */
public interface MovesCalculator {
    /**
     * Get an iterator of all moves that can be performed by this piece from a specific position
     * 
     * @param rule The rule to use
     * @param position The position of the piece
     * @return An iterator of all possible moves
     * @throws IllegalArgumentException if the position is invalid
     * @throws NullPointerException if the rule is null
     */
    public Iterator<Move> getPossibleMoves(Rule rule, Position position) throws IllegalArgumentException, NullPointerException;
}
