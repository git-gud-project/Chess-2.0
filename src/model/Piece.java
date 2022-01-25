package model;

import java.util.Iterator;

public abstract class Piece {
    private Cell _cell;
    private final Team _team; //0 for white, 1 for black. Used for later scalability for XvX games.
    private PieceType _type;

    public Piece(Cell cell, Team team, PieceType type){
        this._cell = cell;
        this._team = team;
        _type = type;
    }
    //Abstract classes
    abstract Iterator<Move> getPossibleMoves();

    Iterable<Cell> iterateMoves() { return null; }

    //Getters and Setters.
    public PieceType getPieceType(){return _type;}

    public Cell getCell() {
        return this._cell;
    }

    public Team getTeam(){
        return _team;
    }
    // Common methods for all pieces.
    /**
     * A help method that checks if the move contains a possible eliminations of a piece.
     * @param move - The move we want to move the piece to.
     * @return true if there's a possible elimination or false if there's none.
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
