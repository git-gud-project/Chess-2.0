package com.chess.model.chess;

import java.awt.*;
import java.util.HashMap;

import com.chess.model.*;

import com.chess.utils.Event;

/**
 * Represents a team in chess.
 * @author Oscar Marrero Engström
 * @version 2022-03-02
 */
public class ChessTeam implements Team {

    /**
     * The team color.
     */
    private final Color teamColor;

    /**
     * The team name.
     */
    private String name;

    /**
     * The timer for the team.
     */
    private GameTime time;

    /**
     * The team identifier.
     */
    private final Identifier teamIdentifier;

    /**
     * The team parameters.
     */
    private final ChessTeamParameters teamParameters;

    /**
     * The authority of the team.
     */
    private boolean hasAuthority;

    /**
     * Map of the skins for the team.
     */
    private HashMap<Identifier, ChessSkinInfo> skinMap;

    /**
     * Event to be triggered when the team name is changed.
     */
    private Event<String> onNameChangedEvent = new Event<>();

    /**
     * Event to be triggered when the team time is changed.
     */
    private Event<GameTime> onTimeChangedEvent = new Event<>();

    /**
     * Event to be triggered when the team authority is changed.
     */
    private Event<Boolean> onAuthorityChangedEvent = new Event<>();

    /**
     * Create a new chess team.
     * 
     * @param teamIdentifier the team identifier.
     * @param color          the team color.
     * @param name           the team name.
     * @param time           the team time.
     * @param teamParameters the team parameters.
     */
    public ChessTeam(Identifier teamIdentifier, Color color, String name, GameTime time,
            ChessTeamParameters teamParameters) {
        this.teamColor = color;
        this.teamIdentifier = teamIdentifier;
        this.name = name;
        this.time = time.cloneTime();
        this.hasAuthority = true;
        this.teamParameters = teamParameters;

        initHashMap();
    }

    /**
     * Create a new chess team.
     * 
     * @param teamIdentifier the team identifier.
     * @param color          the team color.
     * @param name           the team name.
     * @param time           the team time.
     * @param teamParameters the team parameters.
     * @param skinMap        the team skins.
     */
    public ChessTeam(Identifier teamIdentifier, Color color, String name, GameTime time,
            ChessTeamParameters teamParameters, HashMap<Identifier, ChessSkinInfo> skinMap) {
        this(teamIdentifier, color, name, time, teamParameters);

        this.skinMap = cloneHashMap(skinMap);
    }

    /**
     * Clone the team.
     */
    public ChessTeam clone() {
        return new ChessTeam(teamIdentifier, teamColor, name, time, teamParameters, skinMap);
    }

    /**
     * Get the team color.
     * 
     * @return the team color.
     */
    @Override
    public Color getColor() {
        return teamColor;
    }

    /**
     * Get the team identifier.
     * 
     * @return the team identifier.
     */
    public Identifier getTeamIdentifier() {
        return teamIdentifier;
    }

    /**
     * Get the team name.
     * 
     * @return the team name.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Get the team time.
     * 
     * @return the team time.
     */
    @Override
    public GameTime getTime() {
        return time;
    }

    /**
     * Get the team authority.
     * 
     * @return the team authority.
     */
    @Override
    public boolean getHasAuthority() {
        return hasAuthority;
    }

    /**
     * Get the team parameters.
     * 
     * @return the team parameters.
     */
    public ChessTeamParameters getTeamParameters() {
        return teamParameters;
    }

    /**
     * Set the team name.
     * 
     * @param name the team name.
     */
    public void setName(String name) {
        this.name = name;
        onNameChangedEvent.trigger(name);
    }

    /**
     * Set the team authority.
     * 
     * @param hasAuthority the team authority.
     */
    public void setHasAuthority(boolean hasAuthority) {
        this.hasAuthority = hasAuthority;
        onAuthorityChangedEvent.trigger(hasAuthority);
    }

    /**
     * Set the team time.
     *
     * @param time the team time.
     */
    public void setTime(GameTime time) {
        this.time = time;
        onTimeChangedEvent.trigger(time);
    }

    /**
     * Get the event to be triggered when the team name is changed.
     * 
     * @return the event to be triggered when the team name is changed.
     */
    @Override
    public Event<String> getOnNameChangedEvent() {
        return onNameChangedEvent;
    }

    /**
     * Get the event to be triggered when the team time is changed.
     * 
     * @return the event to be triggered when the team time is changed.
     */
    @Override
    public Event<GameTime> getOnTimeChangedEvent() {
        return onTimeChangedEvent;
    }

    /**
     * Get the event to be triggered when the team authority is changed.
     * 
     * @return the event to be triggered when the team authority is changed.
     */
    @Override
    public Event<Boolean> getOnAuthorityChangedEvent() {
        return onAuthorityChangedEvent;
    }

