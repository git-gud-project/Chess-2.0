package com.chess.network;

import java.io.Serializable;

/**
 * Base class for all network messages.
 * Implements Serializable so that it can be sent over the network.
 * @author Wincent Stålbert Holm
 * @version 2022-03-02
 */
public interface Message extends Serializable {
    
}
