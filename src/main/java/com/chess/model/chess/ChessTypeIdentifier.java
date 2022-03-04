package com.chess.model.chess;

import com.chess.model.Identifier;

/**
 * Identifiers for chess pieces.
 * Contains identifiers for all standard chess pieces.
 * @author Wincent Stålbert Holm
 * @version 2022-03-02
 */
public enum ChessTypeIdentifier implements Identifier {
    /**
     * Value for the pawn piece type.
     */
    PAWN("p"),
    /**
     * Value for the rook piece type.
     */
    ROOK("r"),
    /**
     * Value for the knight piece type.
     */
    KNIGHT("n"),
    /**
     * Value for the bishop piece type.
     */
    BISHOP("b"),
    /**
     * Value for the queen piece type.
     */
    QUEEN("q"),
    /**
     * Value for the king piece type.
     */
    KING("k"),
    /**
     * Value corresponding to no piece.
     */
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
