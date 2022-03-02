package com.chess.utils;

/**
 * Interface for a callback with a single generic parameter.
 * 
 * @param <T> The type of the parameter.
 * @author Wincent Stålbert Holm
 * @version 2022-03-02
 */
public interface Delegate<T> {
    /**
     * Invoke the callback with the given parameter.
     * 
     * @param param The parameter to pass to the callback.
     */
    public void trigger(T param);
}
