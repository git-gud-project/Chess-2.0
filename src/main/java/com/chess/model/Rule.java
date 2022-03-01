package com.chess.model;

import java.util.Collection;

/**
 * Rules for how pieces can move and what they may do.
 */
public interface Rule {
    /**
     * Request that a piece be moved from one position to another.
     * 
     * @param from           The position to move from.
     * @param to             The position to move to.
     * @return               True if the move was successful, false otherwise.
     * @throws IllegalArgumentException if the position is invalid.
     */
    public boolean requestMove(
        Position from,
        Position to
    ) throws IllegalArgumentException;

    /**
     * Request that a position be cleared.
     * 
     * @param position       The position to clear.
     * @return               True if the move was successful, false otherwise.
     * @throws IllegalArgumentException if the position is invalid.
     */
    public boolean requestClear(
        Position position
    ) throws IllegalArgumentException;

    /**
     * Get the type identifier of the piece at the specified position.
     * 
     * @param position       The position to get the type identifier of the piece at.
     * @return               The type identifier of the piece at the specified position.
     * @throws IllegalArgumentException if the position is invalid.
     */
    public Identifier getTypeIdentifier(
        Position position
    ) throws IllegalArgumentException;

    /**
     * Get the team identifier of the piece at the specified position.
     * 
     * @param position       The position to get the team identifier of the piece at.
     * @return               The team identifier of the piece at the specified position.
     * @throws IllegalArgumentException if the position is invalid.
     */
    public Identifier getTeamIdentifier(
        Position position
    ) throws IllegalArgumentException;

    /**
     * Check if a position is empty.
     * 
     * @param position       The position to check.
     * @return               True if the position is empty, false otherwise.
     * @throws IllegalArgumentException if the position is invalid.
     */
    public boolean isEmpty(
        Position position
    ) throws IllegalArgumentException;

    /**
     * Calculate a moveset.
     * 
     * @param position       the position of origin
     * @param teamIdentifier the team id of the player
     * @param registry       a registry of the moves, out parameter
     * @param directionRow   the row direction
     * @param directionCol   the column direction
     * @param maxSteps       the maximum amount of steps, 0 for unlimited
     * @param skipOwn        whether to skip own pieces
     * @param cantCapture    whether to stop if a piece is found
     * @param requireCapture whether to require capturing
     */
    public void calculateMoves(
        Position position,
        Identifier teamIdentifier,
        Collection<Move> registry,
        int directionRow,
        int directionCol,
        int maxSteps,
        boolean skipOwn,
        boolean cantCapture,
        boolean requireCapture
    );

    /**
     * Calculate a moveset.
     * 
     * @param position       the position of origin
     * @param teamIdentifier the team id of the player
     * @param registry       a registry of the moves, out parameter
     * @param directionRow   the row direction
     * @param directionCol   the column direction
     * @param maxSteps       the maximum amount of steps, 0 for unlimited
     */
    public default void calculateMoves(
        Position position,
        Identifier teamIdentifier,
        Collection<Move> registry,
        int directionRow,
        int directionCol,
        int maxSteps
    ) {
        calculateMoves(
            position,
            teamIdentifier,
            registry,
            directionRow,
            directionCol,
            maxSteps,
            false,
            false,
            false
        );
    }

    /**
     * Calculate a moveset.
     * 
     * @param position       the position of origin
     * @param teamIdentifier the team id of the player
     * @param registry       a registry of the moves, out parameter
     * @param directionRow   the row direction
     * @param directionCol   the column direction
     */
    public default void calculateMoves(Position position,
        Identifier teamIdentifier,
        Collection<Move> registry,
        int directionRow,
        int directionCol
    ) {
        calculateMoves(
            position,
            teamIdentifier,
            registry,
            directionRow,
            directionCol,
            0,
            false,
            false,
            false
        );
    }
}
