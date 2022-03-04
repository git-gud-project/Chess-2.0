package com.chess.model.chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import com.chess.model.Identifier;
import com.chess.model.Move;
import com.chess.model.PieceBehavior;
import com.chess.model.Position;
import com.chess.model.Rule;

/**
 * The class for the Rook behavior.
 * @author Marcus Phu
 * @version 2022-03-02
 */
public class PieceRook implements PieceBehavior{

    private final Collection<Move> possibleMoves = Collections.synchronizedCollection(new ArrayList<Move>());
    
    /**
     * The team parameters of the team that this piece belongs to.
     */
    private final ChessTeamParameters teamParameters;

    /**
     * Create a new piece king behavior.
     * 
     * @param teamParameters The team parameters of the team that this piece belongs to.
     */
    public PieceRook(ChessTeamParameters teamParameters) {
        this.teamParameters = teamParameters;
    }

    @Override
    public Identifier getTypeIdentifier() {
        return ChessTypeIdentifier.ROOK;
    }

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
    public Iterator<Move> getPossibleMoves(Rule rule, Position position, Identifier teamIdentifier) throws IllegalArgumentException {
        possibleMoves.clear();

        rule.calculateMoves(position, teamIdentifier, possibleMoves, 1, 0);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, -1, 0);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, 0, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, 0, -1);

        return possibleMoves.iterator();
    }
}