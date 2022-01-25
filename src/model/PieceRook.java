package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PieceRook extends Piece {

    public PieceRook(Cell cell, Team team) {
        super(cell, team);
    }

    // Add all possible moves to the _possibleMoves list and return its iterator
    public Iterator<Move> getPossibleMoves(){

        ArrayList<Move> _possibleMoves = new ArrayList<>();

        int boardSize = this.getCell().getBoard().getGameSize();
        Board currentBoard = this.getCell().getBoard();


        // Check to see how far the rook can move to the right
        for(int i = this.getCell().getxPos()+1; i<boardSize; i++) {
            // No piece is in the rooks way
            if(currentBoard.getCell(this.getCell().getyPos(), i).getPiece() == null) {
                    _possibleMoves.add(new Move(currentBoard.getCell(this.getCell().getyPos(), i), false));
            }
            // If a piece is in the rooks way
            else if((currentBoard.getCell(this.getCell().getyPos(), i).getPiece() != null)) {
                // If the piece is on another team, it can be eliminated
                if(currentBoard.getCell(this.getCell().getyPos(), i).getPiece().getTeam() != this.getTeam()) {
                    _possibleMoves.add(new Move(currentBoard.getCell(this.getCell().getyPos(), i), true));
                    break;
                }
                else {
                   break;
                }
            }
        }

        // Check to see how far the rook can move to the left
        for(int i = this.getCell().getxPos()-1; i>=0; i--) {
            // No piece is in the rooks way
            if(currentBoard.getCell(this.getCell().getyPos(), i).getPiece() == null) {
                _possibleMoves.add(new Move(currentBoard.getCell(this.getCell().getyPos(), i), false));
            }
            // If a piece is in the rooks way
            else if((currentBoard.getCell(this.getCell().getyPos(), i).getPiece() != null)) {
                // If the piece is on another team, it can be eliminated
                if(currentBoard.getCell(this.getCell().getyPos(), i).getPiece().getTeam() != this.getTeam()) {
                    _possibleMoves.add(new Move(currentBoard.getCell(this.getCell().getyPos(), i), true));
                    break;
                }
                else {
                    break;
                }
            }
        }

        // Check to see how far the rook can move downwards
        for(int i = this.getCell().getyPos()+1; i<boardSize; i++) {
            // No piece is in the rooks way
            if(currentBoard.getCell(i, this.getCell().getxPos()).getPiece() == null) {
                _possibleMoves.add(new Move(currentBoard.getCell(i, this.getCell().getxPos()), false));
            }
            // If a piece is in the rooks way
            else if((currentBoard.getCell(this.getCell().getyPos(), i).getPiece() != null)) {
                // If the piece is on another team, it can be eliminated
                if(currentBoard.getCell(this.getCell().getyPos(), i).getPiece().getTeam() != this.getTeam()) {
                    _possibleMoves.add(new Move(currentBoard.getCell(i, this.getCell().getxPos()), true));
                    break;
                }
                else {
                    break;
                }
            }
        }

        // Check to see how far the rook can move upwards
        for(int i = this.getCell().getyPos()-1; i>=0; i--) {
            // No piece is in the rooks way
            if(currentBoard.getCell(i, this.getCell().getxPos()).getPiece() == null) {
                _possibleMoves.add(new Move(currentBoard.getCell(i, this.getCell().getxPos()), false));
            }
            // If a piece is in the rooks way
            else if((currentBoard.getCell(this.getCell().getyPos(), i).getPiece() != null)) {
                // If the piece is on another team, it can be eliminated
                if(currentBoard.getCell(this.getCell().getyPos(), i).getPiece().getTeam() != this.getTeam()) {
                    _possibleMoves.add(new Move(currentBoard.getCell(i, this.getCell().getxPos()), true));
                    break;
                }
                else {
                    break;
                }
            }
        }

        return _possibleMoves.iterator();
    }

    @Override
    public String toString() {
        if(getTeam().getColor().equals(Color.WHITE)) return "WRook";
        return "BRook";
    }

}