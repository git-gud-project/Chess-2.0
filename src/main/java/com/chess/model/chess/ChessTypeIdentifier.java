package com.chess.model.chess;

import com.chess.model.Identifier;

/**
 * Identifiers for chess pieces.
 * 
 * Contains identifiers for all standard chess pieces.
 */
public enum ChessTypeIdentifier implements Identifier {
    PAWN("p"),
    ROOK("r"),
    KNIGHT("n"),
    BISHOP("b"),
    QUEEN("q"),
    KING("k"),
    NULL("");
    
    /**
     * The string representation of the identifier.
     */
    private final String id;

    private ChessTypeIdentifier(String id) {
        this.id = id;
    }

    /**
     * Get the identifier for the given string.
     * 
     * @param id The string representation of the identifier.
     * @return The identifier.
     * @throws IllegalArgumentException If the given string is not a valid identifier.
     */
    public static ChessTypeIdentifier getIdentifier(String id) throws IllegalArgumentException {
        for (ChessTypeIdentifier identifier : values()) {
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

        ChessTypeIdentifier that = (ChessTypeIdentifier) o;

        return id.equals(that.id);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
