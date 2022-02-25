package com.chess;

import com.chess.model.Position;
import com.chess.model.chess.ChessModel;

import org.junit.jupiter.api.*;

public class Test_Model_Move {
    private ChessModel model;

    @BeforeEach
    public void setUp() {
        model = new ChessModel();
    }
    
    private void loadInitialPosition() {
        /**
         * Load initial FEN
         */
        model.loadFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    @Test
    public void testMovePawn() {
        loadInitialPosition();

        // Move a pawn 2 spaces forward to from e2 to e4
        Assertions.assertDoesNotThrow(() -> model.takeTurn(new Position("e2"), new Position("e4")));
    }
}
