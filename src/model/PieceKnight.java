package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PieceKnight extends Piece {
    private ArrayList<Move> possibleMoves;

    public PieceKnight(Cell cell, Team team) {
        super(cell, team,PieceType.KNIGHT);
    }

    /**
     * Returns the iterator with all possible move for this Knight-piece.
     * @return possibleMoves.iterator() containing all moves.
     */
    public Iterator<Move> getPossibleMoves(){
        possibleMoves = new ArrayList<>();

        Board board = this.getCell().getBoard();

        board.calculateMoves(this, possibleMoves, 1, 2, 1);
        board.calculateMoves(this, possibleMoves, 1, -2, 1);
        board.calculateMoves(this, possibleMoves, -1, 2, 1);
        board.calculateMoves(this, possibleMoves, -1, -2, 1);
        board.calculateMoves(this, possibleMoves, 2, 1, 1);
        board.calculateMoves(this, possibleMoves, 2, -1, 1);
        board.calculateMoves(this, possibleMoves, -2, 1, 1);
        board.calculateMoves(this, possibleMoves, -2, -1, 1);

        return possibleMoves.iterator();
    }

    @Override
    public String toString() {
        if(getTeam().getColor().equals(Color.WHITE)) return "WKnight";
        return "BKnight";
    }

}
