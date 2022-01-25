package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PieceBishop extends Piece {
    private Iterator<Move> _possibleMoves;

    public PieceBishop(Cell cell, Team team) {
        super(cell, team, PieceType.BISHOP);
    }

    public Iterator<Move> getPossibleMoves(){
        ArrayList<Move> _possibleMoves = new ArrayList<>();

        int boardSize = this.getCell().getBoard().getGameSize();
        Board currentBoard = this.getCell().getBoard();


        // Check to see how far the bishop can move to right downwards
        for(int i = this.getCell().getRow()+1; i<boardSize; i++) {
            // No piece is in the bishops way

            if(currentBoard.getCell(this.getCell().getCol(), i).getPiece() == null) {
                _possibleMoves.add(new Move(currentBoard.getCell(this.getCell().getCol(), i), false));
            }
            // If a piece is in the rooks way
            else if((currentBoard.getCell(this.getCell().getCol(), i).getPiece() != null)) {
                // If the piece is on another team, it can be eliminated
                if(currentBoard.getCell(this.getCell().getCol(), i).getPiece().getTeam() != this.getTeam()) {
                    _possibleMoves.add(new Move(currentBoard.getCell(this.getCell().getCol(), i), true));
                    break;
                }
                else {
                    break;
                }
            }
        }

        // Check to see how far the rook can move to the left
        for(int i = this.getCell().getRow()-1; i>=0; i--) {
            // No piece is in the rooks way
            if(currentBoard.getCell(this.getCell().getCol(), i).getPiece() == null) {
                _possibleMoves.add(new Move(currentBoard.getCell(this.getCell().getCol(), i), false));
            }
            // If a piece is in the rooks way
            else if((currentBoard.getCell(this.getCell().getCol(), i).getPiece() != null)) {
                // If the piece is on another team, it can be eliminated
                if(currentBoard.getCell(this.getCell().getCol(), i).getPiece().getTeam() != this.getTeam()) {
                    _possibleMoves.add(new Move(currentBoard.getCell(this.getCell().getCol(), i), true));
                    break;
                }
                else {
                    break;
                }
            }
        }

        // Check to see how far the rook can move downwards
        for(int i = this.getCell().getCol()+1; i<boardSize; i++) {
            // No piece is in the rooks way
            if(currentBoard.getCell(i, this.getCell().getRow()).getPiece() == null) {
                _possibleMoves.add(new Move(currentBoard.getCell(i, this.getCell().getRow()), false));
            }
            // If a piece is in the rooks way
            else if((currentBoard.getCell(this.getCell().getCol(), i).getPiece() != null)) {
                // If the piece is on another team, it can be eliminated
                if(currentBoard.getCell(this.getCell().getCol(), i).getPiece().getTeam() != this.getTeam()) {
                    _possibleMoves.add(new Move(currentBoard.getCell(i, this.getCell().getRow()), true));
                    break;
                }
                else {
                    break;
                }
            }
        }

        // Check to see how far the rook can move upwards
        for(int i = this.getCell().getCol()-1; i>=0; i--) {
            // No piece is in the rooks way
            if(currentBoard.getCell(i, this.getCell().getRow()).getPiece() == null) {
                _possibleMoves.add(new Move(currentBoard.getCell(i, this.getCell().getRow()), false));
            }
            // If a piece is in the rooks way
            else if((currentBoard.getCell(this.getCell().getCol(), i).getPiece() != null)) {
                // If the piece is on another team, it can be eliminated
                if(currentBoard.getCell(this.getCell().getCol(), i).getPiece().getTeam() != this.getTeam()) {
                    _possibleMoves.add(new Move(currentBoard.getCell(i, this.getCell().getRow()), true));
                    break;
                }
                else {
                    break;
                }
            }
        }

        return _possibleMoves.iterator();
    }

    public String toString() {
        if(getTeam().getColor().equals(Color.WHITE)) return "WBishop";
        return "BBishop";
    }

}
