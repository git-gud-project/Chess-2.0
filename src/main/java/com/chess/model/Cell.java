package com.chess.model;

import com.chess.utils.Event;

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

