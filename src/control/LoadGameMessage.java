package control;

import model.SerialModel;
import network.Message;

public class LoadGameMessage extends Message {
    private SerialModel model;

    public LoadGameMessage(SerialModel model) {
        this.model = model;
    }

    public SerialModel getModel() {
        return model;
    }
}
