package com.chess.utils;

public interface Delegate<T> {
    public void invoke(T param);
}
