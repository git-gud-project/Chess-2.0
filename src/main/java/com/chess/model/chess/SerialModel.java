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

    public SerialModel(ChessModel model) {
        fen = model.toFEN();
        moveList = model.getMoveList();
        skinMapWhite = model.getTeamWhite().getSkinMap();
        skinMapBlack = model.getTeamBlack().getSkinMap();
        skinIndexWhite = model.getTeamWhite().getSkinIndex();
        skinIndexBlack = model.getTeamBlack().getSkinIndex();
        ownSkinWhite = model.getTeamWhite().getOwnSkin();
        ownSkinBlack = model.getTeamBlack().getOwnSkin();
        whiteName = model.getTeamWhite().getName();
        blackName = model.getTeamBlack().getName();
        whiteTime = model.getTeamWhite().getTime();
        blackTime = model.getTeamBlack().getTime();
        started = model.getStarted();
    }

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
}
