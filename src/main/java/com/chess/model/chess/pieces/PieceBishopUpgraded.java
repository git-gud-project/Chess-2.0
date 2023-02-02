package com.chess.model.chess.pieces;

import com.chess.model.*;
import com.chess.model.chess.ChessTypeIdentifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class PieceBishopUpgraded implements PieceBehavior {
    @Override
    public Iterator<Move> getPossibleMoves(Rule rule, Position position, Identifier teamIdentifier) throws IllegalArgumentException {
        final Collection<Move> possibleMoves = new ArrayList<Move>();

        rule.calculateMoves(position, teamIdentifier, possibleMoves, 1, 1, 0, true, false, false);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, -1, 1, 0, true, false, false);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, 1, -1, 0, true, false, false);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, -1, -1, 0, true, false, false);

        return possibleMoves.iterator();
    }

    @Override
    public Identifier getTypeIdentifier() {
            return ChessTypeIdentifier.BISHOPUPGRADE;
        }


}