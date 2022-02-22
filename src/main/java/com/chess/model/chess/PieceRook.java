package com.chess.model.chess;

import java.util.ArrayList;
import java.util.Iterator;

import com.chess.model.Board;
import com.chess.model.Cell;
import com.chess.model.Identifier;
import com.chess.model.Move;
import com.chess.model.PieceBehavior;
import com.chess.model.Position;
import com.chess.model.Rule;

/**
 * The class for the Rook.
 * Is in charge of:
 *  - Adding all possible moves for the rook piece and returning it to the game.
 *  - Checking if the rook has moved or not.
 */
public class PieceRook implements PieceBehavior{
    /**
     * The ArrayList containing all possible moves.
     */
    private final ArrayList<Move> possibleMoves;
    
    private final ChessTeamParameters teamParameters;

    public PieceRook(ChessTeamParameters teamParameters) {
        this.teamParameters = teamParameters;
        this.possibleMoves = new ArrayList<>();
    }

    @Override
    public Identifier getTypeIdentifier() {
        return PieceType.ROOK.getTypeIdentifier();
    }

    @Override
    public Iterator<Move> getPossibleMoves(Rule rule, Position position, Identifier teamIdentifier) {
        possibleMoves.clear();

        rule.calculateMoves(position, teamIdentifier, possibleMoves, 1, 0);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, -1, 0);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, 0, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, 0, -1);

        return possibleMoves.iterator();
    }
}