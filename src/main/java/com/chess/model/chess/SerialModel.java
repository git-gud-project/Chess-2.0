package com.chess.model.chess;

import java.io.Serializable;
import java.util.*;

import com.chess.model.Identifier;
import com.chess.model.Time;

public class SerialModel implements Serializable {
    private String fen;
    private List<String> moveList;
    private HashMap<Identifier, String> skinMapWhite, skinMapBlack;
    private int[] skinIndexWhite, skinIndexBlack;
    private boolean[] ownSkinWhite, ownSkinBlack;
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

    public HashMap<Identifier, String> getWhiteSkinMap() { return this.skinMapWhite; }

    public HashMap<Identifier, String> getBlackSkinMap() { return this.skinMapBlack; }

    public boolean[] getOwnSkinWhite() { return this.ownSkinWhite; }

    public boolean[] getOwnSkinBlack() { return this.ownSkinBlack; }

    public int[] getSkinIndexWhite() { return this.skinIndexWhite; }

    public int[] getSkinIndexBlack() { return this.skinIndexBlack; }

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

    public void setSkinMapWhite(HashMap<Identifier, String> skinMapWhite) { this.skinMapWhite = skinMapWhite; }

    public void setSkinMapBlack(HashMap<Identifier, String> skinMapBlack) { this.skinMapBlack = skinMapBlack; }

    public void setOwnSkinWhite(boolean[] ownSkinWhite) { this.ownSkinWhite = ownSkinWhite; }

    public void setOwnSkinBlack(boolean[] ownSkinBlack) { this.ownSkinBlack = ownSkinBlack; }

    public void setSkinIndexWhite(int[] skinIndexWhite) { this.skinIndexWhite = skinIndexWhite; }

    public void setSkinIndexBlack(int[] skinIndexBlack) { this.skinIndexBlack = skinIndexBlack; }
}
