package com.chess.network;

public interface MessageDelegate {
    public void invoke(Client client, Message message);
}
