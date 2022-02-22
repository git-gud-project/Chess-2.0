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
}
