package com.chess.model.chess;

import com.chess.model.Identifier;
import com.chess.model.PieceBehavior;
import com.chess.model.ChessTeam;

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
    public static ChessPiece createPiece(Identifier type, ChessTeam team) throws IllegalArgumentException
    {
        if (team == null)
        {
            throw new IllegalArgumentException("Team cannot be null.");
        }

        PieceBehavior behavior;

        switch (type.toString()) {
            case PieceType.BISHOP.toString():
                behavior = new PieceBishop();
                break;
            case PieceType.KING.toString():
                behavior = new PieceKing(team.getParameters());
                break;
            case PieceType.KNIGHT.toString():
                behavior = new PieceKnight();
                break;
            case PieceType.PAWN.toString():
                behavior = new PiecePawn(team.getParameters());
                break;
            case PieceType.QUEEN.toString():
                behavior = new PieceQueen();
                break;
            case PieceType.ROOK.toString():
                behavior = new PieceRook(team.getParameters());
                break;
            default:
                throw new IllegalArgumentException("Unknown piece type: " + type);
        }

        ChessPiece piece = new ChessPiece(behavior, team);

        return piece;
    }
}
