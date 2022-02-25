package com.chess.network;

import java.io.Serializable;

/**
 * Base class for all network messages.
 * 
 * Implements Serializable so that it can be sent over the network.
 */
public interface Message extends Serializable {
    
}
