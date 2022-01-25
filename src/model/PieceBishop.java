package model;

import java.util.Iterator;

public class PieceBishop extends Piece {
    private Iterator<Move> _possibleMoves;

    public PieceBishop(Cell cell, Team team) {
        super(cell, team);
    }

    public Iterator<Move> getPossibleMoves(){
        return null; //Fix so we get the possibleMoves for a pawn. Probably check get the current pos, get posX+1 and so on.
    }

}
