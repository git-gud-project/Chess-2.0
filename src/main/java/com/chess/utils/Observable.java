package com.chess.utils;

import java.util.*;

/**
 * Abstract observable which can be listened to.
 */
public abstract class Observable {
    /**
     * List of event listeners.
     */
    private List<Runnable> delegates;

    /**
     * Base constructor.
     */
    public Observable() {
        delegates = new ArrayList<Runnable>();
    }

    /**
     * Add a new event listener.
     * 
     * @param delegate The listener to add.
     */
    public void addDelegate(Runnable delegate) {
        delegates.add(delegate);
    }

    /**
     * Remove an event listener.
     * 
     * @param delegate The listener to remove.
     */
    public void removeDelegate(Runnable delegate) {
        delegates.remove(delegate);
    }

    /**
     * Invoke all listeners.
     */
    public void onChange() {
        for (Runnable delegate : delegates) {
            delegate.run();
        }
    }
}
