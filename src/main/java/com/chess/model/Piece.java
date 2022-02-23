package com.chess.model;

import java.util.Iterator;

/**
 * Class for all chess pieces.
 */
public interface Piece {
    /**
     * Returns the type of the piece.
     *
     * @return the type of the piece
     */
    public Identifier getTeamIdentifier();

    /**
     * Returns the type of the piece.
     *
     * @return the type of the piece
     */
    public Identifier getTypeIdentifier();

    /**
     * Get the path to the image file for the piece.
     * 
     * @return the path to the image file for the piece
     */
    public String getIconPath();

    /**
     * Gets all possible moves for this piece.
     * 
     * @return all possible moves for this piece
     */
    public Iterator<Move> getPossibleMoves(Rule rule, Position position);

    /**
     * Event to be called when the piece is moved.
     * 
     * @param rule the rule that is being used
     * @param from the cell that the piece was in before it was moved
     * @param to the cell that the piece is now in
     */
    public void afterMove(Rule rule, Position from, Position to);

    /**
     * Event to be called before the piece is moved.
     * 
     * @param rule the rule that is being used
     * @param from the cell that the piece was in before it was moved
     * @param to the cell that the piece is now in
     */
    public void beforeMove(Rule rule, Position from, Position to);
}
