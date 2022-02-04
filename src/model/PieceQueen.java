package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PieceQueen extends Piece {
    private ArrayList<Move> possibleMoves;

    public PieceQueen(Cell cell, Team team) {
        super(cell, team,PieceType.QUEEN);
    }

    /**
     * Returns the iterator with all possible move for this Queen-piece.
     * @return possibleMoves.iterator() containing all moves.
     */
    public Iterator<Move> getPossibleMoves(){
        possibleMoves = new ArrayList<>();

        Board board = this.getCell().getBoard();

        board.calculateMoves(this, possibleMoves, 1, 1);
        board.calculateMoves(this, possibleMoves, 1, -1);
        board.calculateMoves(this, possibleMoves, -1, 1);
        board.calculateMoves(this, possibleMoves, -1, -1);
        board.calculateMoves(this, possibleMoves, 1, 0);
        board.calculateMoves(this, possibleMoves, -1, 0);
        board.calculateMoves(this, possibleMoves, 0, 1);
        board.calculateMoves(this, possibleMoves, 0, -1);

        return possibleMoves.iterator();
    }

    @Override
    public String toString() {
        if(getTeam().getColor().equals(Color.WHITE)) return "WQueen";
        return "BQueen";
    }
}
