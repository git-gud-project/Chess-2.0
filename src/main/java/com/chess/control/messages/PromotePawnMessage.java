package com.chess.control.messages;

import com.chess.model.Identifier;
import com.chess.network.Message;

/**
 * A message sent out if a pawns is promoted.
 */

public class PromotePawnMessage implements Message {
    /**
     * The row in which the promoted piece is located.
     */
    private int row;
    /**
     * The column in which the promoted piece is located.
     */
    private int col;
    /**
     * The identifier for the type of the promoted piece.
     */
    private Identifier pieceType;
    /**
     * A variable identifying whether the promoting move the piece made implied the elimination of another piece or not.
     */
    private boolean isElimination;


    /**
     * The constructor for PromotePawnMessage.
     * @param row The row in which the promoted piece is located.
     * @param col The column in which the promoted piece is located.
     * @param pieceType The identifier for the type of the promoted piece.
     * @param isElimination Identifies whether the promoting move the piece made implied the elimination of another piece or not.
     */
    public PromotePawnMessage(int row, int col, Identifier pieceType, boolean isElimination) {
        this.row = row;
        this.col = col;
        this.pieceType = pieceType;
        this.isElimination = isElimination;
    }

    /**
     * Gets the row the promoted piece is located in.
     * @return The row the promoted piece is located in.
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column in which the promoted piece is located.
     * @return The column in which the promoted piece is located.
     */
    public int getCol() {
        return col;
    }

    /**
     * Gets the identifier for the type of the piece that has been promoted.
     * @return The identifier for the type of the piece that has been promoted.
     */
    public Identifier getPieceType() {
        return pieceType;
    }

    /**
     * Gets the variable identifying whether the promoting move made by the piece implied the elimination of another piece.
     * @return True if the promoting move eliminated another piece, false otherwise.
     */
    public boolean isElimination() {
        return isElimination;
    }
}
