package com.chess.model.chess.pieces;

import com.chess.model.*;
import com.chess.model.chess.ChessTeamParameters;
import com.chess.model.chess.ChessTypeIdentifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * The class for the Rook behavior.
 * @author Marcus Phu
 * @version 2022-03-02
 */
public class PieceRookUpgraded implements PieceBehavior{

    /**
     * The team parameters of the team that this piece belongs to.
     */
    private final ChessTeamParameters teamParameters;

    /**
     * Create a new piece king behavior.
     *
     * @param teamParameters The team parameters of the team that this piece belongs to.
     */
    public PieceRookUpgraded(ChessTeamParameters teamParameters) {
        this.teamParameters = teamParameters;
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
        final Collection<Move> possibleMoves = new ArrayList<Move>();


        rule.calculateMoves(position, teamIdentifier, possibleMoves, 1, 0, 0, true, false, false);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, -1, 0, 0, true, false, false);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, 0, 1, 0, true, false, false);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, 0, -1, 0, true, false, false);

        return possibleMoves.iterator();
    }
    @Override
    public Identifier getTypeIdentifier() {
        return ChessTypeIdentifier.ROOKUPGRADE;
    }
}