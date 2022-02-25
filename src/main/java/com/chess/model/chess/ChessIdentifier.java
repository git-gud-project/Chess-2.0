package com.chess.model.chess;

import com.chess.model.Identifier;

public enum ChessIdentifier implements Identifier {
    PAWN("p"),
    ROOK("r"),
    KNIGHT("n"),
    BISHOP("b"),
    QUEEN("q"),
    KING("k"),
    WHITE("white"),
    BLACK("black"),
    NULL("");
    
    private final String id;

    private ChessIdentifier(String id) {
        this.id = id;
    }

    public static ChessIdentifier getIdentifier(String id) {
        for (ChessIdentifier identifier : values()) {
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

        ChessIdentifier that = (ChessIdentifier) o;

        return id.equals(that.id);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
