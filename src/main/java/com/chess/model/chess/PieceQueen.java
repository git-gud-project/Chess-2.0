package com.chess.model.chess;

import java.util.ArrayList;
import java.util.Iterator;

import com.chess.model.Identifier;
import com.chess.model.Move;
import com.chess.model.PieceBehavior;
import com.chess.model.Position;
import com.chess.model.Rule;

/**
 * The class for the Queen behavior.
 */
public class PieceQueen implements PieceBehavior {
    /**
     * The ArrayList containing all possible moves.
     */
    private ArrayList<Move> possibleMoves = new ArrayList<>();

    @Override
    public Identifier getTypeIdentifier() {
        return ChessTypeIdentifier.QUEEN;
    }

    @Override
    public Iterator<Move> getPossibleMoves(Rule rule, Position position, Identifier teamIdentifier) throws IllegalArgumentException {
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
