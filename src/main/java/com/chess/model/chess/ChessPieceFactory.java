package com.chess.model.chess;

import com.chess.model.Identifier;
import com.chess.model.PieceBehavior;

/**
 * A factory for creating chess pieces.
 */
public final class ChessPieceFactory {
    /**
     * Creates a new chess piece.
     * 
     * @param type The type of chess piece to create.
     * @param team The team the chess piece belongs to.
     * @return The newly created chess piece.
     * @throws IllegalArgumentException If the type is invalid or the team is null.
     */
    public static ChessPiece createPiece(Identifier type, ChessTeam team) throws IllegalArgumentException {
        if (team == null) {
            throw new IllegalArgumentException("Team cannot be null.");
        }

        PieceBehavior behavior;

        String typeString = type.toString();

        if (typeString.equals(PieceType.BISHOP.toString())) {
            behavior = new PieceBishop();
        } else if (typeString.equals(PieceType.KING.toString())) {
            behavior = new PieceKing(team.getTeamParameters());
        } else if (typeString.equals(PieceType.KNIGHT.toString())) {
            behavior = new PieceKnight();
        } else if (typeString.equals(PieceType.PAWN.toString())) {
            behavior = new PiecePawn(team.getTeamParameters());
        } else if (typeString.equals(PieceType.QUEEN.toString())) {
            behavior = new PieceQueen();
        } else if (typeString.equals(PieceType.ROOK.toString())) {
            behavior = new PieceRook(team.getTeamParameters());
        } else {
            throw new IllegalArgumentException("Unknown piece type: " + type);
        }

        ChessPiece piece = new ChessPiece(behavior, team);

        return piece;
    }
}
