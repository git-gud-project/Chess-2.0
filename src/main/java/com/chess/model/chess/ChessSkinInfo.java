package com.chess.model.chess;

import com.chess.model.SkinInfo;

import java.io.Serializable;

public class ChessSkinInfo implements SkinInfo, Serializable {

    private final ChessTypeIdentifier pieceType;
    private final ChessTeamIdentifier teamColor;
    private String skinPath;
    private int skinIndex;
    private boolean ownSkin;


    public ChessSkinInfo(ChessTypeIdentifier pieceType, ChessTeamIdentifier teamColor){
        this.pieceType = pieceType;
        this.teamColor = teamColor;
        this.skinPath = pieceType.toString() + teamColor.toString() + ".png";
        this.skinIndex = 0;
        this.ownSkin = false;
    }


    // Getters

    public String getSkinPath() { return this.skinPath; }
    public int getSkinIndex() { return this.skinIndex; }
    public boolean getOwnSkin() { return this.ownSkin; }

    // Setters

    public void setSkinPath(String newPath) { this.skinPath = newPath; }
    public void setSkinIndex(int newIndex) { this.skinIndex = newIndex; }
    public void setOwnSkin(boolean newOwn) { this.ownSkin = newOwn; }

    // Modifiers

    public void incSkinIndex() { this.skinIndex++; }
    public void decSkinIndex() { this.skinIndex--; }

}
