package control;

import network.Message;

public class SetTeamMessage extends Message {
    private boolean _isWhite;

    public SetTeamMessage(boolean isWhite) {
        _isWhite = isWhite;
    }

    public boolean isWhite() {
        return _isWhite;
    }
}
