package com.chess.model.chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.chess.model.*;

/**
 * The class for the Bishop behavior.
 * @author Isak Holmdahl
 * @version 2022-03-02
 */
public class PieceBishop implements PieceBehavior {

    @Override
    public Iterator<Move> getPossibleMoves(Rule rule, Position position, Identifier teamIdentifier) throws IllegalArgumentException {
        final Collection<Move> possibleMoves = new ArrayList<Move>();

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
