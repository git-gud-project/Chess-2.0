import model.Cell;

import java.util.Iterator;

abstract class Piece {
    private Cell _cell;
    private final boolean _team; //TRUE FOR WHITE, FALSE FOR BLACK.

    public Piece(Cell cell, boolean team){
        this._cell = cell;
        this._team = team;
    }

    void move(Cell newCell){
        if(getPossibleMoves().hasNext()){
            this._cell = newCell;
        }
        else{
            System.out.print("That is not a possible move;");
        }
    }

    abstract Iterator<Cell> getPossibleMoves();

    Iterable<Cell> iterateMoves() { return null; }

}
