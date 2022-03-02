package com.chess.model.chess;

import com.chess.model.Identifier;
import com.chess.model.Position;

/**
 * Details for a chess team.
 */
public class ChessTeamParameters {
    /**
     * Shared parameters for chess teams.
     */
    private final SharedChessTeamParameters sharedTeamParameters;

    /**
     * Team identifier.
     */
    private final Identifier teamIdentifier;

    /**
     * The direction for pawns.
     */
    private final int pawnDirection;

    /**
     * The row for the king.
     */
    private final int kingRow;

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
    private Position castlingKingSidePosition = Position.INVALID;

    /**
     * The position for castling queenside.
     */
    private Position castlingQueenSidePosition = Position.INVALID;

    /**
     * Construct a new chess team parameters.
     * @param shared The instance of the shared parameters between the team playing.
     * @param identifier The identifier of the team represented by this instance of the class.
     * @param pawnDirection The pawn direction in which the pawns of the given teams moves.
     * @param kingRow The row in which the king of the given team is located.
     */
    public ChessTeamParameters(SharedChessTeamParameters shared, Identifier identifier, int pawnDirection, int kingRow) {
        this.sharedTeamParameters = shared;
        this.teamIdentifier = identifier;
        this.pawnDirection = pawnDirection;
        this.kingRow = kingRow;
    }

    /**
     * Gets the shared parameters for chess teams.
     *
     * @return the shared parameters for chess teams
     */
    public SharedChessTeamParameters getSharedTeamParameters() {
        return sharedTeamParameters;
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
}
