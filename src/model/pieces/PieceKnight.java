package model.pieces;

import java.util.ArrayList;
import java.util.Iterator;

import model.Board;
import model.Cell;
import model.Move;
import model.PieceBehavior;
import model.PieceType;

public class PieceKnight implements PieceBehavior {
    private ArrayList<Move> possibleMoves = new ArrayList<>();

    @Override
    public Iterator<Move> getPossibleMoves(Cell cell) {
        possibleMoves.clear();

        Board board = cell.getBoard();

        board.calculateMoves(cell, possibleMoves, 1, 2, 1);
        board.calculateMoves(cell, possibleMoves, 1, -2, 1);
        board.calculateMoves(cell, possibleMoves, -1, 2, 1);
        board.calculateMoves(cell, possibleMoves, -1, -2, 1);
        board.calculateMoves(cell, possibleMoves, 2, 1, 1);
        board.calculateMoves(cell, possibleMoves, 2, -1, 1);
        board.calculateMoves(cell, possibleMoves, -2, 1, 1);
        board.calculateMoves(cell, possibleMoves, -2, -1, 1);

        return possibleMoves.iterator();
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KNIGHT;
    }
}
