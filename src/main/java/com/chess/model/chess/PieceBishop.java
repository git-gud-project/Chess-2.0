package com.chess.model.chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import com.chess.model.*;

/**
 * The class for the Bishop behavior.
 */
public class PieceBishop implements PieceBehavior {
    
    private final Collection<Move> possibleMoves = Collections.synchronizedCollection(new ArrayList<Move>());

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
