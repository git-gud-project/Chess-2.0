package com.chess.model.chess;

import com.chess.model.Identifier;

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
}
