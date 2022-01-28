package control;

import network.Message;

public class MovePieceMessage extends Message {
    private int _fromRow, _fromCol, _toRow, _toCol;
    private boolean _isElimination;

    public MovePieceMessage(int fromRow, int fromCol, int toRow, int toCol, boolean isElimination) {
        _fromRow = fromRow;
        _fromCol = fromCol;
        _toRow = toRow;
        _toCol = toCol;
        _isElimination = isElimination;
    }

    public int getFromRow() {
        return _fromRow;
    }

    public int getFromCol() {
        return _fromCol;
    }

    public int getToRow() {
        return _toRow;
    }

    public int getToCol() {
        return _toCol;
    }

    public boolean isElimination() {
        return _isElimination;
    }
}
