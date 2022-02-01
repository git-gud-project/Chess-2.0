package utils;

import java.util.*;

public abstract class Observable {
    private List<Runnable> _delegates;

    public Observable() {
        _delegates = new ArrayList<Runnable>();
    }

    public void addDelegate(Runnable delegate) {
        _delegates.add(delegate);
    }

    public void removeDelegate(Runnable delegate) {
        _delegates.remove(delegate);
    }

    public void invoke() {
        for (Runnable delegate : _delegates) {
            delegate.run();
        }
    }
}
