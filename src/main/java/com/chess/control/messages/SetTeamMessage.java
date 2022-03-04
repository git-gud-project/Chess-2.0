package com.chess.control.messages;

import com.chess.network.Message;

/**
 * A message used to broadcast what team has been set for a given player.
 * @author Wincent Stålbert Holm
 * @version 2022-03-02
 */

public class SetTeamMessage implements Message {
    /**
     * Variable identifying whether the team being set is for the one playing with the white pieces or not.
     */
    private boolean isWhite;

    /**
     * Constructor for SetTeamMessage.
     * @param isWhite Identifies whether the team being set is for the one playing with the white pieces or not.
     */
    public SetTeamMessage(boolean isWhite) {
        this.isWhite = isWhite;
    }

    /**
     * Gets the variable identifying whether the team being set is for the one playing with the white pieces or not.
     * @return True if the team being set is the white one, false otherwise.
     */
    public boolean isWhite() {
        return isWhite;
    }
}
