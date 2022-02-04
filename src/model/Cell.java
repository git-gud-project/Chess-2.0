package model;

import utils.Event;

public class Cell {

    private final int row;
    private final int col;

    private Board board;
    private Piece piece;

    //
    // Events
    //

    private Event<Piece> onPieceChangedEvent = new Event<>();

    //
    // Constructors
    //

    public Cell(Board board, int row, int col){
        this.board = board;
        this.row = row;
        this.col = col;
        selectPiece();
    }

    private void selectPiece() {
        switch (row * board.getGameSize() + col) {
            case 8, 9, 10, 11, 12, 13, 14, 15 -> piece = new PiecePawn(this, board.getChessModel().getTeamBlack());
            case 0, 7 -> piece = new PieceRook(this, board.getChessModel().getTeamBlack());
            case 1, 6 -> piece = new PieceKnight(this, board.getChessModel().getTeamBlack());
            case 2, 5 -> piece = new PieceBishop(this, board.getChessModel().getTeamBlack());
            case 3 -> piece = new PieceQueen(this, board.getChessModel().getTeamBlack());
            case 4 -> piece = new PieceKing(this, board.getChessModel().getTeamBlack());
            case 48, 49, 50, 51, 52, 53, 54, 55 -> piece = new PiecePawn(this, board.getChessModel().getTeamWhite());
            case 56, 63 -> piece = new PieceRook(this, board.getChessModel().getTeamWhite());
            case 57, 62 -> piece = new PieceKnight(this, board.getChessModel().getTeamWhite());
            case 58, 61 -> piece = new PieceBishop(this, board.getChessModel().getTeamWhite());
            case 59 -> piece = new PieceQueen(this, board.getChessModel().getTeamWhite());
            case 60 -> piece = new PieceKing(this, board.getChessModel().getTeamWhite());
            default -> piece = null;
        }
    }

    //
    // Getters
    //

    public Board getBoard () { return this.board; }

    public int getRow() {
        return row;
    }

    public int getCol(){
        return col;
    }

    public Piece getPiece() {
        return piece;
    }

    //
    // Getters - Events
    //

    public Event<Piece> getOnPieceChangedEvent() {
        return onPieceChangedEvent;
    }

    //
    // Setters
    //

    public void setPiece(Piece piece) {
        this.piece = piece;
        onPieceChangedEvent.invoke(piece);
    }

    @Override
    public String toString() {
        return board.positionToString(row, col);
    }
}

