package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PieceKing extends Piece {
    private boolean firstMove = false;
    private ArrayList<Move> _possibleMoves;

    public PieceKing(Cell cell, Team team) {
        super(cell, team,PieceType.KING);
        _possibleMoves = new ArrayList<Move>();
    }

    public Iterator<Move> getPossibleMoves(){
        _possibleMoves.clear();

        Board board = this.getCell().getBoard();
        
        board.calculateMoves(this, _possibleMoves, 1, 1, 1);
        board.calculateMoves(this, _possibleMoves, -1, 1, 1);
        board.calculateMoves(this, _possibleMoves, 1, -1, 1);
        board.calculateMoves(this, _possibleMoves, -1, -1, 1);
        board.calculateMoves(this, _possibleMoves, 1, 0, 1);
        board.calculateMoves(this, _possibleMoves, -1, 0, 1);
        board.calculateMoves(this, _possibleMoves, 0, 1, 1);
        board.calculateMoves(this, _possibleMoves, 0, -1, 1);

        return _possibleMoves.iterator();
    }

    @Override
    public void onMove(Cell oldCell, Cell newCell, boolean state) {
        //TODO: Fix castling.

        if(state){
            firstMove = false;
        }
        else{
            firstMove = true;
        }
    }

    @Override
    public String toString() {
        if(getTeam().getColor().equals(Color.WHITE)) return "WKing";
        return "BKing";
    }

}
