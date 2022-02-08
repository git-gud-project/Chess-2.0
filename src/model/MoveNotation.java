package model;

public class MoveNotation {

    // Does not work for enPassant or Castling yet

    private int _row,_col, _fromCol;
    private Piece _piece;
    private boolean _eliminates;
    private boolean _enPassant, _promotion;
    private Board _board;

    // For normal moves and captures
    public MoveNotation(int fromCol,int toRow, int toCol, Piece piece, boolean eliminates, Board board) {
        _row = toRow;
        _col = toCol;
        _fromCol = fromCol; // Is needed for when pawn eliminates another piece
        _piece = piece;
        _eliminates = eliminates;
        _enPassant = false;
        _promotion = false;
        _board = board;
    }

    // For en passant
    public MoveNotation(boolean enPassant) {
        _enPassant = true;
    }

    // For pawn promotion
    public MoveNotation(int toRow, int toCol, Piece promotedTo, Board board) {
        _enPassant = false;
        _row = toRow;
        _col = toCol;
        _piece = promotedTo;
        _board = board;
        _promotion = true;
    }


    public String toString() {
        if(_enPassant) return "e.p";
        else if(_promotion) {
            String colAndRow = _board.positionToString(_row,_col);
            String piecePrefix = _piece.getPieceType().getFilePrefix();
            return colAndRow + piecePrefix;
        }
        else {
            String colAndRow = _board.positionToString(_row,_col);
            String piecePrefix = _piece.getPieceType().getFilePrefix();
            if(piecePrefix.equals("p")) {
                if(_eliminates) return _board.positionToString(0,_fromCol).charAt(0)+"x"+colAndRow;
                return colAndRow;
            }
            else if(_eliminates) {
                return piecePrefix+"x"+colAndRow;
            }
            else return piecePrefix+colAndRow;
        }
    }

    // Getters

    public int getRow() { return this._row; }
    public int getCol() { return this._col; }
    public int getFromCol() { return this._fromCol; }
    public boolean getEliminates() { return this._eliminates; }
    public boolean getEnPassant() { return this._enPassant; }
    public boolean getPromotion() { return this._promotion; }
    public Piece getPiece() { return this._piece; }

}
