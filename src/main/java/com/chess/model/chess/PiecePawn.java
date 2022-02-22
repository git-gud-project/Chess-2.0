package com.chess.model.chess;

import java.util.ArrayList;
import java.util.Iterator;

import com.chess.model.Board;
import com.chess.model.Cell;
import com.chess.model.Identifier;
import com.chess.model.Move;
import com.chess.model.Piece;
import com.chess.model.PieceBehavior;
import com.chess.model.Position;
import com.chess.model.Rule;
import com.chess.model.Team;

/**
 * The class for the Pawn.
 * Is in charge of:
 *  - Adding all possible moves for the Pawn piece and returning it to the game.
 *  - Checking if the pawn can take two moves or only one.
 *  - Checking if the pawn can capture in a diagonal cell.
 *  - Checking if En Passant is achievable.
 */
public class PiecePawn implements PieceBehavior {

    private final ArrayList<Move> possibleMoves;

    private final ChessTeamParameters teamParameters;

    public PiecePawn(ChessTeamParameters teamParameters) {
        this.teamParameters = teamParameters;
        this.possibleMoves = new ArrayList<>();
    }

    /**
     * Used to set if En Passant is achievable or not for this piece's team.
     * @param oldCell the cell that the piece was in before it was moved
     * @param newCell the cell that the piece is now in
     */
    @Override
    public void onMove(Board board, Cell oldCell, Cell newCell) {
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
    public Identifier getTypeIdentifier() {
        return PieceType.PAWN.getTypeIdentifier();
    }

    @Override
    public Iterator<Move> getPossibleMoves(Rule rule, Position position, Identifier teamIdentifier) {
        possibleMoves.clear();

        final int dirRow = teamParameters.getPawnDirection();

        final int row = position.getRow();
        final int col = position.getCol();
        
        //If the pawn is on the first row, it can move 2 cells.
        if (row == teamParameters.getKingRow() + dirRow) {
            rule.calculateMoves(position, teamIdentifier, possibleMoves, dirRow, 0, 2, false, true, false);
        } else {
            rule.calculateMoves(position, teamIdentifier, possibleMoves, dirRow, 0, 1, false, true, false);
        }
        
        rule.calculateMoves(position, teamIdentifier, possibleMoves, dirRow, 1, 1, false, false, true);
        rule.calculateMoves(position, teamIdentifier, possibleMoves, dirRow, -1, 1, false, false, true);

        // Check if we can capture en passant
        // TODO
        Team otherTeam = board.getChessModel().getOtherTeam(team);

        if (otherTeam.isEnPassant(row + dirRow, col + 1)) {
            Move move = new Move(new Position(row + dirRow, col + 1), position, getTypeIdentifier(), true);
            move.setIsEnPassant(true);
            possibleMoves.add(move);
        } else if (otherTeam.isEnPassant(row + dirRow, col - 1)) {
            Move move = new Move(new Position(row + dirRow, col - 1), position, getTypeIdentifier(), true);
            move.setIsEnPassant(true);
            possibleMoves.add(move);
        }

        return possibleMoves.iterator();
    }
}
