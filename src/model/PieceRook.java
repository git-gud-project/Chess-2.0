package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PieceRook extends Piece {

    public PieceRook(Cell cell, Team team) {
        super(cell, team,PieceType.ROOK);
    }

    // Add all possible moves to the _possibleMoves list and return its iterator
    public Iterator<Move> getPossibleMoves(){

        ArrayList<Move> _possibleMoves = new ArrayList<>();

        int boardSize = this.getCell().getBoard().getGameSize();
        Board currentBoard = this.getCell().getBoard();
        int rookYPos = this.getCell().getyPos();
        int rookXPos = this.getCell().getxPos();


        // Check to see how far the rook can move to the right
        for(int i = rookXPos+1; i<boardSize; i++) {
            // No piece is in the rooks way
            if(currentBoard.isEmpty(rookXPos, i)){
                    _possibleMoves.add(new Move(currentBoard.getCell(i, rookYPos), false));
            }
            // If a piece is in the rooks way
            else {
                // If the piece is on another team, it can be eliminated
                if(currentBoard.getCell(i, rookYPos).getPiece().getTeam() != this.getTeam()) {
                    _possibleMoves.add(new Move(currentBoard.getCell(i, rookYPos), true));
                    break;
                }
                else {
                   break;
                }
            }
        }

        // Check to see how far the rook can move to the left
        for(int i = rookXPos-1; i>=0; i--) {
            // No piece is in the rooks way
            if(currentBoard.isEmpty(rookXPos, i)) {
                _possibleMoves.add(new Move(currentBoard.getCell(i, rookYPos), false));
            }
            // If a piece is in the rooks way
            else {
                // If the piece is on another team, it can be eliminated
                if(currentBoard.getCell(rookYPos, i).getPiece().getTeam() != this.getTeam()) {
                    _possibleMoves.add(new Move(currentBoard.getCell(i, rookYPos), true));
                    break;
                }
                else {
                    break;
                }
            }
        }

        // Check to see how far the rook can move downwards
        for(int i = rookYPos+1; i<boardSize; i++) {
            // No piece is in the rooks way
            if(currentBoard.isEmpty(rookXPos, i)) {
                _possibleMoves.add(new Move(currentBoard.getCell(rookXPos, i), false));
            }
            // If a piece is in the rooks way
            else {
                // If the piece is on another team, it can be eliminated
                if(currentBoard.getCell(i, rookYPos).getPiece().getTeam() != this.getTeam()) {
                    _possibleMoves.add(new Move(currentBoard.getCell(rookXPos, i), true));
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