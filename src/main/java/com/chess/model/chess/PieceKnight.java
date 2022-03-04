package com.chess.model.chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.chess.model.Identifier;
import com.chess.model.Move;
import com.chess.model.PieceBehavior;
import com.chess.model.Position;
import com.chess.model.Rule;

/**
 * The class for the Knight behavior.
 * @author Marcus Phu
 * @version 2022-03-02
 */
public class PieceKnight implements PieceBehavior {

    @Override
    public Identifier getTypeIdentifier() {
        return ChessTypeIdentifier.KNIGHT;
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

        return possibleMoves.iterator();
    }
}
