package model;

import model.Piece;

import java.util.ArrayList;
import java.util.Iterator;

public class PiecePawn extends Piece {

    private boolean firstMove = false;
    private ArrayList<Move> _possibleMoves;

    public PiecePawn(Cell cell, Team team) {
        super(cell, team, PieceType.PAWN);
    }

    //Getter for the iterator containing all possibleMoves.
    //TO-FIX: En croissant!
    public Iterator<Move> getPossibleMoves(){
        _possibleMoves = new ArrayList<Move>();
        Board tempBoard = this.getCell().getBoard();
        int tempX = this.getCell().getxPos();
        int tempY = this.getCell().getyPos();
        if(firstMove){
            _possibleMoves.add(new Move(tempBoard.getCell(tempX, tempY - 1), false));
            _possibleMoves.add(new Move(tempBoard.getCell(tempX, tempY - 2), false));
        }
        else{
            _possibleMoves.add(new Move(tempBoard.getCell(tempX, tempY + 1), false));
            if(tempBoard.getCell(tempX+1, tempY - 1) != null || tempBoard.getCell(tempX - 1, tempY - 1) != null){
                if(checkEliminate(new Move(tempBoard.getCell(tempX + 1, tempY + 1)))){
                    _possibleMoves.add(new Move(tempBoard.getCell(tempX - 1, tempY - 1), true));
                }
                else if(checkEliminate(new Move(tempBoard.getCell(tempX + 1, tempY - 1)))){
                    _possibleMoves.add(new Move(tempBoard.getCell(tempX + 1, tempY - 1), true));
                }
            }
        }
        return _possibleMoves.iterator();
    }

    public void setFirstMove(){
        this.firstMove = true;
    }
}