    /**
     * Tick the game time.
     */
    public void tickTime() {
        time.tick();
        onTimeChangedEvent.trigger(time);
    }

    /**
     * Initiates the hash map containing the skin information for all the pieces for the given team.
     */
    private void initHashMap() {
        this.skinMap = new HashMap<>();
        if (this.teamColor.equals(Color.WHITE)) {
            for (ChessTypeIdentifier orderedName : ChessTypeIdentifier.values()) {
                this.skinMap.put(orderedName, new ChessSkinInfo(orderedName, ChessTeamIdentifier.WHITE));
            }
        } else {
            for (ChessTypeIdentifier orderedName : ChessTypeIdentifier.values()) {
                this.skinMap.put(orderedName, new ChessSkinInfo(orderedName, ChessTeamIdentifier.BLACK));
            }
        }
    }


    /**
     * Gets the path to the current .png file used as skin for the given piece.
     * @param typeIdentifier The identifier for the type of piece.
     * @return A string containing the path to the .png file used as skin for the given piece.
     */
    public String getSkin(Identifier typeIdentifier) {
        return this.skinMap.get(typeIdentifier).getSkinPath();
    }

    /**
     * Sets the path to the .png file used as skin for the given piece.
     * @param typeIdentifier The identifier for the type of piece.
     * @param s A string containing the new path to the .png file to be used as skin for the given piece.
     */
    public void setSkin(Identifier typeIdentifier, String s) {
        this.skinMap.get(typeIdentifier).setSkinPath(s);
    }

    /**
     * Gets the skin index representing which of the current pre-existing skins is being used for the given piece.
     * @param typeIdentifier The identifier for the type of piece.
     * @return The index representing which of the current pre-existing skins is being used for the given piece.
     */
    public int getSkinIndex(Identifier typeIdentifier) {
        return this.skinMap.get(typeIdentifier).getSkinIndex();
    }

    /**
     * Sets the skin representing which of the current pre-existing skins is being used for the given piece.
     * @param typeIdentifier The identifier for the type of piece.
     * @param newValue The new index representing which of the current pre-existing skins is being used for the given piece.
     */
    public void setSkinIndex(Identifier typeIdentifier, int newValue) {
        this.skinMap.get(typeIdentifier).setSkinIndex(newValue);
    }

    /**
     * Determines whether an own file is being used as a skin for the given piece or not.
     * @param typeIdentifier The identifier for the type of piece.
     * @return Ture if a custom skin is being used, false otherwise.
     */
    public boolean getOwnSkin(Identifier typeIdentifier) {
        return this.skinMap.get(typeIdentifier).getOwnSkin();
    }

    /**
     * Sets the value of own skin for the given piece type.
     * @param typeIdentifier The identifier for the type of piece.
     * @param newOwn The new value for own skin.
     */
    public void setOwnSkin(Identifier typeIdentifier, boolean newOwn) {
        this.skinMap.get(typeIdentifier).setOwnSkin(newOwn);
    }

    /**
     * Increases the value of the kin index by one for the provided piece type.
     * @param typeIdentifier The identifier for the type of piece.
     */
    public void incSkinIndex(Identifier typeIdentifier) {
        this.skinMap.get(typeIdentifier).incSkinIndex();
    }

    /**
     * Decreases the value of the skin index by one for the provided piece type.
     * @param typeIdentifier The identifier for the type of piece.
     */
    public void decSkinIndex(Identifier typeIdentifier) {
        this.skinMap.get(typeIdentifier).decSkinIndex();
    }

    /**
     * Used for cloning the hash map storing the skin information of all the pieces for the given team.
     * @param skinMap The skin map to be cloned.
     * @return A new instance of the parameter that has been specified to be cloned.
     */
    private HashMap<Identifier, ChessSkinInfo> cloneHashMap(HashMap<Identifier, ChessSkinInfo> skinMap) {
        HashMap<Identifier, ChessSkinInfo> clone = new HashMap<>();
        for (Identifier p : ChessTypeIdentifier.values()) {
            clone.put(p, skinMap.get(p));
        }
        return clone;
    }

    /**
     * Gets the hash map storing the skin information of all the pieces for the given team.
     * @return The hash map storing the skin information of all the pieces for the given team.
     */
    public HashMap<Identifier, ChessSkinInfo> getSkinMap() {
        return this.skinMap;
    }

    /**
     * Sets the hash map storing the skin information fo all the pieces for the given team.
     * @param skinMap The new hash map storing the skin information fo all the pieces for the given team.
     */
    public void setSkinMap(HashMap<Identifier, ChessSkinInfo> skinMap) {
        this.skinMap = skinMap;
    }

    /**
     * ToString override
     */
    @Override
    public String toString() {
        return name;
    }
}
