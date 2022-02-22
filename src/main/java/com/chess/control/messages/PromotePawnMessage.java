package com.chess.control.messages;

import com.chess.model.Identifier;
import com.chess.model.chess.PieceType;
import com.chess.network.Message;

public class PromotePawnMessage extends Message {
    private int row;
    private int col;
    private Identifier pieceType;
    private boolean isElimination;

    public PromotePawnMessage(int row, int col, Identifier pieceType, boolean isElimination) {
        this.row = row;
        this.col = col;
        this.pieceType = pieceType;
        this.isElimination = isElimination;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Identifier getPieceType() {
        return pieceType;
    }

    public boolean isElimination() {
        return isElimination;
    }
}
