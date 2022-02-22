package com.chess.model.chess;

import java.util.ArrayList;
import java.util.Iterator;

import com.chess.model.*;

/**
 * The class for the King.
 * Is in charge of:
 *  - Adding all possible moves for the king piece and returning it to the game.
 *  - Checking if the king can castle to both king's- and queen's side.
 */
public class PieceKing implements PieceBehavior {
    /**
     * The ArrayList containing all possible moves.
     */
    private final ArrayList<Move> possibleMoves;
    
    private final ChessTeamParameters teamParameters;

    public PieceKing(ChessTeamParameters teamParameters) {
        this.teamParameters = teamParameters;
        this.possibleMoves = new ArrayList<>();
    }

    @Override
    public Identifier getTypeIdentifier() {
        return PieceType.KING;
    }

    @Override
    public Iterator<Move> getPossibleMoves(Rule rule, Position position, Identifier teamIdentifier) {
        possibleMoves.clear();

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
            
            for (int i = 0; i < 3; i++) {
                if (!rule.isEmpty(new Position(position.getRow(), position.getCol() + i))) {
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

            for (int i = 0; i < 3; i++) {
                if (!rule.isEmpty(new Position(position.getRow(), position.getCol() - i))) {
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

    /**
     * Called when the piece is moved.
     * 
     * @param from the cell that the piece was in before it was moved
     * @param to the cell that the piece is now in
     */
    @Override
    public void onMove(Rule rule, Position from, Position to) {
        // Set the castling rights to false if the king is moved.
        teamParameters.setCanCastleKingside(false);
        teamParameters.setCanCastleQueenside(false);
    }

    /**
     * Called before the piece is moved.
     * 
     * @param from the cell that the piece was in before it was moved
     * @param to the cell that the piece is now in
     */
    @Override
    public void beforeMove(Rule rule, Position from, Position to) {
        final boolean canCastleKingSide = teamParameters.canCastleKingside();
        final boolean canCastleQueenSide = teamParameters.canCastleQueenside();

        if (canCastleKingSide && to == teamParameters.getCastlingKingSidePosition()) {
            // Move the rook
            rule.requestMove(new Position(from.getRow(), from.getCol() + 2), new Position(from.getRow(), from.getCol() + 3));
        }

        if (canCastleQueenSide && to == teamParameters.getCastlingQueenSidePosition()) {
            // Move the rook
            rule.requestMove(new Position(from.getRow(), from.getCol() - 4), new Position(from.getRow(), from.getCol() - 1));
        }
    }
}
