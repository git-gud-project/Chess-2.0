package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PieceKing extends Piece {
    private boolean firstMove = false;
    private ArrayList<Move> _possibleMoves;

    public PieceKing(Cell cell, Team team) {
        super(cell, team);
    }

    public Iterator<Move> getPossibleMoves(){
        _possibleMoves = new ArrayList<Move>();
        return null; //Fix so we get the possibleMoves for a pawn. Probably check get the current pos, get posX+1 and so on.
    }

    @Override
    public String toString() {
        if(getTeam().getColor().equals(Color.WHITE)) return "WKing";
        return "BKing";
    }

}
