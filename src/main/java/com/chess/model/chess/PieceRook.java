package com.chess.model.chess;

import java.util.ArrayList;
import java.util.Iterator;

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
        return ChessIdentifier.ROOK;
    }

    /**
     * Called when the piece is moved.
     * 
     * @param from the cell that the piece was in before it was moved
     * @param to the cell that the piece is now in
     */
    @Override
    public void afterMove(Rule rule, Position from, Position to) {
        // Disable castling
        if (from.getCol() == 0) {
            teamParameters.setCanCastleQueenside(false);
        } else if (from.getCol() == 7) {
            teamParameters.setCanCastleKingside(false);
        }
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