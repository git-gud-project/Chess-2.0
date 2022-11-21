package com.chess.control;

import com.chess.model.Identifier;
import com.chess.model.Move;
import com.chess.model.chess.ChessTeam;

/**
 * Interface for the chess control.
 * @author Wincent Stålbert Holm
 * @version 2022-03-02
 */
public interface ChessControlInterface {
    /**
     * Set the pause state of the game.
     * 
     * @param paused the pause state of the game
     */
    public void setPaused(boolean paused);

    /**
     * Set the local team.
     * 
     * @param team the local team
     */
    public void setLocalTeam(ChessTeam team);

    /**
     * Promote a pawn at the given position to the given type.
     * 
     * @param row the row of the pawn
     * @param col the column of the pawn
     * @param typeIdentifier the type identifier to promote to
     * @param isElimination whether or not the promotion is also an elimination
     * @throws IllegalArgumentException if the position is invalid
     */
    public void promotePawn(int row, int col, Identifier typeIdentifier, boolean isElimination) throws IllegalArgumentException;
    /**
     * Promote a piece at the given position to the given type.
     *
     * @param row the row of the piece
     * @param col the column of the piece
     * @param typeIdentifier the type identifier to promote to
     * @param isElimination whether or not the promotion is also an elimination
     * @throws IllegalArgumentException if the position is invalid
     */
    public void promotePiece(int row, int col, Identifier typeIdentifier, boolean isElimination) throws IllegalArgumentException;

    /**
     * Execute a move.
     * 
     * This is executed locally.
     * 
     * @param move the move to execute
     */
    public void executeMove(Move move);

    /**
     * Move a piece according to the given move.
     * 
     * This may go through network communication.
     * 
     * @param move the move to execute
     */
    public void movePiece(Move move);
}
