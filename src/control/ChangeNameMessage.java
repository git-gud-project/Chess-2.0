package control;

import network.Message;

public class ChangeNameMessage extends Message {
    public String _name;
    public boolean _isWhite;

    public ChangeNameMessage(String name, boolean isWhite) {
        _name = name;
        _isWhite = isWhite;
    }

    public String getName() {
        return _name;
    }

    public boolean isWhite() {
        return _isWhite;
    }
}
