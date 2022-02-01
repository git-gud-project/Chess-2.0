package utils;

import java.util.*;

public class Event<T> {
    private List<Delegate<T>> _delegates;

    public Event() {
        _delegates = new ArrayList<Delegate<T>>();
    }

    public void addDelegate(Delegate<T> delegate) {
        _delegates.add(delegate);
    }

    public void removeDelegate(Delegate<T> delegate) {
        _delegates.remove(delegate);
    }

    public void invoke(T param) {
        for (Delegate<T> delegate : _delegates) {
            delegate.invoke(param);
        }
    }
}
