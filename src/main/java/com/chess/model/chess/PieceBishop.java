package com.chess.model.chess;

import java.util.ArrayList;
import java.util.Iterator;

import com.chess.model.*;

/**
 * The class for the Bishop behavior.
 */
public class PieceBishop implements PieceBehavior {
    /**
     * The ArrayList containing all possible moves.
     */
    private ArrayList<Move> possibleMoves = new ArrayList<>();

    @Override
    public Iterator<Move> getPossibleMoves(Rule rule, Position position, Identifier teamIdentifier) throws IllegalArgumentException {
        possibleMoves.clear();

        rule.calculateMoves(position, teamIdentifier, possibleMoves, 1, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, -1, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, 1, -1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, -1, -1);

        return possibleMoves.iterator();
    }

    @Override
    public Identifier getTypeIdentifier() {
        return ChessTypeIdentifier.BISHOP;
    }
}
