package com.chess.model.chess;

import com.chess.model.Identifier;
import com.chess.model.PieceBehavior;
import com.chess.model.Team;

import com.chess.model.chess.ChessPiece;

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
    public static ChessPiece createPiece(Identifier type, Team team) throws IllegalArgumentException
    {
        if (team == null)
        {
            throw new IllegalArgumentException("Team cannot be null.");
        }

        PieceBehavior behavior;

        switch (type) {
            case BISHOP:
                behavior = new PieceBishop();
                break;
            case KING:
                behavior = new PieceKing(team.getParameters());
                break;
            case KNIGHT:
                behavior = new PieceKnight();
                break;
            case PAWN:
                behavior = new PiecePawn(team.getParameters());
                break;
            case QUEEN:
                behavior = new PieceQueen();
                break;
            case ROOK:
                behavior = new PieceRook(team.getParameters());
                break;
            default:
                throw new IllegalArgumentException("Unknown piece type: " + type);
        }

        ChessPiece piece = new ChessPiece(behavior, team);

        return piece;
    }
}
