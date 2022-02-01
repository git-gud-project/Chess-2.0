package model;

import java.util.Iterator;

public abstract class Piece {
    private Cell _cell;
    private final Team _team;
    private PieceType _type;

    public Piece(Cell cell, Team team, PieceType type){
        this._cell = cell;
        this._team = team;
        _type = type;
    }
    //Abstract classes
    public abstract Iterator<Move> getPossibleMoves();

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
        if((move.getCell().getPiece() != null) && (move.getCell().getPiece().getTeam() != this.getTeam())){
            return true;
        }
        else{
            return false;
        }
    }

    public void onMove(Cell oldCell, Cell newCell, boolean State) {}

    //moves the cell of the piece.
    public void move(Cell newCell) {
        Cell oldCell = this.getCell();
        _cell.setPiece(null);
        _cell = newCell;
        _cell.setPiece(this);

        onMove(oldCell, newCell, true);
    }

    public void fakeMove(Cell newCell) {
        Cell oldCell = this.getCell();
        _cell.setPiece(null);
        _cell = newCell;
        _cell.setPiece(this);

        onMove(oldCell, newCell, false);
    }
}
