package com.chess.model.chess;

import com.chess.model.Identifier;

/**
 * Identifiers for the teams in a chess game.
 * 
 * Contains an entry for white and black.
 */
public enum ChessTeamIdentifier implements Identifier {
    WHITE("w"),
    BLACK("b"),
    NULL("");
    
    /**
     * The string representation of the identifier.
     */
    private final String id;

    private ChessTeamIdentifier(String id) {
        this.id = id;
    }

    /**
     * Get the identifier for the given string.
     * 
     * @param id The string representation of the identifier.
     * @return The identifier.
     * @throws IllegalArgumentException If the given string is not a valid identifier.
     */
    public static ChessTeamIdentifier getIdentifier(String id) throws IllegalArgumentException {
        for (ChessTeamIdentifier identifier : values()) {
            if (identifier.id.equals(id)) {
                return identifier;
            }
        }

        return NULL;
    }

    @Override
    public boolean compare(Identifier o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChessTeamIdentifier that = (ChessTeamIdentifier) o;

        return id.equals(that.id);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
