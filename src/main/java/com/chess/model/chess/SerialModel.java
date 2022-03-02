package com.chess.model.chess;

import java.io.Serializable;
import java.util.*;

import com.chess.model.Identifier;
import com.chess.model.GameTime;

/**
 * A class that represents the serializable parts of the model used to represent the chess game.
 * @author Oscar Marrero Engström
 * @version 2022-03-01
 */
public class SerialModel implements Serializable {
    /**
     * A string representation of the state of the board.
     */
    private String fen;
    /**
     * A list of strings representing the move history of the game.
     */
    private List<String> moveList;
    /**
     * A map linking strings containing the path to a .png file used as a skin for the given piece.
     */
    private HashMap<Identifier, ChessSkinInfo> skinMapWhite, skinMapBlack;
    /**
     * The name of the team playing with the white pieces.
     */
    private String whiteName;
    /**
     * The name of the team playing with the black pieces.
     */
    private String blackName;
    /**
     * The game time remaining for the team playing with the white pieces.
     */
    private GameTime whiteTime;
    /**
     * The game time remaining for the team playing with the black pieces.
     */
    private GameTime blackTime;
    /**
     * Keeps track of whether the game has been started or not.
     */
    private boolean started;

    //
    // Getters
    //

    /**
     * Gets the string representation for the state of the board.
     * @return A string representation for the state of the board.
     */
    public String getFen() {
        return fen;
    }

    /**
     * Gets the list of strings representing the move history of the game.
     * @return A list of strings representing the move history of the game.
     */
    public List<String> getMoveList() {
        return moveList;
    }

    /**
     * Gets the map linking strings containing the path to a .png file representing the skin used for the given piece used by the white team.
     * @return A map linking strings containing the path to a .png file representing the skin used for the given piece used by the white team.
     */
    public HashMap<Identifier, ChessSkinInfo> getWhiteSkinMap() {
        return this.skinMapWhite;
    }

    /**
     * Gets the map linking strings containing the path to a .png file representing the skin used for the given piece used by the black team.
     * @return A map linking strings containing the path to a .png file representing the skin used for the given piece used by the black team.
     */
    public HashMap<Identifier, ChessSkinInfo> getBlackSkinMap() {
        return this.skinMapBlack;
    }

    /**
     * Gets the name of the team playing with the white pieces.
     * @return The name of the team playing with the white pieces.
     */
    public String getWhiteName() {
        return whiteName;
    }

    /**
     * Gets the name of the team playing with the white pieces.
     * @return The name of the team playing with the white pieces.
     */
    public String getBlackName() {
        return blackName;
    }

    /**
     * Gets the remaining time the team playing with the white pieces has left.
     * @return The remaining time the team playing with the white pieces has left.
     */
    public GameTime getWhiteTime() {
        return whiteTime;
    }

    /**
     * Gets the remaining time the team playing with the black pieces has left.
     * @return The remaining time the team playing with the black pieces has left.
     */
    public GameTime getBlackTime() {
        return blackTime;
    }

    /**
     * Determines whether the game has been started yet or not.
     * @return True if the game has been started, false otherwise.
     */
    public boolean getStarted() {
        return started;
    }

    //
    // Setters
    //

    /**
     * Sets the string representation of the current state of the board.
     * @param fen The new string representation of the current state of the board.
     */
    public void setFen(String fen) {
        this.fen = fen;
    }

    /**
     * Sets the move list containing the strings representing of the move history of a given match.
     * @param moveList The new move list with strings representing the move history of a given match.
     */
    public void setMoveList(List<String> moveList) {
        this.moveList = moveList;
    }

    /**
     * Sets the map linking each piece type to a .png file containing the image used as skin for the corresponding piece type for the white team.
     * @param skinMapWhite The new map linking each piece type to a .png file containing the image used as skin for the corresponding piece type for the white team.
     */
    public void setSkinMapWhite(HashMap<Identifier, ChessSkinInfo> skinMapWhite) {
        this.skinMapWhite = skinMapWhite;
    }

    /**
     * Sets the map linking each piece type to a .png file containing the image used as skin for the corresponding piece type for the black team.
     * @param skinMapBlack The map linking each piece type to a .png file containing the image used as skin for the corresponding piece type for the black team.
     */
    public void setSkinMapBlack(HashMap<Identifier, ChessSkinInfo> skinMapBlack) {
        this.skinMapBlack = skinMapBlack;
    }

    /**
     * Sets the name used by the team playing with the white pieces.
     * @param whiteName The new name used by the team playing with the white pieces.
     */
    public void setWhiteName(String whiteName) {
        this.whiteName = whiteName;
    }

    /**
     * Sets the name used by the team playing with the black pieces.
     * @param blackName The new name used by the team playing with the black pieces.
     */
    public void setBlackName(String blackName) {
        this.blackName = blackName;
    }

    /**
     * Sets the remaining time the team playing with the white pieces has left.
     * @param whiteTime The new remaining time the team playing with the white pieces has left.
     */
    public void setWhiteTime(GameTime whiteTime) {
        this.whiteTime = whiteTime;
    }

    /**
     * Sets the remaining time the team playing with the black pieces has left.
     * @param blackTime The new remaining time the team playing with the black pieces has left.
     */
    public void setBlackTime(GameTime blackTime) {
        this.blackTime = blackTime;
    }

    /**
     * Sets the state of the game to be started or not.
     * @param started Should be true for the game to be started, false is interpreted as not being started.
     */
    public void setStarted(boolean started) {
        this.started = started;
    }

}
