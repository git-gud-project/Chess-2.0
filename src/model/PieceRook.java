package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PieceRook extends Piece {
    private ArrayList<Move> possibleMoves;

    public PieceRook(Cell cell, Team team) {
        super(cell, team,PieceType.ROOK);
        possibleMoves = new ArrayList<Move>();
    }

    // Add all possible moves to the possibleMoves list and return its iterator
    public Iterator<Move> getPossibleMoves(){
        possibleMoves.clear();

        Board board = this.getCell().getBoard();

        board.calculateMoves(this, possibleMoves, 1, 0);
        board.calculateMoves(this, possibleMoves, -1, 0);
        board.calculateMoves(this, possibleMoves, 0, 1);
        board.calculateMoves(this, possibleMoves, 0, -1);

        return possibleMoves.iterator();
    }

    @Override
    public String toString() {
        if(getTeam().getColor().equals(Color.WHITE)) return "WRook";
        return "BRook";
    }
}