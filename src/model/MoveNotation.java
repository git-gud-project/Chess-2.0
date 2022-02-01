package model;

public class MoveNotation {

    // Does not work for enPassant or Castling yet

    private int _row,_col, _fromCol;
    private Piece _piece;
    private boolean _eliminates;
    private boolean _enPassant;
    private Board _board;

    public MoveNotation(int fromCol,int toRow, int toCol, Piece piece, boolean eliminates, Board board) {
        _row = toRow;
        _col = toCol;
        _fromCol = fromCol; // Is needed for when pawn eliminates another piece
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
                if(_eliminates) return _board.positionToString(0,_fromCol).substring(0,1)+"x"+colAndRow;
                return colAndRow;
            }
            else if(_eliminates) {
                return piecePrefix+"x"+colAndRow;
            }
            else return piecePrefix+colAndRow;
        }
    }


}
