package com.chess.control.messages;

import com.chess.model.chess.SerialModel;
import com.chess.network.Message;

/**
 * A message sent out after the host has loaded a new game.
 * @author Wincent Stålbert Holm
 * @version 2022-03-02
 */

public class LoadGameMessage implements Message {
    /**
     * The serial model used to load a new instance of the game by the host.
     */
    private SerialModel model;

    /**
     * Constructor for load game message.
     * @param model The serial model containing the information about the game to be loaded.
     */
    public LoadGameMessage(SerialModel model) {
        this.model = model;
    }

    /**
     * Gets the instance of serial model containing the new game to be loaded by the host.
     * @return The instance of serial model containing the new game to be loaded by the host.
     */
    public SerialModel getModel() {
        return model;
    }
}
