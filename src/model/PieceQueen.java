package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PieceQueen implements PieceBehavior {
    private ArrayList<Move> possibleMoves = new ArrayList<>();

    @Override
    public Iterator<Move> getPossibleMoves(Cell cell) {
        possibleMoves.clear();

        Board board = cell.getBoard();

        board.calculateMoves(cell, possibleMoves, 1, 1);
        board.calculateMoves(cell, possibleMoves, 1, -1);
        board.calculateMoves(cell, possibleMoves, -1, 1);
        board.calculateMoves(cell, possibleMoves, -1, -1);
        board.calculateMoves(cell, possibleMoves, 1, 0);
        board.calculateMoves(cell, possibleMoves, -1, 0);
        board.calculateMoves(cell, possibleMoves, 0, 1);
        board.calculateMoves(cell, possibleMoves, 0, -1);

        return possibleMoves.iterator();
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.QUEEN;
    }
}
