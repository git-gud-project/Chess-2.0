package com.chess.model.pieces;

import java.util.ArrayList;
import java.util.Iterator;

import com.chess.model.Board;
import com.chess.model.Cell;
import com.chess.model.Move;
import com.chess.model.PieceBehavior;
import com.chess.model.PieceType;

public class PieceRook implements PieceBehavior{
    private ArrayList<Move> possibleMoves = new ArrayList<>();

    private boolean hasMoved = false;

    public Iterator<Move> getPossibleMoves(Cell cell) {
        possibleMoves.clear();

        Board board = cell.getBoard();

        board.calculateMoves(cell, possibleMoves, 1, 0);
        board.calculateMoves(cell, possibleMoves, -1, 0);
        board.calculateMoves(cell, possibleMoves, 0, 1);
        board.calculateMoves(cell, possibleMoves, 0, -1);

        return possibleMoves.iterator();
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.ROOK;
    }

    @Override
    public boolean hasMoved() {
        return hasMoved;
    }

    @Override
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}