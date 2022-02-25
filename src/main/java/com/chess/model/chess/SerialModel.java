package com.chess.model.chess;

import java.io.Serializable;
import java.util.*;

import com.chess.model.Identifier;
import com.chess.model.Time;

public class SerialModel implements Serializable {
    private String fen;
    private List<String> moveList;
    private HashMap<Identifier, ChessSkinInfo> skinMapWhite, skinMapBlack;
    private String whiteName, blackName;
    private Time whiteTime, blackTime;
    private boolean started;

    //
    // Getters
    //

    public List<String> getMoveList() {
        return moveList;
    }

    public String getFen() {
        return fen;
    }

    public String getWhiteName() {
        return whiteName;
    }
    
    public String getBlackName() {
        return blackName;
    }
    
    public Time getWhiteTime() {
        return whiteTime;
    }
    
    public Time getBlackTime() {
        return blackTime;
    }

    public boolean getStarted() {
        return started;
    }

    public HashMap<Identifier, ChessSkinInfo> getWhiteSkinMap() { return this.skinMapWhite; }

    public HashMap<Identifier, ChessSkinInfo> getBlackSkinMap() { return this.skinMapBlack; }

    //
    // Setters
    //

    public void setFen(String fen) {
        this.fen = fen;
    }

    public void setMoveList(List<String> moveList) {
        this.moveList = moveList;
    }

    public void setWhiteName(String whiteName) {
        this.whiteName = whiteName;
    }

    public void setBlackName(String blackName) {
        this.blackName = blackName;
    }

    public void setWhiteTime(Time whiteTime) {
        this.whiteTime = whiteTime;
    }

    public void setBlackTime(Time blackTime) {
        this.blackTime = blackTime;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void setSkinMapWhite(HashMap<Identifier, ChessSkinInfo> skinMapWhite) { this.skinMapWhite = skinMapWhite; }

    public void setSkinMapBlack(HashMap<Identifier, ChessSkinInfo> skinMapBlack) { this.skinMapBlack = skinMapBlack; }
}
