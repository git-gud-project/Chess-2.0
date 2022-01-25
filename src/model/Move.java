package model;

public class Move {
    private Cell _moveCell;
    private boolean _elimination;

    public Move(Cell toCell){
        this._moveCell = toCell;
    }
}
