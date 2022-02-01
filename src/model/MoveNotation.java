package model;

public class MoveNotation {

    // Does not work for enPassant or Castling yet

    private int _row,_col;
    private Piece _piece;
    private boolean _eliminates;
    private boolean _enPassant;
    private Board _board;

    public MoveNotation(int toRow, int toCol, Piece piece, boolean eliminates, Board board) {
        _row = toRow;
        _col = toCol;
        _piece = piece;
        _eliminates = eliminates;
        _enPassant = false;
        _board = board;
    }

    public MoveNotation(boolean enPassant) {
        _enPassant = true;
    }


    public String toString() {
        if(_enPassant) return "e.p";
        else {
            String colAndRow = _board.positionToString(_row,_col);
            String piecePrefix = _piece.getPieceType().getFilePrefix();
            if(piecePrefix.equals("p")) {
                return colAndRow;
            }
            else if(_eliminates) {
                return piecePrefix+"x"+colAndRow;
            }
            else return piecePrefix+colAndRow;
        }
    }


}
