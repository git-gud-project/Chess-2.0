package com.chess.control.messages;

import com.chess.model.Identifier;
import com.chess.network.Message;

public class MovePieceMessage extends Message {
    private final int fromRow, fromCol, toRow, toCol;
    private final boolean isElimination;
    private final Identifier pieceType;

    public MovePieceMessage(int fromRow, int fromCol, int toRow, int toCol, boolean isElimination, Identifier pieceType) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
        this.isElimination = isElimination;
        this.pieceType = pieceType;
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

    public Identifier getPieceType() {
        return pieceType;
    }
}
