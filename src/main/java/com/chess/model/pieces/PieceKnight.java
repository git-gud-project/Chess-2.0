package com.chess.model.pieces;

import java.util.ArrayList;
import java.util.Iterator;

import com.chess.model.Board;
import com.chess.model.Cell;
import com.chess.model.Move;
import com.chess.model.PieceBehavior;
import com.chess.model.PieceType;

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
