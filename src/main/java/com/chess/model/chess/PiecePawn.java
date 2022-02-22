package com.chess.model.chess;

import java.util.ArrayList;
import java.util.Iterator;

import com.chess.model.Board;
import com.chess.model.Cell;
import com.chess.model.Move;
import com.chess.model.Piece;
import com.chess.model.PieceBehavior;

/**
 * The class for the Pawn.
 * Is in charge of:
 *  - Adding all possible moves for the Pawn piece and returning it to the game.
 *  - Checking if the pawn can take two moves or only one.
 *  - Checking if the pawn can capture in a diagonal cell.
 *  - Checking if En Passant is achievable.
 */
public class PiecePawn implements PieceBehavior {

    private ArrayList<Move> possibleMoves = new ArrayList<>();

    @Override
    public Iterator<Move> getPossibleMoves(Board board, Cell cell) {
        possibleMoves.clear();

        Piece piece = cell.getPiece();

        ChessTeam team = piece.getTeam();

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
        ChessTeam otherTeam = board.getChessModel().getOtherTeam(team);

        if (otherTeam.isEnPassant(row + dirRow, col + 1)) {
            Move move = new Move(board.getCell(row + dirRow, col + 1), cell, true);
            move.setIsEnPassant(true);
            possibleMoves.add(move);
        } else if (otherTeam.isEnPassant(row + dirRow, col - 1)) {
            Move move = new Move(board.getCell(row + dirRow, col - 1), cell,true);
            move.setIsEnPassant(true);
            possibleMoves.add(move);
        }

        return possibleMoves.iterator();
    }

    /**
     * Used to set if En Passant is achievable or not for this piece's team.
     * @param oldCell the cell that the piece was in before it was moved
     * @param newCell the cell that the piece is now in
     */
    @Override
    public void onMove(Board board, Cell oldCell, Cell newCell) {
        Piece piece = newCell.getPiece();

        ChessTeam team = piece.getTeam();

        ChessTeam otherTeam = board.getChessModel().getOtherTeam(team);

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

    /**
     * Returns the piece type of the current piece.
     * @return PieceType.PAWN
     */
    @Override
    public PieceType getTypeIdentifier() {
        return PieceType.PAWN;
    }
}
