package model;

public class Move {
    private Cell moveCell;
    private boolean elimination;
    private boolean isEnPassant;
    private boolean isCastleKingSide;
    private boolean isCastleQueenSide;

    public Move(Cell toCell) {
        this.moveCell = toCell;
    }

    public Move(Cell toCell, boolean eliminatable) {
        this.moveCell = toCell;
        this.elimination = eliminatable;
    }

    public Cell getCell() {
        return moveCell;
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
}
