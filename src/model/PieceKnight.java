package model;

import model.Piece;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PieceKnight extends Piece {
    private ArrayList<Move> _possibleMoves;
    Board tempBoard = this.getCell().getBoard();
    int tempX = this.getCell().getRow();
    int tempY = this.getCell().getCol();

    public PieceKnight(Cell cell, Team team) {
        super(cell, team,PieceType.KNIGHT);
    }

    public Iterator<Move> getPossibleMoves(){
        _possibleMoves = new ArrayList<Move>();
        if(tempBoard.getCell(tempX + 2, tempY - 1) != null){ //change to check if move isValid();
            if(checkEliminate(new Move(tempBoard.getCell(tempX + 2, tempY - 1)))){
                _possibleMoves.add(new Move(tempBoard.getCell(tempX +2, tempY - 1), true));
            }
            else if(checkEliminate(new Move(tempBoard.getCell(tempX + 2, tempY - 1)))){
                _possibleMoves.add(new Move(tempBoard.getCell(tempX + 2, tempY - 1), false));
            }
        }
        if(tempBoard.getCell(tempX + 2, tempY + 1) != null){ //change to check if move isValid();
            if(checkEliminate(new Move(tempBoard.getCell(tempX + 2, tempY + 1)))){
                _possibleMoves.add(new Move(tempBoard.getCell(tempX +2, tempY + 1), true));
            }
            else if(checkEliminate(new Move(tempBoard.getCell(tempX + 2, tempY + 1)))){
                _possibleMoves.add(new Move(tempBoard.getCell(tempX + 2, tempY + 1), false));
            }
        }

        if(tempBoard.getCell(tempX - 2, tempY - 1) != null){ //change to check if move isValid();
            if(checkEliminate(new Move(tempBoard.getCell(tempX - 2, tempY - 1)))){
                _possibleMoves.add(new Move(tempBoard.getCell(tempX - 2, tempY - 1), true));
            }
            else if(checkEliminate(new Move(tempBoard.getCell(tempX - 2, tempY - 1)))){
                _possibleMoves.add(new Move(tempBoard.getCell(tempX - 2, tempY - 1), false));
            }
        }
        if(tempBoard.getCell(tempX - 2, tempY + 1) != null){ //change to check if move isValid();
            if(checkEliminate(new Move(tempBoard.getCell(tempX - 2, tempY + 1)))){
                _possibleMoves.add(new Move(tempBoard.getCell(tempX - 2, tempY + 1), true));
            }
            else if(checkEliminate(new Move(tempBoard.getCell(tempX - 2, tempY + 1)))){
                _possibleMoves.add(new Move(tempBoard.getCell(tempX - 2, tempY + 1), false));
            }
        }

        if(tempBoard.getCell(tempX + 1, tempY - 2) != null){ //change to check if move isValid();
            if(checkEliminate(new Move(tempBoard.getCell(tempX + 1, tempY - 2)))){
                _possibleMoves.add(new Move(tempBoard.getCell(tempX + 1, tempY - 2), true));
            }
            else if(checkEliminate(new Move(tempBoard.getCell(tempX + 1, tempY - 2)))){
                _possibleMoves.add(new Move(tempBoard.getCell(tempX + 1, tempY - 2), false));
            }
        }
        if(tempBoard.getCell(tempX - 1, tempY - 2) != null){ //change to check if move isValid();
            if(checkEliminate(new Move(tempBoard.getCell(tempX - 1, tempY  - 2)))){
                _possibleMoves.add(new Move(tempBoard.getCell(tempX - 1, tempY - 2), true));
            }
            else if(checkEliminate(new Move(tempBoard.getCell(tempX - 1, tempY - 2)))){
                _possibleMoves.add(new Move(tempBoard.getCell(tempX - 1, tempY - 2), false));
            }
        }

        if(tempBoard.getCell(tempX + 1, tempY + 2) != null){ //change to check if move isValid();
            if(checkEliminate(new Move(tempBoard.getCell(tempX + 1, tempY + 2)))){
                _possibleMoves.add(new Move(tempBoard.getCell(tempX + 1, tempY + 2), true));
            }
            else if(checkEliminate(new Move(tempBoard.getCell(tempX + 1, tempY + 2)))){
                _possibleMoves.add(new Move(tempBoard.getCell(tempX + 1, tempY + 2), false));
            }
        }
        if(tempBoard.getCell(tempX - 1, tempY + 2) != null){ //change to check if move isValid();
            if(checkEliminate(new Move(tempBoard.getCell(tempX - 1, tempY  + 2)))){
                _possibleMoves.add(new Move(tempBoard.getCell(tempX - 1, tempY + 2), true));
            }
            else if(checkEliminate(new Move(tempBoard.getCell(tempX - 1, tempY + 2)))){
                _possibleMoves.add(new Move(tempBoard.getCell(tempX - 1, tempY + 2), false));
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
