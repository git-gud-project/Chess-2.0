package com.chess;

import com.chess.model.Position;
import com.chess.model.chess.ChessModel;
import com.chess.model.chess.ChessTeamIdentifier;

import org.junit.jupiter.api.*;

public class Test_Model_Check {
    private ChessModel model;
    
    @BeforeEach
    public void setUp() {
        model = new ChessModel();
    }
    
    /**
     * Test method for {@link com.chess.model.chess.ChessModel#loadFEN(java.lang.String)}. and {@link com.chess.model.chess.ChessModel#toFEN()}.
     */
    @Test
    public void testChec() {
        // One move away from check
        model.loadFEN("rnbqkbnr/1ppppppp/8/7Q/p1B1P3/8/PPPP1PPP/RNB1K1NR w KQkq - 0 4");

        // Move h5 to f7
        model.takeTurn(new Position("h5"), new Position("f7"));

        Assertions.assertTrue(model.isCheck(ChessTeamIdentifier.BLACK));
    }
}
