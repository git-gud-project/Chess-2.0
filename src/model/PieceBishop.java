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
        int bishopColPos = this.getCell().getCol();
        int bishopRowPos = this.getCell().getRow();

        int inc = 1;

        // Check to see how far the bishop can move down to the right
        while(true) {
            // Check that current position is on the board
            if(currentBoard.isValid(inc+bishopRowPos, inc+bishopColPos)) {
                // No piece is in the bishops way
                if(currentBoard.isEmpty(inc+bishopRowPos, inc+bishopColPos)){
                    _possibleMoves.add(new Move(currentBoard.getCell(inc+bishopRowPos, inc+bishopColPos), false));
                }
                // If a piece is in the bishops way
                else {
                    // If the piece is on another team, it can be eliminated
                    if(currentBoard.getCell(inc+bishopRowPos, inc+bishopColPos).getPiece().getTeam() != this.getTeam()) {
                        _possibleMoves.add(new Move(currentBoard.getCell(inc+bishopRowPos, inc+bishopColPos), true));
                        break;
                    }
                    else break;
                }
                inc++;
            }
            else break;
        }
        inc = 1;
        // Check to see how far the bishop can move up to the right
        while(true) {
            // Check that current position is on the board
            if(currentBoard.isValid(bishopRowPos-inc, inc+bishopColPos)) {
                // No piece is in the bishops way
                if(currentBoard.isEmpty(bishopRowPos-inc, inc+bishopColPos)){
                    _possibleMoves.add(new Move(currentBoard.getCell(bishopRowPos-inc, inc+bishopColPos), false));
                }
                // If a piece is in the bishops way
                else {
                    // If the piece is on another team, it can be eliminated
                    if(currentBoard.getCell(bishopRowPos-inc, inc+bishopColPos).getPiece().getTeam() != this.getTeam()) {
                        _possibleMoves.add(new Move(currentBoard.getCell(bishopRowPos-inc, inc+bishopColPos), true));
                        break;
                    }
                    else break;
                }
                inc++;
            }
            else break;
        }
        inc = 1;
        // Check to see how far the bishop can move down to the left
        while(true) {
            // Check that current position is on the board
            if(currentBoard.isValid(inc+bishopRowPos, bishopColPos-inc)) {
                // No piece is in the bishops way
                if(currentBoard.isEmpty(inc+bishopRowPos, bishopColPos-inc)){
                    _possibleMoves.add(new Move(currentBoard.getCell(inc+bishopRowPos, bishopColPos-inc), false));
                }
                // If a piece is in the bishops way
                else {
                    // If the piece is on another team, it can be eliminated
                    if(currentBoard.getCell(inc+bishopRowPos, bishopColPos-inc).getPiece().getTeam() != this.getTeam()) {
                        _possibleMoves.add(new Move(currentBoard.getCell(inc+bishopRowPos, bishopColPos-inc), true));
                        break;
                    }
                    else break;
                }
                inc++;
            }
            else break;
        }
        inc = 1;
        // Check to see how far the bishop can move up to the left
        while(true) {
            // Check that current position is on the board
            if(currentBoard.isValid(bishopRowPos-inc, bishopColPos-inc)) {
                // No piece is in the bishops way
                if(currentBoard.isEmpty(bishopRowPos-inc, bishopColPos-inc)){
                    _possibleMoves.add(new Move(currentBoard.getCell(bishopRowPos-inc, bishopColPos-inc), false));
                }
                // If a piece is in the bishops way
                else {
                    // If the piece is on another team, it can be eliminated
                    if(currentBoard.getCell(bishopRowPos-inc, bishopColPos-inc).getPiece().getTeam() != this.getTeam()) {
                        _possibleMoves.add(new Move(currentBoard.getCell(bishopRowPos-inc, bishopColPos-inc), true));
                        break;
                    }
                    else break;
                }
                inc++;
            }
            else break;
        }


        return _possibleMoves.iterator();
    }

    public String toString() {
        if(getTeam().getColor().equals(Color.WHITE)) return "WBishop";
        return "BBishop";
    }

}
