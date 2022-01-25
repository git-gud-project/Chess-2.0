package model;

import model.Piece;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PieceKnight extends Piece {
    private ArrayList<Move> _possibleMoves;
    Board tempBoard = this.getCell().getBoard();

    public PieceKnight(Cell cell, Team team) {
        super(cell, team,PieceType.KNIGHT);
    }

    public Iterator<Move> getPossibleMoves(){
        _possibleMoves = new ArrayList<>();
        int row = this.getCell().getRow();
        int col = this.getCell().getCol();
        System.out.println(tempBoard.isValid(row + 2, col - 1));
        if(tempBoard.isValid(row + 2, col - 1)){
            if(checkEliminate(new Move(tempBoard.getCell(row + 2, col - 1)))){
                _possibleMoves.add(new Move(tempBoard.getCell(row +2, col - 1), true));
            }
            else if(!checkEliminate(new Move(tempBoard.getCell(row + 2, col - 1))) && tempBoard.getCell(row + 2, col - 1).getPiece() != null){
                assert true;
            }
            else{
                _possibleMoves.add(new Move(tempBoard.getCell(row + 2, col - 1), false));
            }
        }
        if(tempBoard.isValid(row + 2, col + 1)){ //change to check if move isValid();
            if(checkEliminate(new Move(tempBoard.getCell(row + 2, col + 1)))){
                _possibleMoves.add(new Move(tempBoard.getCell(row +2, col + 1), true));
            }
            else if(!checkEliminate(new Move(tempBoard.getCell(row + 2, col + 1))) && tempBoard.getCell(row + 2, col + 1).getPiece() != null){
                assert true;
            }
            else{
                _possibleMoves.add(new Move(tempBoard.getCell(row + 2, col + 1), false));
            }
        }

        if(tempBoard.isValid(row - 2, col - 1)){ //change to check if move isValid();
            if(checkEliminate(new Move(tempBoard.getCell(row - 2, col - 1)))){
                _possibleMoves.add(new Move(tempBoard.getCell(row - 2, col - 1), true));
            }
            else if(!checkEliminate(new Move(tempBoard.getCell(row - 2, col - 1))) && tempBoard.getCell(row - 2, col - 1).getPiece() != null){
                assert true;
            }
            else{
                _possibleMoves.add(new Move(tempBoard.getCell(row - 2, col - 1), false));
            }
        }
        if(tempBoard.isValid(row - 2, col + 1)){ //change to check if move isValid();
            if(checkEliminate(new Move(tempBoard.getCell(row - 2, col + 1)))){
                _possibleMoves.add(new Move(tempBoard.getCell(row - 2, col + 1), true));
            }
            else if(!checkEliminate(new Move(tempBoard.getCell(row - 2, col + 1))) && tempBoard.getCell(row - 2, col + 1).getPiece() != null){
                assert true;
            }
            else{
                _possibleMoves.add(new Move(tempBoard.getCell(row - 2, col + 1), false));
            }
        }

        if(tempBoard.isValid(row + 1, col - 2)){ //change to check if move isValid();
            if(checkEliminate(new Move(tempBoard.getCell(row + 1, col - 2)))){
                _possibleMoves.add(new Move(tempBoard.getCell(row + 1, col - 2), true));
            }
            else if(!checkEliminate(new Move(tempBoard.getCell(row + 1, col - 2))) && tempBoard.getCell(row + 1, col - 2).getPiece() != null){
                assert true;
            }
            else{
                _possibleMoves.add(new Move(tempBoard.getCell(row + 1, col - 2), false));
            }
        }
        if(tempBoard.isValid(row - 1, col - 2)){ //change to check if move isValid();
            if(checkEliminate(new Move(tempBoard.getCell(row - 1, col  - 2)))){
                _possibleMoves.add(new Move(tempBoard.getCell(row - 1, col - 2), true));
            }
            else if(!checkEliminate(new Move(tempBoard.getCell(row - 1, col - 2))) && tempBoard.getCell(row - 1, col - 2).getPiece() != null){
                assert true;
            }
            else{
                _possibleMoves.add(new Move(tempBoard.getCell(row - 1, col - 2), false));
            }
        }

        if(tempBoard.isValid(row + 1, col + 2)){ //change to check if move isValid();
            if(checkEliminate(new Move(tempBoard.getCell(row + 1, col + 2)))){
                _possibleMoves.add(new Move(tempBoard.getCell(row + 1, col + 2), true));
            }
            else if(!checkEliminate(new Move(tempBoard.getCell(row + 1, col + 2))) && tempBoard.getCell(row + 1, col + 2).getPiece() != null){
                assert true;
            }
            else{
                _possibleMoves.add(new Move(tempBoard.getCell(row + 1, col + 2), false));
            }
        }
        if(tempBoard.isValid(row - 1, col + 2)){ //change to check if move isValid();
            if(checkEliminate(new Move(tempBoard.getCell(row - 1, col  + 2)))){
                _possibleMoves.add(new Move(tempBoard.getCell(row - 1, col + 2), true));
            }
            else if(!checkEliminate(new Move(tempBoard.getCell(row - 1, col + 2))) && tempBoard.getCell(row - 1, col + 2).getPiece() != null){
                assert true;
            }
            else{
                _possibleMoves.add(new Move(tempBoard.getCell(row - 1, col + 2), false));
            }
        }
        return _possibleMoves.iterator(); //Fix so we get the possibleMoves for a pawn. Probably check get the current pos, get posX+1 and so on.
    }

    @Override
    public String toString() {
        if(getTeam().getColor().equals(Color.WHITE)) return "WKnight";
        return "BKnight";
    }

}
