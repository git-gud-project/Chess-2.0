package com.chess.model;

import java.util.Iterator;

/**
 * Class for all chess pieces.
 */
public class Piece {

    /**
     * The type of the piece.
     */
    private PieceBehavior behavior;

    /**
     * The cell that the piece is currently in.
     */
    private Cell cell;

    /**
     * The team that the piece belongs to.
     */
    private final Team team;

    /**
     * Constructs a new piece.
     * 
     * @param cell the cell that the piece is currently in
     * @param team the team that the piece belongs to
     * @param type the type of the piece
     */
    public Piece(PieceBehavior behavior, Cell cell, Team team) {
        this.cell = cell;
        this.team = team;
        this.behavior = behavior;
    }

    /**
     * Get the cell that the piece is currently in.
     * 
     * @return the cell that the piece is currently in
     */
    public Cell getCell() {
        return this.cell;
    }

    /**
     * Get the team that the piece belongs to.
     * 
     * @return the team that the piece belongs to
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Get the type of the piece.
     * 
     * @return the type of the piece
     */
    public PieceType getPieceType() {
        return behavior.getPieceType();
    }

    /**
     * Get if the piece has moved from its starting position.
     * 
     * @return if the piece has moved from its starting position
     */
    public boolean hasMoved() {
        return behavior.hasMoved();
    }

    /**
     * Sets if the piece has moved from its starting position.
     * 
     * @param hasMoved if the piece has moved from its starting position
     */
    public void setHasMoved(boolean hasMoved) {
        behavior.setHasMoved(hasMoved);
    }

    /**
     * Get the path to the image file for the piece.
     * 
     * @return the path to the image file for the piece
     */
    public String getIconPath() {
        return "/images/" + getPieceType().getFilePrefix() + team.getFileSuffix() + ".png";
    }

    /**
     * Gets all possible moves for this piece.
     * 
     * @return all possible moves for this piece
     */
    public Iterator<Move> getPossibleMoves() {
        return behavior.getPossibleMoves(cell);
    }

    /**
     * Move the piece to a new cell.
     * 
     * @param newCell the cell that the piece is now in
     */
    public void move(Cell newCell) {
        Cell oldCell = this.getCell();

        // Call the beforeMove method
        behavior.beforeMove(oldCell, newCell);

        // Move the piece
        cell.setPiece(null);
        cell = newCell;
        cell.setPiece(this);

        // Call the onMove method
        behavior.onMove(oldCell, newCell, true);
    }

    /**
     * Performs a fake move.
     * 
     * @param newCell the cell that the piece is now in
     */
    public void fakeMove(Cell newCell) {
        // Move the piece
        cell.setPiece(null);
        cell = newCell;
        cell.setPiece(this);
    }
}
