package com.chess.control;

import com.chess.network.Message;

public class MovePieceMessage extends Message {
    private int fromRow, fromCol, toRow, toCol;
    private boolean isElimination;

    public MovePieceMessage(int fromRow, int fromCol, int toRow, int toCol, boolean isElimination) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
        this.isElimination = isElimination;
    }

    public int getFromRow() {
        return fromRow;
    }

    public int getFromCol() {
        return fromCol;
    }

    public int getToRow() {
        return toRow;
    }

    public int getToCol() {
        return toCol;
    }

    public boolean isElimination() {
        return isElimination;
    }
}
