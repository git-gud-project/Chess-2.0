package com.chess.model.chess;

import java.util.ArrayList;
import java.util.Iterator;

import com.chess.model.Identifier;
import com.chess.model.Move;
import com.chess.model.PieceBehavior;
import com.chess.model.Position;
import com.chess.model.Rule;

/**
 * The class for the Bishop.
 * Is in charge of:
 *  - Adding all possible moves for the bishop piece and returning it to the game.
 */
public class PieceKnight implements PieceBehavior {
    /**
     * The ArrayList containing all possible moves.
     */
    private final ArrayList<Move> possibleMoves = new ArrayList<>();

    @Override
    public Identifier getTypeIdentifier() {
        return ChessIdentifier.KNIGHT;
    }

    @Override
    public Iterator<Move> getPossibleMoves(Rule rule, Position position, Identifier teamIdentifier) {
        possibleMoves.clear();

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
