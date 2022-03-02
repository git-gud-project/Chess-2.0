package com.chess.control.messages;

import com.chess.network.Message;

/**
 * A message sent out whenever the game is paused/resumed.
 * @author Wincent Stålbert Holm
 * @version 2022-03-02
 */

public class PauseGameMessage implements Message {
    /**
     * The variable identifying whether the game is paused or not.
     */
    private boolean paused;

    /**
     * Constructor for PausedGameMessage.
     * @param paused The variable identifying whether the game is paused or not.
     */
    public PauseGameMessage(boolean paused) {
        this.paused = paused;
    }

    /**
     * Gets the variable identifying whether the game is paused or not.
     * @return True if the game is paused, false otherwise.
     */
    public boolean isPaused() {
        return paused;
    }
}
