package model;

import java.util.Iterator;

abstract class Piece {
    private Cell _cell;
    private final int _team; //0 for white, 1 for black. Used for later scalability for XvX games.

    public Piece(Cell cell, int team){
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

    abstract Iterator<Move> getPossibleMoves();

    Iterable<Cell> iterateMoves() { return null; }

}
