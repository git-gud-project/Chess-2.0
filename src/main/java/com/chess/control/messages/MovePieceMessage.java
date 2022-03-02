package com.chess.control.messages;

import com.chess.model.Identifier;
import com.chess.network.Message;

/**
 * A message sent whenever a move is made.
 */
public class MovePieceMessage implements Message {
    /**
     * The row from which the move is made.
     */
    private final int fromRow;
    /**
     * The column from which the move is made.
     */
    private final int fromCol;
    /**
     * The row towards which the move is made.
     */
    private final int toRow;
    /**
     * The column towards which the move is made.
     */
    private final int toCol;
    /**
     * Identifies whether the move being made eliminates another piece or not.
     */
    private final boolean isElimination;
    /**
     * The identifier corresponding to the type of the piece being moved.
     */
    private final Identifier pieceType;

    /**
     * The constructor for MovePieceMessage.
     * @param fromRow The row from which the move is made.
     * @param fromCol The column from which the move is made.
     * @param toRow The row to which the move is made.
     * @param toCol The column to which the move is made.
     * @param isElimination Identifies whether the move implies the elimination of another piece.
     * @param pieceType The identifier for the type of the piece performing the move.
     */
    public MovePieceMessage(int fromRow, int fromCol, int toRow, int toCol, boolean isElimination, Identifier pieceType) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
        this.isElimination = isElimination;
        this.pieceType = pieceType;
    }

    /**
     * Gets the row from which the move is made.
     * @return The row from which the move is made.
     */
    public int getFromRow() {
        return fromRow;
    }

    /**
     * Gets the column from which the move is made.
     * @return The column from which the move is made.
     */
    public int getFromCol() {
        return fromCol;
    }

    /**
     * Gets the row towards which the move is made.
     * @return The row towards which the move is made.
     */
    public int getToRow() {
        return toRow;
    }

    /**
     * Gets the column towards which the move is made.
     * @return The column towards which the move is made.
     */
    public int getToCol() {
        return toCol;
    }

    /**
     * Gets the variable identifying whether the move implies the elimination of another piece or not.
     * @return True if the move implies the elimination of another piece, false otherwise.
     */
    public boolean isElimination() {
        return isElimination;
    }

    /**
     * Gets the identifier for the type of the piece that is being moved.
     * @return The identifier for the type of the piece that is being moved.
     */
    public Identifier getPieceType() {
        return pieceType;
    }
}
