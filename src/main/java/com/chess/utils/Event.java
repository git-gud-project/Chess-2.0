package com.chess.utils;

import java.util.*;

/**
 * Event which can be listened to.
 * 
 * @param <T> The type of the event parameter.
 */
public class Event<T> {
    /**
     * List of event listeners.
     */
    private List<Delegate<T>> delegates;

    /**
     * Create a new event.
     */
    public Event() {
        delegates = new ArrayList<Delegate<T>>();
    }

    /**
     * Add a new event listener.
     * 
     * @param delegate The listener to add.
     */
    public void addDelegate(Delegate<T> delegate) {
        delegates.add(delegate);
    }

    /**
     * Remove an event listener.
     * 
     * @param delegate The listener to remove.
     */
    public void removeDelegate(Delegate<T> delegate) {
        delegates.remove(delegate);
    }

    /**
     * Invoke all listeners.
     * 
     * @param param The event parameter.
     */
    public void trigger(T param) {
        for (Delegate<T> delegate : delegates) {
            delegate.trigger(param);
        }
    }
}
