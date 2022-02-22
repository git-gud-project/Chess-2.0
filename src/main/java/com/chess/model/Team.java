package com.chess.model;

public interface Team {

    /**
     * Gets the identifying name of the team.
     * @return The identifying name of the team.
     */
    public String getName();

    /**
     * Gets the remaining time the team has to play.
     * @return The remaining time the team has to play.
     */
    public Time getTime();
}
