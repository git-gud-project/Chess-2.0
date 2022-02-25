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
     * Get the amount of time in minutes.
     * 
     * @return The amount of time in minutes.
     */
    public int getMinutes() { return minutes; }

    /**
     * Get the amount of time in seconds.
     * 
     * @return The amount of time in seconds.
     */
    public int getSeconds() { return seconds; }

    /**
     * Get the amount of time in milliseconds.
     * 
     * @return The amount of time in milliseconds.
     */
    public int getMilliseconds() { return milliseconds; }

    /**
     * Set the amount of time in minutes.
     * 
     * @param minutes The amount of time in minutes.
     */
    public void setMinutes(int minutes) { this.minutes = minutes; }

    /**
     * Set the amount of time in seconds.
     * 
     * @param seconds The amount of time in seconds.
     */
    public void setSeconds(int seconds) { this.seconds = seconds; }

    /**
     * Set the amount of time in milliseconds.
     * 
     * @param milliseconds The amount of time in milliseconds.
     */
    public void setMilliSeconds(int milliseconds) { this.milliseconds = milliseconds; }

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
    
    public void reset() {
        this.minutes = 5;
        this.seconds = 0;
        this.milliseconds = 0;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%d0", minutes, seconds, milliseconds);
    }

    public GameTime clone() {
        return new GameTime(this.minutes, this.seconds, this.milliseconds);
    }
}
