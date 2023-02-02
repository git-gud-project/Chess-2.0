package com.chess.model.chess.pieces;

import com.chess.model.*;
import com.chess.model.chess.ChessTypeIdentifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * The class for the Knight behavior.
 * @author Marcus Phu
 * @version 2022-03-02
 */
public class PieceKnightUpgraded implements PieceBehavior {

    @Override
    public Identifier getTypeIdentifier() {
        return ChessTypeIdentifier.KNIGHTUPGRADE;
    }

    @Override
    public Iterator<Move> getPossibleMoves(Rule rule, Position position, Identifier teamIdentifier) throws IllegalArgumentException {
        final Collection<Move> possibleMoves = new ArrayList<Move>();


        rule.calculateMoves(position, teamIdentifier, possibleMoves, 1, 2, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, 1, -2, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, -1, 2, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, -1, -2, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, 2, 1, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, 2, -1, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, -2, 1, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, -2, -1, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, 2, 2, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, -2, 2, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, 2, -2, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, -2, -2, 1);

        return possibleMoves.iterator();
    }
}
