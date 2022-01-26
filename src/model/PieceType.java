package model;

public enum PieceType {
    BISHOP("b"),
    KING("k"),
    KNIGHT("n"),
    PAWN("p"),
    QUEEN("q"),
    ROOK("r");

    private String _filePrefix;

    private PieceType(String prefix) {
        _filePrefix = prefix;
    }
    
    public String getFilePrefix() {
        return _filePrefix;
    }
}
