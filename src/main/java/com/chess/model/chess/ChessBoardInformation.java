package com.chess.model.chess;

import java.util.Iterator;

import com.chess.model.Board;
import com.chess.model.Cell;
import com.chess.model.Identifier;
import com.chess.model.Move;
import com.chess.model.MovesCalculator;
import com.chess.model.Position;
import com.chess.model.Rule;

public class ChessBoardInformation implements BoardInformation {

    private final Board board;

    private final TeamManager teamManager;

    public ChessBoardInformation(Board board, TeamManager teamManager) {
        this.board = board;
        this.teamManager = teamManager;
    }

    @Override
    public boolean isEmpty(int row, int col) {
        return this.board.getCell(row, col).getPiece() == null;
    }

    @Override
    public boolean isEmpty(Position position) {
        return this.isEmpty(position.getRow(), position.getCol());
    }

    @Override
    public Identifier getTeamIdentifier(Position position) {
        int row = position.getRow();
        int col = position.getCol();
        return this.board.getCell(row, col).getPiece().getTeamIdentifier();
    }

    @Override
    public Identifier getTeamIdentifier(int row, int col) {
        return this.board.getCell(row, col).getPiece().getTeamIdentifier();
    }

    @Override
    public Identifier getTypeIdentifier(Position position) {
        int row = position.getRow();
        int col = position.getCol();
        return this.board.getCell(row, col).getPiece().getTypeIdentifier();
    }

    @Override
    public Identifier getTypeIdentifier(int row, int col) {
        return this.board.getCell(row, col).getPiece().getTypeIdentifier();
    }

    @Override
    public int getBoardSize() {
        return this.board.getRows();
    }

    @Override
    public boolean isValid(Position position) {
        int row = position.getRow();
        int col = position.getCol();
        return row >= 0 && row <= getBoardSize() && col >= 0 && col <= getBoardSize();
    }

    @Override
    // todo add boolean parameter to signal that it is a fake move
    public void setPiece(Position position, Identifier piece, Identifier team, boolean isFinalMove) {
        Cell cell = board.getCell(position);

        cell.updatePiece(ChessPieceFactory.createPiece(piece, teamManager.getTeam(team)), isFinalMove);
    }

    @Override
    // todo add boolean parameter to signal that it is a fake move
    public void clearPiece(Position position, boolean isFinalMove) {
        Cell cell = board.getCell(position);
        cell.emptyCell(isFinalMove);
    }

    @Override
    public boolean isElimination(Position position, Identifier piece, Identifier team) {
        Identifier otherTeamIdentifier = teamManager.getOtherTeamIdentifier(team);
        ChessTeamParameters otherTeam = teamManager.getTeamParameters(otherTeamIdentifier);

        if (!getTeamIdentifier(position).equals(otherTeamIdentifier)) {
            return false;
        }

        if (piece.equals(PieceType.PAWN)) {
            return otherTeam.getSharedTeamParameters().getEnPassantPosition().equals(position);
        }

        return true;
    }

    @Override
    public MovesCalculator getPossibleMovesIterator(Position position) {
        Cell cell = board.getCell(position);

        return (rule, pos) -> {
            return cell.getPiece().getPossibleMoves(rule, pos);
        };
    }
}
