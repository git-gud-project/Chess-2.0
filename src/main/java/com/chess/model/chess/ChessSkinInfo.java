package com.chess.model.chess;

import com.chess.model.SkinInfo;

import java.io.Serializable;

public class ChessSkinInfo implements SkinInfo, Serializable {

    private ChessIdentifier pieceType, teamColor;
    private String skinPath;
    private int skinIndex;
    private boolean ownSkin;

    private static final String[] whiteNames = {"pw.png", "rw.png", "nw.png", "bw.png", "qw.png", "kw.png"};
    private static final String[] blackNames = {"pb.png", "rb.png", "nb.png", "bb.png", "qb.png", "kb.png"};

    public ChessSkinInfo(ChessIdentifier pieceType, ChessIdentifier teamColor){
        this.pieceType = pieceType;
        this.teamColor = teamColor;
        this.skinPath = choosePath();
        this.skinIndex = chooseIndex();
        this.ownSkin = false;

    }

    // Util for constructor

    private String choosePath(){
        if(teamColor.equals(ChessIdentifier.WHITE)) return whiteNames[chooseIndex()];
        return blackNames[chooseIndex()];
    }

    private int chooseIndex(){
        switch(pieceType){
            case ROOK: return 1;
            case KNIGHT: return 2;
            case BISHOP: return 3;
            case QUEEN: return 4;
            case KING: return 5;
            default: return 0;
        }
    }

    // Getters

    public String getSkinPath() { return this.skinPath; }
    public int getSkinIndex() { return this.skinIndex; }
    public boolean getOwnSkin() { return this.ownSkin; }

    // Setters

    public void setSkinPath(String newPath) { this.skinPath = newPath; }
    public void setSkinIndex(int newIndex) { this.skinIndex = newIndex; }
    public void setOwnSkin(boolean newOwn) { this.ownSkin = newOwn; }

}
