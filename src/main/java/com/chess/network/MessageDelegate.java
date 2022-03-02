package com.chess.network;

/**
 * Delegate for a callback with a client and a message parameter.
 * @author Wincent Stålbert Holm
 * @version 2022-03-02
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
