package com.chess.model;

import java.io.Serializable;

/**
 * An identifier to be used for comparesons.
 * @author Wincent Stålbert Holm
 * @version 2022-03-02
 */
public interface Identifier extends Serializable {
    /**
     * Compare this identifier to another.
     * 
     * @param other The identifier to compare to.
     * @return True if the identifiers are equal, false otherwise.
     */
    public boolean compare(Identifier other);
}
