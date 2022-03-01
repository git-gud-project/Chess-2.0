package com.chess.model.chess;

import com.chess.model.Identifier;
import com.chess.model.MovesCalculator;
import com.chess.model.Position;

/**
 * Information about the board. Facilitates the connection between the model and
 * sub components.
 */
interface BoardInformation {
    /**
     * Returns whether there is a piece at the specified position.
     * 
     * @param position the position to check
     * @return whether there is a piece at the specified position
     * @throws IllegalArgumentException if the position is invalid
     */
    public boolean isEmpty(Position position) throws IllegalArgumentException;

    /**
     * Returns whether there is a piece at a specific row and column.
     * 
     * @param row the row to check
     * @param col the column to check
     * @return whether there is a piece at a specific row and column
     * @throws IllegalArgumentException if the position is invalid
     */
    public boolean isEmpty(int row, int col) throws IllegalArgumentException;

    /**
     * Returns whether the specified position is on the board.
     * 
     * @param position the position to check
     * @return whether the specified position is on the board
     */
    public boolean isValid(Position position);

    /**
     * Returns the team identifier of the piece at the specified position.
     * 
     * @param position the position to check
     * @return the team identifier of the piece at the specified position
     * @throws IllegalArgumentException if the position is invalid or there is no piece at the specified position
     */
    public Identifier getTeamIdentifier(Position position) throws IllegalArgumentException;

    /**
     * Returns the team identifier of the piece at a specific row and column.
     * 
     * @param row the row to check
     * @param col the column to check
     * @return the team identifier of the piece at a specific row and column
     * @throws IllegalArgumentException if the position is invalid or there is no piece at the specified position
     */
    public Identifier getTeamIdentifier(int row, int col) throws IllegalArgumentException;

    /**
     * Returns the type identifier of the piece at the specified position.
     * 
     * @param position the position to check
     * @return the type identifier of the piece at the specified position
     * @throws IllegalArgumentException if the position is invalid or there is no piece at the specified position
     */
    public Identifier getTypeIdentifier(Position position) throws IllegalArgumentException;

    /**
     * Returns the type identifier of the piece at a specific row and column.
     * 
     * @param row the row to check
     * @param col the column to check
     * @return the type identifier of the piece at a specific row and column
     * @throws IllegalArgumentException if the position is invalid or there is no piece at the specified position
     */
    public Identifier getTypeIdentifier(int row, int col) throws IllegalArgumentException;

    /**
     * Returns the board size.
     * 
     * @return the board size
     */
    public int getBoardSize();

    /**
     * Set the piece at the specified position from a piece and team identifier.
     * 
     * @param position the position to set
     * @param piece the piece to set
     * @param team the team to set
     * @param isFinalMove whether the move is final and should trigger the cell's on change event
     * @throws IllegalArgumentException if the position is invalid
     */
    public void setPiece(Position position, Identifier piece, Identifier team, boolean isFinalMove) throws IllegalArgumentException;

    /**
     * Clear the piece at the specified position.
     * 
     * @param position the position to clear
     * @param isFinalMove whether the move is final and should trigger the cell's on change event
     * @throws IllegalArgumentException if the position is invalid
     */
    public void clearPiece(Position position, boolean isFinalMove) throws IllegalArgumentException;

    /**
     * Returns the move calculator of a piece at the specified position.
     * 
     * @param position the position to check
     * @return the move calculator of a piece at the specified position
     * @throws IllegalArgumentException if the position is invalid or there is no piece at the specified position
     */
    public MovesCalculator getPossibleMovesIterator(Position position) throws IllegalArgumentException;
}
