package com.chess.model.chess;

import com.chess.model.Identifier;
import com.chess.model.Move;
import com.chess.model.Position;

import java.util.Iterator;
import java.util.List;

public interface Rule {
    public void calculateMoves(
            Position position,
            Identifier teamIdentifier,
            List<Move> registry,
            int directionRow,
            int directionCol,
            int maxSteps,
            boolean skipOwn,
            boolean cantCapture,
            boolean requireCapture
    );
}

