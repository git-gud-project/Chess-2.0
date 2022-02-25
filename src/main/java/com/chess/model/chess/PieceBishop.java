package com.chess.model.chess;

import java.util.ArrayList;
import java.util.Iterator;

import com.chess.model.*;

/**
 * The class for the Bishop.
 * Is in charge of:
 *  - Adding all possible moves for the bishop piece and returning it to the game.
 */
public class PieceBishop implements PieceBehavior {
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
    public Iterator<Move> getPossibleMoves(Rule rule, Position position, Identifier teamIdentifier) {
        possibleMoves.clear();

        rule.calculateMoves(position, teamIdentifier, possibleMoves, 1, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, -1, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, 1, -1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, -1, -1);

        return possibleMoves.iterator();
    }

    @Override
    public Identifier getTypeIdentifier() {
        return ChessIdentifier.BISHOP;
    }
}
