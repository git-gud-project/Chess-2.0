package com.chess.model.chess.pieces;

import com.chess.model.*;
import com.chess.model.chess.ChessTeamParameters;
import com.chess.model.chess.ChessTypeIdentifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * The class for the Knight behavior.
 * @author Marcus Phu
 * @version 2022-03-02
 */
public class PiecePawnKnight implements PieceBehavior {

    private final ChessTeamParameters teamParameters;
    public PiecePawnKnight(ChessTeamParameters teamParameters) {
        this.teamParameters = teamParameters;
    }

    @Override
    public Identifier getTypeIdentifier() {
        return ChessTypeIdentifier.PAWNKNIGHT;
    }

    @Override
    public Iterator<Move> getPossibleMoves(Rule rule, Position position, Identifier teamIdentifier) throws IllegalArgumentException {
        final Collection<Move> possibleMoves = new ArrayList<Move>();
        final int dirRow = teamParameters.getPawnDirection();

        rule.calculateMoves(position, teamIdentifier, possibleMoves, 1, 2, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, 1, -2, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, -1, 2, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, -1, -2, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, 2, 1, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, 2, -1, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, -2, 1, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, -2, -1, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, dirRow, 0, 1, false, true, false);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, dirRow, 1, 1, false, false, true);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, dirRow, -1, 1, false, false, true);

        return possibleMoves.iterator();
    }
}
