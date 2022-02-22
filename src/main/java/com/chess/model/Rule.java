package com.chess.model;

import java.util.List;

public interface Rule {
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
        List<Move> registry,
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
        List<Move> registry,
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
        List<Move> registry,
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
