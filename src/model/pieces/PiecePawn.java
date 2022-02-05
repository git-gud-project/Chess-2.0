package model.pieces;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import model.Board;
import model.Cell;
import model.Move;
import model.Piece;
import model.PieceBehavior;
import model.PieceType;
import model.Team;

public class PiecePawn implements PieceBehavior {

    private ArrayList<Move> possibleMoves = new ArrayList<>();

    @Override
    public Iterator<Move> getPossibleMoves(Cell cell) {
        possibleMoves.clear();

        Board board = cell.getBoard();

        Piece piece = cell.getPiece();

        Team team = piece.getTeam();

        int dirRow = team.getPawnDirectionRow();

        int row = cell.getRow();
        int col = cell.getCol();
        
        //If the pawn is on the first row, it can move 2 cells.
        if (row == team.getKingRow() + dirRow) {
            board.calculateMoves(cell, possibleMoves, dirRow, 0, 2, false, true, false);
        } else {
            board.calculateMoves(cell, possibleMoves, dirRow, 0, 1, false, true, false);
        }
        
        board.calculateMoves(cell, possibleMoves, dirRow, 1, 1, false, false, true);
        board.calculateMoves(cell, possibleMoves, dirRow, -1, 1, false, false, true);

        // Check if we can capture en passant
        Team otherTeam = board.getChessModel().getOtherTeam(team);

        if (otherTeam.isEnPassant(row + dirRow, col + 1)) {
            Move move = new Move(board.getCell(row + dirRow, col + 1), true);
            move.setIsEnPassant(true);
            possibleMoves.add(move);
        } else if (otherTeam.isEnPassant(row + dirRow, col - 1)) {
            Move move = new Move(board.getCell(row + dirRow, col - 1), true);
            move.setIsEnPassant(true);
            possibleMoves.add(move);
        }

        return possibleMoves.iterator();
    }

    @Override
    public void onMove(Cell oldCell, Cell newCell, boolean state) {
        Board board = newCell.getBoard();

        Piece piece = newCell.getPiece();

        Team team = piece.getTeam();

        Team otherTeam = board.getChessModel().getOtherTeam(team);

        int dirRow = team.getPawnDirectionRow();

        // Check if we moved 2 cells.
        if (oldCell.getRow() == team.getKingRow() + dirRow && Math.abs(oldCell.getRow() - newCell.getRow()) == 2) {
            team.setEnPassant(piece);
        }

        // Check if we did en passant.
        if (otherTeam.isEnPassant(newCell.getRow(), newCell.getCol())) {
            Piece other = otherTeam.getEnPassantPiece();
            other.getCell().setPiece(null);
        }
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }
}
