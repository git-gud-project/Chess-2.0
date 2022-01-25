package model;

import model.Piece;

import java.util.Iterator;

public class PieceKnight extends Piece {
    private Iterator<Move> _possibleMoves;

    public PieceKnight(Cell cell, int team) {
        super(cell, team);
    }

    public Iterator<Move> getPossibleMoves(){
        return null; //Fix so we get the possibleMoves for a pawn. Probably check get the current pos, get posX+1 and so on.
    }

}
