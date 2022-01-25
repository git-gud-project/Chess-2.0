package model;

public enum PieceType {
    BISHOP("b"),
    KING("k"),
    KNIGHT("h"),
    PAWN("p"),
    QUEEN("q"),
    ROOK("t");

    private String _filePrefix;

    private PieceType(String prefix) {
        _filePrefix = prefix;
    }
    
    public String getFilePrefix() {
        return _filePrefix;
    }
}
