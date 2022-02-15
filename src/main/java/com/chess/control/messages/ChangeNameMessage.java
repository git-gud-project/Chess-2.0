package com.chess.control.messages;

import com.chess.network.Message;

public class ChangeNameMessage extends Message {
    public String name;
    public boolean isWhite;

    public ChangeNameMessage(String name, boolean isWhite) {
        this.name = name;
        this.isWhite = isWhite;
    }

    public String getName() {
        return name;
    }

    public boolean isWhite() {
        return isWhite;
    }
}
