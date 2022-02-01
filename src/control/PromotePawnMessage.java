package control;

import model.PieceType;
import network.Message;

public class PromotePawnMessage extends Message {
    private int _row;
    private int _col;
    private PieceType _pieceType;
    private boolean _isElimination;

    public PromotePawnMessage(int row, int col, PieceType pieceType, boolean isElimination) {
        _row = row;
        _col = col;
        _pieceType = pieceType;
        _isElimination = isElimination;
    }

    public int getRow() {
        return _row;
    }

    public int getCol() {
        return _col;
    }

    public PieceType getPieceType() {
        return _pieceType;
    }

    public boolean isElimination() {
        return _isElimination;
    }
}
