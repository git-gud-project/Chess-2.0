package com.chess.model;

import com.chess.utils.Event;

/**
 * Represents a cell in the board, indentified by a row and a column.
 * 
 * Can contain a piece.
 */
public class Cell {
    /**
     * The position of the cell.
     */
    private final Position position;
    
    /**
     * The piece on the cell.
     */
    private Piece piece;

    /**
     * Event fired when the piece on the cell changes.
     */
    private Event<Piece> onPieceChangedEvent = new Event<>();

    /**
     * Constructor for Cell
     *
     * @param position The position of the cell.
     */
    public Cell(Position position) {
        this.position = position;
    }

    /**
     * Get the position of the cell.
     * @return The position of the cell.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Get the piece behavior on this cell
     * @return The piece that stands on this cell
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Returns true if this cell is empty
     * @return True if this cell is empty
     */
    public boolean isEmpty() {
        return piece == null;
    }

    /**
     * Get the piece change event
     * @return The event that triggers when the piece that stands on this cell changes
     */
    public Event<Piece> getOnPieceChangedEvent() {
        return onPieceChangedEvent;
    }

    /**
     * Set which piece that stands on this cell
     * @param piece The piece to be placed on this cell
     */
    public void updatePieceBehavior(Piece piece, boolean triggerOnPieceChangedEvent) {
        this.piece = piece;
        
        if (triggerOnPieceChangedEvent) {
            onPieceChangedEvent.trigger(piece);
        }
    }

    /**
     * ToString override
     * @return The string representation of the cell
     */
    @Override
    public String toString() {
        return position.toString();
    }
}

