package model;

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
}
