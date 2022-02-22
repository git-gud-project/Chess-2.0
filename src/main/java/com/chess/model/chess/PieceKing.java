package com.chess.model.chess;

import java.util.ArrayList;
import java.util.Iterator;

import com.chess.model.*;

/**
 * The class for the King.
 * Is in charge of:
 *  - Adding all possible moves for the king piece and returning it to the game.
 *  - Checking if the king can castle to both king's- and queen's side.
 */
public class PieceKing implements PieceBehavior {
    /**
     * The ArrayList containing all possible moves.
     */
    private ArrayList<Move> possibleMoves = new ArrayList<>();
    /**
     * The variable determining if the king has moved or not.
     */
    private boolean hasMoved = false;

    /**
     * Puts all possible moves for this piece to an iterator.
     * @param cell The cell of the current piece.
     * @return An iterator of the array containing all possible moves.
     */
    @Override
    public Iterator<Move> getPossibleMoves(Board board, Position position, Identifier teamIdentifier) {
        possibleMoves.clear();

        board.calculateMoves(position, teamIdentifier, possibleMoves, 1, 1, 1);
        board.calculateMoves(position, teamIdentifier, possibleMoves, -1, 1, 1);
        board.calculateMoves(position, teamIdentifier, possibleMoves, 1, -1, 1);
        board.calculateMoves(position, teamIdentifier, possibleMoves, -1, -1, 1);
        board.calculateMoves(position, teamIdentifier, possibleMoves, 1, 0, 1);
        board.calculateMoves(position, teamIdentifier, possibleMoves, -1, 0, 1);
        board.calculateMoves(position, teamIdentifier, possibleMoves, 0, 1, 1);
        board.calculateMoves(position, teamIdentifier, possibleMoves, 0, -1, 1);

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

    /**
     * Handles the castling of the King to both king's and queen's side.
     * @param oldCell the cell that the piece was in before it was moved
     * @param newCell the cell that the piece is now in
     */
    @Override
    public void beforeMove(Board board, Cell oldCell, Cell newCell) {
        Team team = oldCell.getPiece().getTeam();
        
        boolean canCastleKingSide = team.canCastleKingSide();
        boolean canCastleQueenSide = team.canCastleQueenSide();

        if (canCastleKingSide && newCell == team.getCastlingKingSideCell()) {
            Cell rookCell = board.getCell(newCell.getRow(), newCell.getCol() + 1);
            Piece rook = rookCell.getPiece();
            rook.move(board, board.getCell(newCell.getRow(), newCell.getCol() - 1));
        }

        if (canCastleQueenSide && newCell == team.getCastlingQueenSideCell()) {
            Cell rookCell = board.getCell(newCell.getRow(), newCell.getCol() - 2);
            Piece rook = rookCell.getPiece();
            rook.move(board, board.getCell(newCell.getRow(), newCell.getCol() + 1));
        }
    }

    /**
     * Returns the piece type of the current piece.
     * @return PieceType.KING
     */
    @Override
    public PieceType getTypeIdentifier() {
        return PieceType.KING;
    }

    /**
     * Returns the variable hasMoved containing the information if the piece has moved or not.
     * @return hasMoved
     */
    @Override
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * Sets the variable hasMoved containing the information if the piece has moved or not.
     * @param hasMoved
     */
    @Override
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}
