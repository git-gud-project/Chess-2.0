package model;

import model.Piece;

import java.util.ArrayList;
import java.util.Iterator;

public class PieceKnight extends Piece {
    private ArrayList<Move> _possibleMoves;

    public PieceKnight(Cell cell, Team team) {
        super(cell, team);
    }

    public Iterator<Move> getPossibleMoves(){

        return _possibleMoves.iterator(); //Fix so we get the possibleMoves for a pawn. Probably check get the current pos, get posX+1 and so on.
    }

}