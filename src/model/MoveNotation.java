package model;

public class MoveNotation {

    // Does not work for enPassant or Castling yet

    private int row, col, fromCol;
    private Piece piece;
    private boolean eliminates;
    private boolean enPassant, promotion;
    private Board board;

    // For normal moves and captures
    public MoveNotation(int fromCol,int toRow, int toCol, Piece piece, boolean eliminates, Board board) {
        this.row = toRow;
        this.col = toCol;
        this.fromCol = fromCol; // Is needed for when pawn eliminates another piece
        this.piece = piece;
        this.eliminates = eliminates;
        this.enPassant = false;
        this.promotion = false;
        this.board = board;
    }

    // For en passant
    public MoveNotation(boolean enPassant) {
        enPassant = true;
    }

    // For pawn promotion
    public MoveNotation(int toRow, int toCol, Piece promotedTo, Board board) {
        this.enPassant = false;
        this.row = toRow;
        this.col = toCol;
        this.piece = promotedTo;
        this.board = board;
        this.promotion = true;
    }


    public String toString() {
        if(enPassant) return "e.p";
        else if(promotion) {
            String colAndRow = board.positionToString(row,col);
            String piecePrefix = piece.getPieceType().getFilePrefix();
            return colAndRow + piecePrefix;
        }
        else {
            String colAndRow = board.positionToString(row,col);
            String piecePrefix = piece.getPieceType().getFilePrefix();
            if(piecePrefix.equals("p")) {
                if(eliminates) return board.positionToString(0,fromCol).charAt(0)+"x"+colAndRow;
                return colAndRow;
            }
            else if(eliminates) {
                return piecePrefix+"x"+colAndRow;
            }
            else return piecePrefix+colAndRow;
        }
    }


}
