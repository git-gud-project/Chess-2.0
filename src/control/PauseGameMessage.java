package control;

import network.Message;

public class PauseGameMessage extends Message {
    private boolean paused;

    public PauseGameMessage(boolean paused) {
        this.paused = paused;
    }

    public boolean isPaused() {
        return paused;
    }
}
