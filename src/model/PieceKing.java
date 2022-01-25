package model;

import java.util.ArrayList;
import java.util.Iterator;

public class PieceKing extends Piece {
    private boolean firstMove = false;
    private ArrayList<Move> _possibleMoves;

    public PieceKing(Cell cell, Team team) {
        super(cell, team);
    }

    public Iterator<Move> getPossibleMoves(){
        return null; //Fix so we get the possibleMoves for a pawn. Probably check get the current pos, get posX+1 and so on.
    }

}
