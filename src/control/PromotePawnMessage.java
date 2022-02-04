package control;

import model.PieceType;
import network.Message;

public class PromotePawnMessage extends Message {
    private int row;
    private int col;
    private PieceType pieceType;
    private boolean isElimination;

    public PromotePawnMessage(int row, int col, PieceType pieceType, boolean isElimination) {
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

    public PieceType getPieceType() {
        return pieceType;
    }

    public boolean isElimination() {
        return isElimination;
    }
}
