package com.chess.model;

import com.chess.utils.Event;

/**
 * Represents a cell in the board, identified by a position.
 * Can contain a piece. When a piece is changed, it triggers an event.
 * @author Wincent Stålbert Holm
 * @version 2022-03-02
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
     * 
     * @return The position of the cell.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Get the piece behavior on this cell
     * 
     * @return The piece that stands on this cell
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Returns true if this cell is empty, i.e. getPiece() returns null
     * 
     * @return True if this cell is empty
     */
    public boolean isEmpty() {
        return piece == null;
    }

    /**
     * Get the piece changed event
     * 
     * @return The event that triggers when the piece that stands on this cell changes
     */
    public Event<Piece> getOnPieceChangedEvent() {
        return onPieceChangedEvent;
    }

    /**
     * Set which piece that stands on this cell
     * 
     * @param piece The piece to be placed on this cell
     * @param triggerEvent If true, the event that triggers when the piece that stands on this cell changes will be triggered
     */
    public void updatePiece(Piece piece, boolean triggerEvent) {
        this.piece = piece;
        
        if (triggerEvent) {
            onPieceChangedEvent.trigger(piece);
        }
    }

    /**
     * Empty this cell, setting the piece to null
     * 
     * @param triggerEvent If true, the event that triggers when the piece that stands on this cell changes will be triggered
     */
    public void emptyCell(boolean triggerEvent) {
        this.piece = null;

        if (triggerEvent) {
            onPieceChangedEvent.trigger(null);
        }
    }

    /**
     * ToString override
     * 
     * @return The string representation of the cell, the same as the position
     */
    @Override
    public String toString() {
        return position.toString();
    }
}

