package com.chess.model.pieces;

import java.util.ArrayList;
import java.util.Iterator;

import com.chess.model.Board;
import com.chess.model.Cell;
import com.chess.model.Move;
import com.chess.model.PieceBehavior;
import com.chess.model.PieceType;

/**
 * The class for the Bishop.
 * Is in charge of:
 *  - Adding all possible moves for the bishop piece and returning it to the game.
 */
public class PieceKnight implements PieceBehavior {
    /**
     * The ArrayList containing all possible moves.
     */
    private ArrayList<Move> possibleMoves = new ArrayList<>();

    /**
     * Puts all possible moves for this piece to an iterator.
     * @param cell The cell of the current piece.
     * @return An iterator of possibleMoves array.
     */
    @Override
    public Iterator<Move> getPossibleMoves(Cell cell) {
        possibleMoves.clear();

        Board board = cell.getBoard();

        board.calculateMoves(cell, possibleMoves, 1, 2, 1);
        board.calculateMoves(cell, possibleMoves, 1, -2, 1);
        board.calculateMoves(cell, possibleMoves, -1, 2, 1);
        board.calculateMoves(cell, possibleMoves, -1, -2, 1);
        board.calculateMoves(cell, possibleMoves, 2, 1, 1);
        board.calculateMoves(cell, possibleMoves, 2, -1, 1);
        board.calculateMoves(cell, possibleMoves, -2, 1, 1);
        board.calculateMoves(cell, possibleMoves, -2, -1, 1);

        return possibleMoves.iterator();
    }

    /**
     * Returns the piece type of the current piece.
     * @return PieceType.KNIGHT
     */
    @Override
    public PieceType getPieceType() {
        return PieceType.KNIGHT;
    }
}