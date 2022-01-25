package model;

import model.Piece;

import java.util.ArrayList;
import java.util.Iterator;

public class PiecePawn extends Piece {

    private boolean firstMove = false;
    private ArrayList<Move> _possibleMoves;

    public PiecePawn(Cell cell, int team) {
        super(cell, team);
    }

    //Getter for the iterator containing all possibleMoves.
    //TO-FIX: En croissant!
    public Iterator<Move> getPossibleMoves(){
        if(firstMove){
            _possibleMoves.add(new Move(new Cell(this.getCell().getxPos(), this.getCell().getyPos()+1), false));
            _possibleMoves.add(new Move(new Cell(this.getCell().getxPos(), this.getCell().getyPos()+2), false)); //Fel implementering eftersom vi skapar en ny cell. Ska istället referera till brädans cell men vi har inte kommit så långt.
        }
        else{
            _possibleMoves.add(new Move(new Cell(this.getCell().getxPos(), this.getCell().getyPos()+1), false));
            if(checkEliminate(new Move(new Cell(this.getCell().getxPos() - 1, this.getCell().getyPos() + 1)))){
                _possibleMoves.add(new Move(new Cell(this.getCell().getxPos() - 1, this.getCell().getyPos() + 1), true));
            }
            else if(checkEliminate(new Move(new Cell(this.getCell().getxPos() + 1, getCell().getyPos() + 1)))){
                _possibleMoves.add(new Move(new Cell(this.getCell().getxPos() + 1, this.getCell().getyPos() + 1), true));
            }
        }
        return _possibleMoves.iterator();
    }
}
