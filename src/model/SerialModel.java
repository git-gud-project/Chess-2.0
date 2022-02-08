package model;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class SerialModel implements Serializable {

    private ArrayList<SerialMoveNotation> _serialMoveList;
    private String _FEN;
    private String _whiteName, _blackName;
    private int _fullMoves, _halfMoves;
    private int _wMinutes, _wSeconds, _wMseconds;
    private int _bMinutes, _bSeconds, _bMseconds;

    public SerialModel(ChessModel model) {
        this._FEN = model.toFEN();
        this._whiteName = model.getTeamWhite().getName();
        this._blackName = model.getTeamBlack().getName();
        this._fullMoves = model.getFullMoves();
        this._halfMoves = model.getHalfMoves();
        this._wMinutes = model.getTeamWhite().getTime().getMinutes();
        this._wSeconds = model.getTeamWhite().getTime().getSeconds();
        this._wMseconds = model.getTeamWhite().getTime().getMseconds();
        this._bMinutes = model.getTeamBlack().getTime().getMinutes();
        this._bSeconds = model.getTeamBlack().getTime().getSeconds();
        this._bMseconds = model.getTeamBlack().getTime().getMseconds();
        makeList(model);
    }

    public void makeList(ChessModel model) {
        _serialMoveList = new ArrayList<>();
        ArrayList<Move> modelList = model.getMoveList();
        for (Move moveNotation : modelList) {
            _serialMoveList.add(new SerialMoveNotation(moveNotation));
        }
    }

    // Getters

    public ArrayList<SerialMoveNotation> getSerialMoveList() { return this._serialMoveList; }
    public String getFEN(){ return this._FEN; }
    public String getWhiteName() { return this._whiteName; }
    public String getBlackName() { return this._blackName; }
    public int getFullMoves() { return this._fullMoves; }
    public int getHalfMoves() { return this._halfMoves; }
    public int getWhiteMinutes() { return this._wMinutes; }
    public int getWhiteSeconds() { return this._wSeconds; }
    public int getWhiteMseconds() { return this._wMseconds; }
    public int getBlackMinutes() { return this._bMinutes; }
    public int getBlackSeconds() { return this._bSeconds; }
    public int getBlackMseconds() { return this._bMseconds; }

}
