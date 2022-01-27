package model;

import model.Piece;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class for the Knight.
 *
 * Variables:
 * - _possibleMoves: Contains all the moves that the pawn can do.
 * - tempBoard: References to the game board.
 */
public class PieceKnight extends Piece {
    private ArrayList<Move> _possibleMoves;

    public PieceKnight(Cell cell, Team team) {
        super(cell, team,PieceType.KNIGHT);
    }

    /**
     * Returns the iterator with all possible move for this Knight-piece.
     * @return _possibleMoves.iterator() containing all moves.
     */
    public Iterator<Move> getPossibleMoves(){
        _possibleMoves = new ArrayList<>();

        Board board = this.getCell().getBoard();

        board.calculateMoves(this, _possibleMoves, 1, 2, 1);
        board.calculateMoves(this, _possibleMoves, 1, -2, 1);
        board.calculateMoves(this, _possibleMoves, -1, 2, 1);
        board.calculateMoves(this, _possibleMoves, -1, -2, 1);
        board.calculateMoves(this, _possibleMoves, 2, 1, 1);
        board.calculateMoves(this, _possibleMoves, 2, -1, 1);
        board.calculateMoves(this, _possibleMoves, -2, 1, 1);
        board.calculateMoves(this, _possibleMoves, -2, -1, 1);

        return _possibleMoves.iterator(); //Fix so we get the possibleMoves for a pawn. Probably check get the current pos, get posX+1 and so on.
    }

    @Override
    public String toString() {
        if(getTeam().getColor().equals(Color.WHITE)) return "WKnight";
        return "BKnight";
    }

}
