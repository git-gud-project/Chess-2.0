package com.chess.model.chess;

import com.chess.model.Identifier;
import com.chess.model.Position;

public final class SharedChessTeamParameters {
    /**
     * The position marked for en passant.
     */
    private Position enPassantPosition = Position.INVALID;

    /**
     * The team that enabled en passant.
     */
    private Identifier enPassantTeam = Identifier.NULL;

    /**
     * Gets the position marked for en passant.
     *
     * @return the position marked for en passant
     */
    public Position getEnPassantPosition() {
        return enPassantPosition;
    }

    /**
     * Sets the position marked for en passant.
     *
     * @param enPassantPosition the position marked for en passant
     */
    public void setEnPassantPosition(Position enPassantPosition) {
        this.enPassantPosition = enPassantPosition;
    }

    /**
     * Gets the team that enabled en passant.
     *
     * @return the team that enabled en passant
     */
    public Identifier getEnPassantTeam() {
        return enPassantTeam;
    }

    /**
     * Sets the team that enabled en passant.
     *
     * @param enPassantTeam the team that enabled en passant
     */
    public void setEnPassantTeam(Identifier enPassantTeam) {
        this.enPassantTeam = enPassantTeam;
    }
}
