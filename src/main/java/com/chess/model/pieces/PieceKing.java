package com.chess.model.pieces;

import java.util.ArrayList;
import java.util.Iterator;

import com.chess.model.Board;
import com.chess.model.Cell;
import com.chess.model.Move;
import com.chess.model.Piece;
import com.chess.model.PieceBehavior;
import com.chess.model.PieceType;
import com.chess.model.Team;

public class PieceKing implements PieceBehavior {
    private ArrayList<Move> possibleMoves = new ArrayList<>();

    private boolean hasMoved = false;

    @Override
    public Iterator<Move> getPossibleMoves(Cell cell) {
        possibleMoves.clear();

        Board board = cell.getBoard();
        
        board.calculateMoves(cell, possibleMoves, 1, 1, 1);
        board.calculateMoves(cell, possibleMoves, -1, 1, 1);
        board.calculateMoves(cell, possibleMoves, 1, -1, 1);
        board.calculateMoves(cell, possibleMoves, -1, -1, 1);
        board.calculateMoves(cell, possibleMoves, 1, 0, 1);
        board.calculateMoves(cell, possibleMoves, -1, 0, 1);
        board.calculateMoves(cell, possibleMoves, 0, 1, 1);
        board.calculateMoves(cell, possibleMoves, 0, -1, 1);

        Piece piece = cell.getPiece();
        Team team = piece.getTeam();
        boolean canCastleKingSide = team.canCastleKingSide();
        boolean canCastleQueenSide = team.canCastleQueenSide();

        if (canCastleKingSide) {
            Move move = new Move(team.getCastlingKingSideCell(), cell);
            move.setIsCastleKingSide(true);
            possibleMoves.add(move);
        }

        if (canCastleQueenSide) {
            Move move = new Move(team.getCastlingQueenSideCell(), cell);
            move.setIsCastleQueenSide(true);
            possibleMoves.add(move);
        }

        return possibleMoves.iterator();
    }

    @Override
    public void beforeMove(Cell oldCell, Cell newCell) {
        Team team = oldCell.getPiece().getTeam();
        Board board = oldCell.getBoard();
        boolean canCastleKingSide = team.canCastleKingSide();
        boolean canCastleQueenSide = team.canCastleQueenSide();

        if (canCastleKingSide && newCell == team.getCastlingKingSideCell()) {
            Cell rookCell = board.getCell(newCell.getRow(), newCell.getCol() + 1);
            Piece rook = rookCell.getPiece();
            rook.move(board.getCell(newCell.getRow(), newCell.getCol() - 1));
        }

        if (canCastleQueenSide && newCell == team.getCastlingQueenSideCell()) {
            Cell rookCell = board.getCell(newCell.getRow(), newCell.getCol() - 2);
            Piece rook = rookCell.getPiece();
            rook.move(board.getCell(newCell.getRow(), newCell.getCol() + 1));
        }
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KING;
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
