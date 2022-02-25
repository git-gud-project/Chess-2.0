package com.chess.model;

import java.io.Serializable;

public interface Identifier extends Serializable {
    public boolean compare(Identifier o);
}
