package com.chess.network;

/**
 * Delegate for a callback with a client and a message parameter.
 */
public interface MessageDelegate {
    /**
     * Invoke the callback with the given client and message parameter.
     * 
     * @param client The client to pass to the callback.
     * @param message The message to pass to the callback.
     */
    public void onMessage(Client client, Message message);
}
