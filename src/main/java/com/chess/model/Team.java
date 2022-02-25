package com.chess.model;

import com.chess.utils.Event;

import java.awt.*;

/**
 * Represents a team
 */
public interface Team {

    /**
     * Gets the color of the team.
     * 
     * @return The color of the team.
     */
    public Color getColor();

    /**
     * Gets the name of the team.
     * 
     * @return The name of the team.
     */
    public String getName();

    /**
     * Gets the remaining time of the team.
     * 
     * @return The remaining time of the team.
     */
    public GameTime getTime();

    /**
     * Gets if the team has authority.
     * 
     * @return True or false based on if the team has authority.
     */
    public boolean getHasAuthority();

    /**
     * Gets the event that is triggered when the team's name changes.
     * 
     * @return The event that is triggered when the team's name changes.
     */
    public Event<String> getOnNameChangedEvent();

    /**
     * Gets the event that is triggered when the time of the team changes.
     * 
     * @return The event that is triggered when the time of the team changes.
     */
    public Event<GameTime> getOnTimeChangedEvent();

    /**
     * Gets the event that is triggered when the team's authority changes.
     * 
     * @return The event that is triggered when the team's authority changes.
     */
    public Event<Boolean> getOnAuthorityChangedEvent();
}
