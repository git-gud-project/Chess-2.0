package model;

public class Move {
    private Cell _moveCell;
    private boolean _elimination;


    public Move(Cell toCell, Boolean eliminates){
        this._moveCell = toCell;
        this._elimination = eliminates;
    }
    public Move(Cell toCell, boolean eliminatable){
        this._moveCell = toCell;
        this._elimination = eliminatable;
    }

    public Cell getCell(){
        return _moveCell;
    }
}
