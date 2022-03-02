package com.chess.model.chess;

import com.chess.model.SkinInfo;

import java.io.Serializable;

/**
 * A class used for representing the selected skin for a given piece belonging to a certain team.
 */
public class ChessSkinInfo implements SkinInfo, Serializable {

    /**
     * The path to the .png image file that represents the skin the given team and piece type combination uses.
     */
    private String skinPath;

    /**
     * The index used to represent which skin has been chosen among the pre-existing ones.
     */
    private int skinIndex;

    /**
     * Keeps track of whether an own file has been selected by the user to use as a skin, or if a pre-existing file is being used.
     */
    private boolean ownSkin;

    /**
     * Constructor for ChessSkinInfo.
     * @param pieceType The identifier of the piece type the skin information belongs to.
     * @param teamColor The identifier of the team the skin information belongs to.
     */
    public ChessSkinInfo(ChessTypeIdentifier pieceType, ChessTeamIdentifier teamColor) {
        this.skinPath = pieceType.toString() + teamColor.toString() + ".png";
        this.skinIndex = 0;
        this.ownSkin = false;
    }

    // Getters

    /**
     * Gets the current path to the .png image file used as skin for the piece.
     * @return The current path to the .png image file used as skin for the piece.
     */
    public String getSkinPath() { return this.skinPath; }

    /**
     * Gets the current index representing which of the pre-existing skins is currently in use.
     * @return The current index representing which of the pre-existing skins is currently in use.
     */
    public int getSkinIndex() { return this.skinIndex; }

    /**
     * Determines if the user is currently using a custom skin for the given piece, or a pre-existing one.
     * @return True if a custom skin is being used, false otherwise.
     */
    public boolean getOwnSkin() { return this.ownSkin; }

    // Setters

    /**
     * Sets the path the .png file used as skin for the given piece.
     * @param newPath New path to an image file which will be selected as a new skin for the given piece.
     */
    public void setSkinPath(String newPath) { this.skinPath = newPath; }

    /**
     * Sets the index representing which of the pre-existing skins is currently being used for the given piece.
     * @param newIndex The new index representing which of the pre-existing skins is currently being used for the given piece.
     */
    public void setSkinIndex(int newIndex) { this.skinIndex = newIndex; }

    /**
     * Sets the value of own skin.
     * @param newOwn The new value of own skin.
     */
    public void setOwnSkin(boolean newOwn) { this.ownSkin = newOwn; }

    // Modifiers

    /**
     * Increments the value of skin index by one.
     */
    public void incSkinIndex() { this.skinIndex++; }

    /**
     * Decrements the value of skin index by one.
     */
    public void decSkinIndex() { this.skinIndex--; }

}
