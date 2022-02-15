package com.chess.control.messages;

import com.chess.model.SerialModel;
import com.chess.network.Message;

public class LoadGameMessage extends Message {
    private SerialModel model;

    public LoadGameMessage(SerialModel model) {
        this.model = model;
    }

    public SerialModel getModel() {
        return model;
    }
}
