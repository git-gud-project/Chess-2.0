package com.chess.model;

public class ChessBoardInformation implements BoardInformation {

    private final Board board;

    public ChessBoardInformation(Board board) {
        this.board = board;
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
    public Identifier getTypeIdentifier(Position position) {
        int row = position.getRow();
        int col = position.getCol();
        return this.board.getCell(row,col).getPiece().getTypeIdentifier();
    }

    @Override
    public int getBoardSize() {
        return this.board.getRows();
    }
}
