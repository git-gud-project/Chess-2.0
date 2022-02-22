package com.chess.model.chess;

import com.chess.model.Identifier;

public enum PieceType {
    BISHOP("b"),
    KING("k"),
    KNIGHT("n"),
    PAWN("p"),
    QUEEN("q"),
    ROOK("r");

    private String filePrefix;

    private PieceType(String prefix) {
        filePrefix = prefix;
    }
    
    public String getFilePrefix() {
        return filePrefix;
    }

    public Identifier getTypeIdentifier() {
        return new Identifier(filePrefix);
    }
}
