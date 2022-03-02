package com.chess.model.chess;

import com.chess.model.Identifier;

/**
 * Identifiers for the teams in a chess game.
 * 
 * Contains an entry for white and black.
 */
public enum ChessTeamIdentifier implements Identifier {
    /**
     * Value for the white team.
     */
    WHITE("w"),
    /**
     * Value for the black team.
     */
    BLACK("b"),
    /**
     * Value corresponding to no team.
     */
    NULL("");
    
    /**
     * The string representation of the identifier.
     */
    private final String id;

    /**
     * Private constructor for enum ChessTeamIdentifier.
     * @param id The string representation of the new instance.
     */
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

    /**
     * Compares the calling instance to the one provided as a parameter.
     * @param o The instance to be compared to the callee.
     * @return True if both instances share the same string representation, false otherwise.
     */
    @Override
    public boolean compare(Identifier o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChessTeamIdentifier that = (ChessTeamIdentifier) o;

        return id.equals(that.id);
    }

    /**
     * Provides a string representation of the calling object.
     * @return A string representation of the callee.
     */
    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
