package com.chess.model;

import java.util.Iterator;

public interface MovesCalculator {
    public Iterator<Move> getPossibleMoves(Rule rule, Position position);
}
