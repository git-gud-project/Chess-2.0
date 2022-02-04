package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PieceBishop extends Piece {
    private ArrayList<Move> possibleMoves;

    public PieceBishop(Cell cell, Team team) {
        super(cell, team, PieceType.BISHOP);

        possibleMoves = new ArrayList<Move>();
    }

    public Iterator<Move> getPossibleMoves() {
        possibleMoves.clear();

        Board board = this.getCell().getBoard();

        board.calculateMoves(this, possibleMoves, 1, 1);
        board.calculateMoves(this, possibleMoves, -1, 1);
        board.calculateMoves(this, possibleMoves, 1, -1);
        board.calculateMoves(this, possibleMoves, -1, -1);

        return possibleMoves.iterator();
    }

    public String toString() {
        if(getTeam().getColor().equals(Color.WHITE)) return "WBishop";
        return "BBishop";
    }

}
