package com.chess.model.chess.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.chess.model.*;
import com.chess.model.chess.ChessTeamParameters;
import com.chess.model.chess.ChessTypeIdentifier;

/**
 * The class for the King behavior.
 * @author Marcus Phu
 * @version 2022-03-02
 */
public class PieceKing implements PieceBehavior {

    /**
     * The team parameters of the team that this piece belongs to.
     */
    private final ChessTeamParameters teamParameters;

    /**
     * Create a new piece king behavior.
     * 
     * @param teamParameters The team parameters of the team that this piece belongs to.
     */
    public PieceKing(ChessTeamParameters teamParameters) {
        this.teamParameters = teamParameters;
    }

    @Override
    public Identifier getTypeIdentifier() {
        return ChessTypeIdentifier.KING;
    }

    @Override
    public Iterator<Move> getPossibleMoves(Rule rule, Position position, Identifier teamIdentifier) throws IllegalArgumentException {
        final Collection<Move> possibleMoves = new ArrayList<Move>();

        rule.calculateMoves(position, teamIdentifier, possibleMoves, 1, 1, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, -1, 1, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, 1, -1, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, -1, -1, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, 1, 0, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, -1, 0, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, 0, 1, 1);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, 0, -1, 1);

        final boolean canCastleKingside = teamParameters.canCastleKingside();
        final boolean canCastleQueenside = teamParameters.canCastleQueenside();

        if (canCastleKingside) {
            boolean canCastle = true;
            
            // Check that the positions between the king and the rook are empty.
            for (int i = position.getCol() + 1; i < position.getCol() + 3; i++) {
                if (!rule.isEmpty(new Position(position.getRow(), i))) {
                    canCastle = false;
                    break;
                }
            }

            if (canCastle) {
                Move move = new Move(teamParameters.getCastlingKingSidePosition(), position, getTypeIdentifier(), false);
                move.setIsCastleKingSide(true);
                possibleMoves.add(move);
            }
        }

        if (canCastleQueenside) {
            boolean canCastle = true;

            // Check that the positions between the king and the rook are empty.
            for (int i = position.getCol() - 1; i > position.getCol() - 4; i--) {
                if (!rule.isEmpty(new Position(position.getRow(), i))) {
                    canCastle = false;
                    break;
                }
            }

            if (canCastle) {
                Move move = new Move(teamParameters.getCastlingQueenSidePosition(), position, getTypeIdentifier(), false);
                move.setIsCastleQueenSide(true);
                possibleMoves.add(move);
            }
        }

        return possibleMoves.iterator();
    }

    @Override
    public void afterMove(Rule rule, Position from, Position to) {
        // Set the castling rights to false if the king is moved.
        teamParameters.setCanCastleKingside(false);
        teamParameters.setCanCastleQueenside(false);
    }

    @Override
    public void beforeMove(Rule rule, Position from, Position to) {
        final boolean canCastleKingSide = teamParameters.canCastleKingside();
        final boolean canCastleQueenSide = teamParameters.canCastleQueenside();

        if (canCastleKingSide && to.equals(teamParameters.getCastlingKingSidePosition())) {
            // Move the rook
            boolean success = rule.requestMove(new Position(from.getRow(), from.getCol() + 3), new Position(from.getRow(), from.getCol() + 1));
            
            if (!success) {
                throw new IllegalStateException("Failed to move the rook for castling.");
            }
        }

        if (canCastleQueenSide && to.equals(teamParameters.getCastlingQueenSidePosition())) {
            // Move the rook
            boolean success = rule.requestMove(new Position(from.getRow(), from.getCol() - 4), new Position(from.getRow(), from.getCol() - 1));
        
            if (!success) {
                throw new IllegalStateException("Failed to move the rook for castling.");
            }
        }
    }
}
