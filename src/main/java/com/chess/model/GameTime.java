package com.chess.model;

import java.io.Serializable;

/**
 * Represents a game time.
 */
public class GameTime implements Serializable {

    /**
     * The amount of time in minutes.
     */
    private int minutes;

    /**
     * The amount of time in seconds.
     */
    private int seconds;

    /**
     * The amount of time in milliseconds.
     */
    private int milliseconds;

    /**
     * Creates a new time.
     * 
     * @param minutes The amount of time in minutes.
     * @param seconds The amount of time in seconds.
     * @param milliseconds The amount of time in milliseconds.
     */
    public GameTime(int minutes, int seconds, int milliseconds){
        this.minutes = minutes;
        this.seconds = seconds;
        this.milliseconds = milliseconds;
    }

    /**
     * Tick the time forward by one 10 millisecond.
     */
    public void tick() {
        if(minutes > 0 || seconds > 0 ||milliseconds > 0) {
            milliseconds--;
            if (milliseconds == -1) {
                milliseconds = 9;
                seconds--;
            }
            if (seconds == -1) {
                seconds = 59;
                minutes--;
            }
        }
    }

    /**
     * Returns the string representation of the time a player has left to play.
     * @return A string representation of the time a player has left to play.
     */
    @Override
    public String toString() {
        return String.format("%02d:%02d:%d0", minutes, seconds, milliseconds);
    }

    /**
     * Clones this instance of the class GameTime to give a new instance of the class with the same parameters.
     * @return A new instance of the class GameTime with the same parameter values as the callee.
     */
    public GameTime cloneTime() {
        return new GameTime(this.minutes, this.seconds, this.milliseconds);
    }
}
