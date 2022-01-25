package model;

import model.Piece;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PiecePawn extends Piece {

    private boolean firstMove = true;
    private ArrayList<Move> _possibleMoves;

    public PiecePawn(Cell cell, Team team) {
        super(cell, team, PieceType.PAWN);
    }

    //Getter for the iterator containing all possibleMoves.
    //TO-FIX: En croissant!
    public Iterator<Move> getPossibleMoves(){
        _possibleMoves = new ArrayList<Move>();
        Board tempBoard = this.getCell().getBoard();
        int row = this.getCell().getRow();
        int col = this.getCell().getCol();
        if(firstMove){
            //Unnecessary but need to check if this is valid moves.
            _possibleMoves.add(new Move(tempBoard.getCell(row - 1, col), false));
            _possibleMoves.add(new Move(tempBoard.getCell(row - 2, col), false));
        }
        else{
            //Need a check to see if this a valid move.
            _possibleMoves.add(new Move(tempBoard.getCell(row, col + 1), false));
            if(tempBoard.getCell(row+1, col - 1) != null || tempBoard.getCell(row - 1, col - 1) != null){ //change to check if move isValid()
                if(checkEliminate(new Move(tempBoard.getCell(row + 1, col + 1)))){
                    _possibleMoves.add(new Move(tempBoard.getCell(row - 1, col - 1), true));
                }
                else if(checkEliminate(new Move(tempBoard.getCell(row + 1, col - 1)))){
                    _possibleMoves.add(new Move(tempBoard.getCell(row + 1, col - 1), true));
                }
            }
        }
        return _possibleMoves.iterator();
    }

    public void setFirstMove(){
        this.firstMove = true;
    }

    @Override
    public String toString() {
        if(getTeam().getColor().equals(Color.WHITE)) return "WPawn";
        return "BPawn";
    }
}
