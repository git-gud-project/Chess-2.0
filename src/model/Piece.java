package model;

import java.util.Iterator;

abstract class Piece {
    private Cell _cell;
    private final Team _team; //0 for white, 1 for black. Used for later scalability for XvX games.

    public Piece(Cell cell, Team team){
        this._cell = cell;
        this._team = team;
    }
    //Abstract classes
    abstract Iterator<Move> getPossibleMoves();

    Iterable<Cell> iterateMoves() { return null; }

    //Getters and Setters.
    public Cell getCell() {
        return this._cell;
    }

    public int getTeam(){
        return _team;
    }
    // Common methods for all pieces.
    /**
     * A help method that checks if the move contains a possible eliminations of a piece.
     * @param move
     * @return
     */
    public boolean checkEliminate(Move move){
        if(move.getCell().getPiece().getTeam() != this.getTeam()){
            return true;
        }
        else{
            return false;
        }
    }

    //moves the cell of the piece.
    void move(Cell newCell){
        if(getPossibleMoves().hasNext()){
            this._cell = newCell;
        }
        else{
            System.out.print("That is not a possible move;");
        }
    }
}
