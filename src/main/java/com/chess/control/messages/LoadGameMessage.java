package com.chess.control.messages;

import com.chess.model.chess.SerialModel;
import com.chess.network.Message;

public class LoadGameMessage implements Message {
    private SerialModel model;

    public LoadGameMessage(SerialModel model) {
        this.model = model;
    }

    public SerialModel getModel() {
        return model;
    }
}
