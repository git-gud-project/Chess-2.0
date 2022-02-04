package control;

import network.Message;

public class SetTeamMessage extends Message {
    private boolean isWhite;

    public SetTeamMessage(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean isWhite() {
        return isWhite;
    }
}
