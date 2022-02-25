package com.chess.control.messages;

import com.chess.network.Message;

public class PauseGameMessage implements Message {
    private boolean paused;

    public PauseGameMessage(boolean paused) {
        this.paused = paused;
    }

    public boolean isPaused() {
        return paused;
    }
}
