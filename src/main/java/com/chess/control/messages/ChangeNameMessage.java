package com.chess.control.messages;

import com.chess.network.Message;

/**
 * A massage used to notify that a player has changed their name.
 * @author Wincent Stålbert Holm
 * @version 2022-03-02
 */

public class ChangeNameMessage implements Message {
    /**
     * The name the player has changed to.
     */
    private String name;
    /**
     * Identifies if the player who has changed their name is the one playing with the white pieces or not.
     */
    private boolean isWhite;

    /**
     * Constructor for change name message
     * @param name The name the player is changing to.
     * @param isWhite True if the plays changing their name is the one playing with the white pieces, false otherwise.
     */
    public ChangeNameMessage(String name, boolean isWhite) {
        this.name = name;
        this.isWhite = isWhite;
    }

    /**
     * Gets the name the players is changing theirs to.
     * @return The new name for the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the variable representing whether the player changing their name is the one playing with the white pieces.
     * @return True if the player changing their name is the one playing with the white pieces, false otherwise.
     */
    public boolean isWhite() {
        return isWhite;
    }
}
