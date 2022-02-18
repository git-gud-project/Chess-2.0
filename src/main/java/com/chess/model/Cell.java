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

    /**
     * Constructor for Cell
     *
     * @param board The board which this cell will belong to
     * @param row The row that this cell will lay in
     * @param col The column that this cell will lay in
     */
    public Cell(Board board, int row, int col){
        this.board = board;
        this.row = row;
        this.col = col;
    }

    /**
     * Get the board which this cell belongs to
     * @return The board that this cell belongs to
     */
    public Board getBoard () { return this.board; }

    /**
     * Get which row number this cell lays in
     * @return The row number for this cell
     */
    public int getRow() {
        return row;
    }

    /**
     * Get which column number this cell lays in
     * @return The column number for this cell
     */
    public int getCol(){
        return col;
    }

    /**
     * @return The piece that stands on this cell
     */
    public Piece getPiece() {
        return piece;
    }


    /**
     * @return The event that triggers when the piece that stands on this cell changes
     */
    public Event<Piece> getOnPieceChangedEvent() {
        return onPieceChangedEvent;
    }

    /**
     * Set which piece that stands on this cell
     * @param piece The piece to be placed on this cell
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
        onPieceChangedEvent.trigger(piece);
    }

    /**
     * @return The row and column for this cell in a String, formatted to match chess notation
     */
    @Override
    public String toString() {
        return board.positionToString(row, col);
    }
}

