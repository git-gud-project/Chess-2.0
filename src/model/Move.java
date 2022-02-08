package model;

public class Move {
    private Cell moveCell, fromCell;
    private Piece piece;
    private boolean elimination;
    private boolean isEnPassant;
    private boolean isCastleKingSide;
    private boolean isCastleQueenSide;
    private boolean isPromotion;
    private boolean isCheck;
    private boolean isCheckMate;
    private PieceType promotedTo;

    public Move(Cell toCell, Cell fromCell) {
        this.moveCell = toCell;
        this.fromCell = fromCell;
        this.piece = fromCell.getPiece();
        this.isCheck = false;
        this.isCheckMate = false;
    }

    public Move(Cell toCell, Cell fromCell, boolean eliminatable) {
        this.moveCell = toCell;
        this.fromCell = fromCell;
        this.piece = fromCell.getPiece();
        this.elimination = eliminatable;
        this.isCheck = false;
        this.isCheckMate = false;
    }

    public Move(Cell toCell, PieceType type) {
        this.moveCell = toCell;
        this.isPromotion = true;
        this.promotedTo = type;
        this.isCheck = false;
        this.isCheckMate = false;
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

    public void addCheck() {
        this.isCheck = true;
    }

    public void addCheckMate() {
        this.isCheckMate = true;
    }

    public String toString() {
        if(isCastleKingSide) return "0-0";
        else if(isCastleQueenSide) return "0-0-0";
        else if(isPromotion) {
            String colAndRow = moveCell.toString();
            String piecePrefix = promotedTo.getFilePrefix();
            return colAndRow + piecePrefix;
        }
        else {
            StringBuilder result = new StringBuilder("");
            String colAndRow = moveCell.toString();
            String piecePrefix = piece.getPieceType().getFilePrefix();
            if(piecePrefix.equals("p")) {
                if(elimination) {
                    if(isEnPassant) result.append(moveCell.getBoard().positionToString(0, fromCell.getCol()).charAt(0)+"x"+colAndRow + " e.p");
                    else result.append(moveCell.getBoard().positionToString(0, fromCell.getCol()).charAt(0)+"x"+colAndRow);
                }
                else result.append(colAndRow);
            }
            else if(elimination) {
                result.append(piecePrefix+"x"+colAndRow);
            }
            else result.append(piecePrefix+colAndRow);

            if(isCheck) result.append("+");
            else if(isCheckMate) result.append("#");

            return result.toString();
        }
    }
}
