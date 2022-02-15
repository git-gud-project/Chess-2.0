package com.chess;

import org.junit.Assert;

import com.chess.model.*;

import org.junit.Test;

import junit.framework.TestCase;

public class ModelTest extends TestCase {
    private ChessModel model;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        model = new ChessModel();
    }
    
    private void loadInitialPosition() {
        /**
         * Load initial FEN
         */
        model.loadFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    /**
     * Test method for {@link com.chess.model.ChessModel#loadFEN(java.lang.String)}. and {@link com.chess.model.ChessModel#toFEN()}.
     */
    @Test
    public void testStartFEN() {
        loadInitialPosition();

        Assert.assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", model.toFEN());
    }
}
