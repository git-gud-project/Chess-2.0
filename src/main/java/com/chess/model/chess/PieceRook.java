package com.chess.model.chess;

import java.util.ArrayList;
import java.util.Iterator;

import com.chess.model.Board;
import com.chess.model.Cell;
import com.chess.model.Move;
import com.chess.model.PieceBehavior;

/**
 * The class for the Rook.
 * Is in charge of:
 *  - Adding all possible moves for the rook piece and returning it to the game.
 *  - Checking if the rook has moved or not.
 */
public class PieceRook implements PieceBehavior{
    /**
     * The ArrayList containing all possible moves.
     */
    private ArrayList<Move> possibleMoves = new ArrayList<>();

    /**
     * The variable determining if the rook has moved or not.
     */
    private boolean hasMoved = false;

    /**
     * Puts all possible moves for this piece to an iterator.
     * @param cell The cell of the current piece.
     * @return An iterator of possibleMoves array.
     */
    public Iterator<Move> getPossibleMoves(Board board, Cell cell) {
        possibleMoves.clear();

        board.calculateMoves(cell, possibleMoves, 1, 0);
        board.calculateMoves(cell, possibleMoves, -1, 0);
        board.calculateMoves(cell, possibleMoves, 0, 1);
        board.calculateMoves(cell, possibleMoves, 0, -1);

        return possibleMoves.iterator();
    }

    /**
     * Returns the piece type of the current piece.
     * @return PieceType.ROOK
     */
    @Override
    public PieceType getTypeIdentifier() {
        return PieceType.ROOK;
    }

    /**
     * Returns the variable hasMoved containing the information if the Rook has moved or not.
     * @return hasMoved
     */
    @Override
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * Sets the variable hasMoved containing the information if the piece has moved or not.
     * @param hasMoved
     */
    @Override
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}