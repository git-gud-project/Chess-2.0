package com.chess.control;

import com.chess.network.Message;

public class PauseGameMessage extends Message {
    private boolean paused;

    public PauseGameMessage(boolean paused) {
        this.paused = paused;
    }

    public boolean isPaused() {
        return paused;
    }
}
