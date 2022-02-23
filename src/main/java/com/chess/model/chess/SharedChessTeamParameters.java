package com.chess.model.chess;

import com.chess.model.Position;

public final class SharedChessTeamParameters {
    /**
     * The position marked for en passant.
     */
    private Position enPassantPosition = Position.INVALID;

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
}
