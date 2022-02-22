package com.chess.model;

import com.chess.model.chess.ChessPieceFactory;

public class ChessBoardInformation implements BoardInformation {

    private final Board board;

    public ChessBoardInformation(Board board, ChessPieceFactory pieceFactory) {
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
    public Identifier getTeamIdentifier(int row, int col) {
        return this.board.getCell(row, col).getPiece().getTeamIdentifier();
    }

    @Override
    public Identifier getTypeIdentifier(Position position) {
        int row = position.getRow();
        int col = position.getCol();
        return this.board.getCell(row,col).getPiece().getTypeIdentifier();
    }

    @Override
    public Identifier getTypeIdentifier(int row, int col) {
        return this.board.getCell(row,col).getPiece().getTypeIdentifier();
    }

    @Override
    public int getBoardSize() {
        return this.board.getRows();
    }

    @Override
    public boolean isValid(Position position) {
        int row = position.getRow();
        int col = position.getCol();
        return row>=0 && row <= getBoardSize() && col >= 0 && col <= getBoardSize();
    }

    @Override
    //todo add boolean parameter to signal that it is a fake move
    public void setPiece(Position position, Identifier piece, Identifier team) {
        Cell cell = board.getCell(position);

        cell.updatePiece(ChessPieceFactory.createPiece(piece, team));
    }

    @Override
    //todo add boolean parameter to signal that it is a fake move
    public void clearPiece(Position position) {
        Cell cell = board.getCell(position);
        cell.emptyCell();
    }
}
