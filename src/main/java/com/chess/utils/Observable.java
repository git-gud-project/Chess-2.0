package com.chess.utils;

import java.util.*;

public abstract class Observable {
    private List<Runnable> delegates;

    public Observable() {
        delegates = new ArrayList<Runnable>();
    }

    public void addDelegate(Runnable delegate) {
        delegates.add(delegate);
    }

    public void removeDelegate(Runnable delegate) {
        delegates.remove(delegate);
    }

    public void invoke() {
        for (Runnable delegate : delegates) {
            delegate.run();
        }
    }
}
