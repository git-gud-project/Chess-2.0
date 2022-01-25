package model;

import java.awt.*;
import java.util.Iterator;

public class PieceBishop extends Piece {
    private Iterator<Move> _possibleMoves;

    public PieceBishop(Cell cell, Team team) {
        super(cell, team, PieceType.BISHOP);
    }

    public Iterator<Move> getPossibleMoves(){
       return null;
    }

    public String toString() {
        if(getTeam().getColor().equals(Color.WHITE)) return "WBishop";
        return "BBishop";
    }

}
