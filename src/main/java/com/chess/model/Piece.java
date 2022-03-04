package com.chess.model;

import java.util.Iterator;

/**
 * Class for all chess pieces.
 * @author Marcus Phu
 * @version 2022-03-02
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
     * @param rule the rule that is being used
     * @param position the position of the piece
     * @return all possible moves for this piece
     * @throws IllegalArgumentException if the position is invalid
     */
    public Iterator<Move> getPossibleMoves(Rule rule, Position position) throws IllegalArgumentException;

    /**
     * Method that is called after a piece has been moved.
     * 
     * @param rule the rule that is being used
     * @param from the position the piece has moved from
     * @param to the position the piece has moved to
     */
    public void afterMove(Rule rule, Position from, Position to);

    /**
     * Method that is called before a piece is moved.
     * 
     * @param rule the rule that is being used
     * @param from the position the piece is moving from
     * @param to the position the piece is moving to
     */
    public void beforeMove(Rule rule, Position from, Position to);
}
