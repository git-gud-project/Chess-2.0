package com.chess.model;

import com.chess.utils.Event;

import java.awt.*;

/**
 * Represents a team
 */
public interface Team {

    //GETTERS
    /**
     * Gets the color of the team.
     * @return The color of the team.
     */
    public Color getColor();

    /**
     * Gets the name of the team.
     * @return The name of the team.
     */
    public String getName();

    /**
     * Gets the remaining time of the team.
     * @return The remaining time of the team.
     */
    public Time getTime();


    /**
     * Gets if the team has authority.
     * @return True or false based on if the team has authority.
     */
    public boolean getHasAuthority();

    //
    // Getters - Events
    //

    public Event<String> getOnNameChangedEvent();

    public Event<Time> getOnTimeChangedEvent();

    public Event<Boolean> getOnAuthorityChangedEvent();

    //SETTERS

    public void setHasAuthority(boolean hasAuthority);
}
