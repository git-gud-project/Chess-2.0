package model;

public class Move {
    private Cell _moveCell;
    private boolean _elimination;
    private boolean _isEnPassant;
    private boolean _isCastleKingSide;
    private boolean _isCastleQueenSide;

    public Move(Cell toCell) {
        this._moveCell = toCell;
    }

    public Move(Cell toCell, boolean eliminatable) {
        this._moveCell = toCell;
        this._elimination = eliminatable;
    }

    public Cell getCell() {
        return _moveCell;
    }

    public boolean isEliminatable() {
        return _elimination;
    }

    public boolean getIsEnPassant() {
        return _isEnPassant;
    }

    public void setIsEnPassant(boolean isEnPassant) {
        this._isEnPassant = isEnPassant;
    }

    public boolean getIsCastleKingSide() {
        return _isCastleKingSide;
    }

    public void setIsCastleKingSide(boolean isCastleKingSide) {
        this._isCastleKingSide = isCastleKingSide;
    }

    public boolean getIsCastleQueenSide() {
        return _isCastleQueenSide;
    }

    public void setIsCastleQueenSide(boolean isCastleQueenSide) {
        this._isCastleQueenSide = isCastleQueenSide;
    }
}
