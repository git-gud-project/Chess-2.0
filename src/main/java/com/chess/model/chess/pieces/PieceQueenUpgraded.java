package com.chess.model.chess.pieces;

import com.chess.model.*;
import com.chess.model.chess.ChessTypeIdentifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * The class for the Queen behavior.
 * @author Marcus Phu
 * @version 2022-03-02
 */
public class PieceQueenUpgraded implements PieceBehavior {

    @Override
    public Identifier getTypeIdentifier() {
        return ChessTypeIdentifier.QUEENUPGRADE;
    }

    @Override
    public Iterator<Move> getPossibleMoves(Rule rule, Position position, Identifier teamIdentifier) throws IllegalArgumentException {
        final Collection<Move> possibleMoves = new ArrayList<Move>();
        
        possibleMoves.clear();

        rule.calculateMoves(position, teamIdentifier, possibleMoves, 1, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, 1, -1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, -1, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, -1, -1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, 1, 0);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, -1, 0);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, 0, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, 0, -1);

        return possibleMoves.iterator();
    }
}
