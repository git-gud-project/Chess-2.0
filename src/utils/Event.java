package utils;

import java.util.*;

public class Event<T> {
    private List<Delegate<T>> delegates;

    public Event() {
        delegates = new ArrayList<Delegate<T>>();
    }

    public void addDelegate(Delegate<T> delegate) {
        delegates.add(delegate);
    }

    public void removeDelegate(Delegate<T> delegate) {
        delegates.remove(delegate);
    }

    public void invoke(T param) {
        for (Delegate<T> delegate : delegates) {
            delegate.invoke(param);
        }
    }
}
