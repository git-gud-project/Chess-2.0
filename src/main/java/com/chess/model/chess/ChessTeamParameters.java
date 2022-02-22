package com.chess.model.chess;

import com.chess.model.Identifier;
import com.chess.model.Position;

/**
 * Details for a chess team.
 */
public class ChessTeamParameters {
    /**
     * Team identifier.
     */
    private final Identifier teamIdentifier;

    /**
     * The direction for pawns.
     */
    private final int pawnDirection;

    /**
     * Can castle kingside.
     */
    private boolean canCastleKingside;

    /**
     * Can castle queenside.
     */
    private boolean canCastleQueenside;

    /**
     * The position for castling kingside.
     */
    private Position castlingKingSidePosition;

    /**
     * The position for castling queenside.
     */
    private Position castlingQueenSidePosition;

    /**
     * The row for the king.
     */
    private int kingRow;

    /**
     * Constructs a new chess team parameters.
     */
    public ChessTeamParameters(Identifier identifier, int pawnDirection) {
        this.teamIdentifier = identifier;
        this.pawnDirection = pawnDirection;
    }

    /**
     * Returns the team identifier.
     *
     * @return the team identifier
     */
    public Identifier getTeamIdentifier() {
        return teamIdentifier;
    }

    /**
     * Gets the direction for pawns.
     *
     * @return the direction for pawns
     */
    public int getPawnDirection() {
        return pawnDirection;
    }

    /**
     * Gets whether or not the team can castle kingside.
     *
     * @return whether or not the team can castle kingside
     */
    public boolean canCastleKingside() {
        return canCastleKingside;
    }

    /**
     * Sets whether or not the team can castle kingside.
     *
     * @param canCastleKingside whether or not the team can castle kingside
     */
    public void setCanCastleKingside(boolean canCastleKingside) {
        this.canCastleKingside = canCastleKingside;
    }

    /**
     * Gets whether or not the team can castle queenside.
     *
     * @return whether or not the team can castle queenside
     */
    public boolean canCastleQueenside() {
        return canCastleQueenside;
    }

    /**
     * Sets whether or not the team can castle queenside.
     *
     * @param canCastleQueenside whether or not the team can castle queenside
     */
    public void setCanCastleQueenside(boolean canCastleQueenside) {
        this.canCastleQueenside = canCastleQueenside;
    }

    /**
     * Gets the position for castling kingside.
     *
     * @return the position for castling kingside
     */
    public Position getCastlingKingSidePosition() {
        return castlingKingSidePosition;
    }

    /**
     * Sets the position for castling kingside.
     *
     * @param castlingKingSidePosition the position for castling kingside
     */
    public void setCastlingKingSidePosition(Position castlingKingSidePosition) {
        this.castlingKingSidePosition = castlingKingSidePosition;
    }

    /**
     * Gets the position for castling queenside.
     *
     * @return the position for castling queenside
     */
    public Position getCastlingQueenSidePosition() {
        return castlingQueenSidePosition;
    }

    /**
     * Sets the position for castling queenside.
     *
     * @param castlingQueenSidePosition the position for castling queenside
     */
    public void setCastlingQueenSidePosition(Position castlingQueenSidePosition) {
        this.castlingQueenSidePosition = castlingQueenSidePosition;
    }

    /**
     * Gets the row for the king.
     *
     * @return the row for the king
     */
    public int getKingRow() {
        return kingRow;
    }

    /**
     * Sets the row for the king.
     *
     * @param kingRow the row for the king
     */
    public void setKingRow(int kingRow) {
        this.kingRow = kingRow;
    }
}