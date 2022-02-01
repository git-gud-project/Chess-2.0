package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PieceRook extends Piece {
    private boolean firstMove = false;
    private ArrayList<Move> _possibleMoves;

    public PieceRook(Cell cell, Team team) {
        super(cell, team,PieceType.ROOK);
        _possibleMoves = new ArrayList<Move>();
    }

    // Add all possible moves to the _possibleMoves list and return its iterator
    public Iterator<Move> getPossibleMoves(){
        _possibleMoves.clear();

        Board board = this.getCell().getBoard();

        board.calculateMoves(this, _possibleMoves, 1, 0);
        board.calculateMoves(this, _possibleMoves, -1, 0);
        board.calculateMoves(this, _possibleMoves, 0, 1);
        board.calculateMoves(this, _possibleMoves, 0, -1);

        return _possibleMoves.iterator();
    }

    @Override
    public String toString() {
        if(getTeam().getColor().equals(Color.WHITE)) return "WRook";
        return "BRook";
    }

    public void setFirstMove(boolean firstMove){
        this.firstMove = firstMove;
    }

    public boolean getFirstMove(){
        return this.firstMove;
    }
}