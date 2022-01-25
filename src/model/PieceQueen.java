package model;

import java.util.Iterator;

public class PieceQueen extends Piece {
    private Iterator<Move> _possibleMoves;

    public PieceQueen(Cell cell, int team) {
        super(cell, team);
    }

    public Iterator<Move> getPossibleMoves(){
        return null; //Fix so we get the possibleMoves for a pawn. Probably check get the current pos, get posX+1 and so on.
    }

}
