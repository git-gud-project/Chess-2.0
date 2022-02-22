package com.chess.model;

public interface Team {

    /**
     * Gets the identifying name for the team.
     * @return The identifying name for the team.
     */
    public String getName();

    /**
     * Gets the remaining time a team has to play.
     * @return The remaining time a team has to play.
     */
    public Time getTime();
}
