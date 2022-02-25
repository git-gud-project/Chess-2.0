package com.chess.model.chess;

import java.util.ArrayList;
import java.util.Iterator;

import com.chess.model.Identifier;
import com.chess.model.Move;
import com.chess.model.PieceBehavior;
import com.chess.model.Position;
import com.chess.model.Rule;

/**
 * The class for the Pawn behavior.
 */
public class PiecePawn implements PieceBehavior {

    private final ArrayList<Move> possibleMoves;

    private final ChessTeamParameters teamParameters;

    public PiecePawn(ChessTeamParameters teamParameters) {
        this.teamParameters = teamParameters;
        this.possibleMoves = new ArrayList<>();
    }

    @Override
    public void afterMove(Rule rule, Position from, Position to) {
        final SharedChessTeamParameters sharedTeamParameters = teamParameters.getSharedTeamParameters();

        // Check if we moved 2 cells.
        if (from.distanceRow(to) == 2) {
            sharedTeamParameters.setEnPassantPosition(new Position(to.getRow() - teamParameters.getPawnDirection(), to.getCol()));
            sharedTeamParameters.setEnPassantTeam(teamParameters.getTeamIdentifier());

            return;
        }
        
        Position enPassentPosition = sharedTeamParameters.getEnPassantPosition();

        // Check if we did en passant.
        if (enPassentPosition.equals(to)) {
            boolean success = rule.requestClear(new Position(to.getRow() - teamParameters.getPawnDirection(), to.getCol()));

            if (!success) {
                throw new IllegalStateException("En Passant failed.");
            }
        }
    }

    @Override
    public Identifier getTypeIdentifier() {
        return ChessTypeIdentifier.PAWN;
    }

    @Override
    public Iterator<Move> getPossibleMoves(Rule rule, Position position, Identifier teamIdentifier) throws IllegalArgumentException {
        possibleMoves.clear();

        final int dirRow = teamParameters.getPawnDirection();

        final int row = position.getRow();
        final int col = position.getCol();
        
        //If the pawn is on the first row, it can move 2 cells.
        if (row == teamParameters.getKingRow() + dirRow) {
            rule.calculateMoves(position, teamIdentifier, possibleMoves, dirRow, 0, 2, false, true, false);
        } else {
            rule.calculateMoves(position, teamIdentifier, possibleMoves, dirRow, 0, 1, false, true, false);
        }

        rule.calculateMoves(position, teamIdentifier, possibleMoves, dirRow, 1, 1, false, false, true);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, dirRow, -1, 1, false, false, true);

        Position enPassentRow = teamParameters.getSharedTeamParameters().getEnPassantPosition();

        // Check if we can take en passant.
        if (enPassentRow.equals(new Position(row + dirRow, col + 1))) {
            Move move = new Move(new Position(row + dirRow, col + 1), position, getTypeIdentifier(), true);
            move.setIsEnPassant(true);
            possibleMoves.add(move);
        } else if (enPassentRow.equals(new Position(row + dirRow, col - 1))) {
            Move move = new Move(new Position(row + dirRow, col - 1), position, getTypeIdentifier(), true);
            move.setIsEnPassant(true);
            possibleMoves.add(move);
        }

        return possibleMoves.iterator();
    }
}
