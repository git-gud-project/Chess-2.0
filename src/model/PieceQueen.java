package model;

import java.util.ArrayList;
import java.util.Iterator;

public class PieceQueen extends Piece {
    private ArrayList<Move> _possibleMoves;

    public PieceQueen(Cell cell, Team team) {
        super(cell, team,PieceType.QUEEN);
    }

    public Iterator<Move> getPossibleMoves(){
        _possibleMoves = new ArrayList<Move>();
        return null; //Fix so we get the possibleMoves for a pawn. Probably check get the current pos, get posX+1 and so on.
    }

}
