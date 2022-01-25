package model;

import model.Piece;

import java.util.ArrayList;
import java.util.Iterator;

public class PiecePawn extends Piece {

    private boolean firstMove = false;
    private ArrayList<Move> _possibleMoves;

    public PiecePawn(Cell cell, Team team) {
        super(cell, team);
    }

    //Getter for the iterator containing all possibleMoves.
    public Iterator<Move> getPossibleMoves(){
        return null; //Fix so we get the possibleMoves for a pawn. Probably check get the current pos, get posX+1 and so on.
    }

    /**
     * A private help method that checks if the move contains a possible eliminations of a piece.
     * @param move
     * @return
     */
    private boolean checkEliminate(Move move){
        return false;
    }
}
