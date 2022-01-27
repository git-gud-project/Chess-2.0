package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class for the Queen.
 *
 * Variables:
 * - _possibleMoves: Contains all the moves that the pawn can do.
 * - tempBoard: References to the game board.
 */
public class PieceQueen extends Piece {
    private ArrayList<Move> _possibleMoves;

    public PieceQueen(Cell cell, Team team) {
        super(cell, team,PieceType.QUEEN);
    }

    /**
     * Returns the iterator with all possible move for this Queen-piece.
     * @return _possibleMoves.iterator() containing all moves.
     */
    public Iterator<Move> getPossibleMoves(){
        _possibleMoves = new ArrayList<>();

        Board board = this.getCell().getBoard();

        board.calculateMoves(this, _possibleMoves, 1, 1);
        board.calculateMoves(this, _possibleMoves, 1, -1);
        board.calculateMoves(this, _possibleMoves, -1, 1);
        board.calculateMoves(this, _possibleMoves, -1, -1);
        board.calculateMoves(this, _possibleMoves, 1, 0);
        board.calculateMoves(this, _possibleMoves, -1, 0);
        board.calculateMoves(this, _possibleMoves, 0, 1);
        board.calculateMoves(this, _possibleMoves, 0, -1);

        return _possibleMoves.iterator();
    }

    @Override
    public String toString() {
        if(getTeam().getColor().equals(Color.WHITE)) return "WQueen";
        return "BQueen";
    }
}
