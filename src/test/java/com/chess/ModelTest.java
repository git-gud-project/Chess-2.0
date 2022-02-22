package com.chess;

import com.chess.model.*;
import com.chess.model.chess.ChessModel;

import org.junit.jupiter.api.*;
import org.junit.jupiter.*;

public class ModelTest {
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

    /**
     * Test method for {@link com.chess.model.chess.ChessModel#loadFEN(java.lang.String)}. and {@link com.chess.model.chess.ChessModel#toFEN()}.
     */
    @Test
    public void testStartFEN() {
        loadInitialPosition();

        Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", model.toFEN());
    }
}
