package com.chess.model;

import java.io.Serializable;

public class Time implements Serializable {

    private int minutes, seconds, mseconds;

    public Time() {
        minutes = 5;
        seconds = 0;
        mseconds = 0;
    }

    public Time(int minutes) {
        this.minutes = minutes;
        seconds = 0;
        mseconds = 0;
    }

    public int getMinutes() { return minutes; }
    public int getSeconds() { return seconds; }
    public int getMseconds() { return mseconds; }

    public void tick() {
        if(minutes > 0 || seconds > 0 ||mseconds > 0) {
            mseconds--;
            if (mseconds == -1) {
                mseconds = 9;
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
        this.mseconds = 0;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%d0", minutes, seconds, mseconds);
    }
}
