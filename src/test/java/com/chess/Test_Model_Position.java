package com.chess;

import com.chess.model.*;
import com.chess.model.chess.*;

import org.junit.jupiter.api.*;

public class Test_Model_Position {
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
    public void testCell() {
        loadInitialPosition();

        final Board board = model.getBoard();

        final Position e2 = new Position("e2");

        final Cell cell = board.getCell(e2);

        Assertions.assertEquals(cell.getPosition(), e2);

        final Piece piece = cell.getPiece();

        Assertions.assertNotNull(piece);

        Assertions.assertEquals(piece.getTeamIdentifier(), ChessTeamIdentifier.WHITE);

        Assertions.assertEquals(piece.getTypeIdentifier(), ChessTypeIdentifier.PAWN);
    }
}
