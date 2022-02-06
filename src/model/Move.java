package model;

public class Move {
    private Cell moveCell, fromCell;
    private Piece piece;
    private boolean elimination;
    private boolean isEnPassant;
    private boolean isCastleKingSide;
    private boolean isCastleQueenSide;
    private boolean isPromotion;

    public Move(Cell toCell, Cell fromCell) {
        this.moveCell = toCell;
        this.fromCell = fromCell;
        this.piece = fromCell.getPiece();
    }

    public Move(Cell toCell, Cell fromCell, boolean eliminatable) {
        this.moveCell = toCell;
        this.fromCell = fromCell;
        this.piece = fromCell.getPiece();
        this.elimination = eliminatable;
    }

    public Move(Cell toCell, PieceType type) {
        this.moveCell = toCell;
        this.isPromotion = true;
    }

    public Cell getToCell() {
        return moveCell;
    }

    public Cell getFromCell() {
        return fromCell;
    }

    public boolean isEliminatable() {
        return elimination;
    }

    public boolean getIsEnPassant() {
        return isEnPassant;
    }

    public void setIsEnPassant(boolean isEnPassant) {
        this.isEnPassant = isEnPassant;
    }

    public boolean getIsCastleKingSide() {
        return isCastleKingSide;
    }

    public void setIsCastleKingSide(boolean isCastleKingSide) {
        this.isCastleKingSide = isCastleKingSide;
    }

    public boolean getIsCastleQueenSide() {
        return isCastleQueenSide;
    }

    public void setIsCastleQueenSide(boolean isCastleQueenSide) {
        this.isCastleQueenSide = isCastleQueenSide;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public PieceType getPieceType() {
        return this.piece.getPieceType();
    }

    public String toString() {
        if(isEnPassant) return "e.p";
        else if(isCastleKingSide) return "0-0";
        else if(isCastleQueenSide) return "0-0-0";
        else if(isPromotion) {
            String colAndRow = moveCell.toString();
            String piecePrefix = piece.getPieceType().getFilePrefix();
            return colAndRow + piecePrefix;
        }
        else {
            String colAndRow = moveCell.toString();
            String piecePrefix = piece.getPieceType().getFilePrefix();
            if(piecePrefix.equals("p")) {
                if(elimination) return moveCell.getBoard().positionToString(0, fromCell.getCol()).charAt(0)+"x"+colAndRow;
                else return colAndRow;
            }
            else if(elimination) {
                return piecePrefix+"x"+colAndRow;
            }
            else return piecePrefix+colAndRow;
        }
    }
}
