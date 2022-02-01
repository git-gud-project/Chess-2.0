package control;

import model.PieceType;
import network.Message;

public class PromotePawnMessage extends Message {
    private int _row;
    private int _col;
    private PieceType _pieceType;

    public PromotePawnMessage(int row, int col, PieceType pieceType) {
        _row = row;
        _col = col;
        _pieceType = pieceType;
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
}
