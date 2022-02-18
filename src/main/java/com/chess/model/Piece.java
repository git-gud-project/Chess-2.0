package com.chess.model;

import javax.swing.*;
import java.awt.*;
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
     * @param behavior the PieceBehavior for this new piece
     * @param cell the cell that the piece is currently in
     * @param team the team that the piece belongs to
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
        int n;
        switch(behavior.getPieceType().getFilePrefix()){
            case "r": n = 1; break;
            case "n": n = 2; break;
            case "b": n = 3; break;
            case "q": n = 4; break;
            case "k": n = 5; break;
            default: n = 0;
        }
        if (!team.getOwnSkin(n)) {
            if (team.getSkinIndex(n) == 0) {
                return "/images/" + team.getSkin(behavior.getPieceType());
            } else {
                return "/skins/" + team.getSkin(getPieceType());
            }
        } else {
            //TODO: Behaves a bit mysteriously if a file is deleted or moved in between pop-ups. Could try to fix this some way.
            return team.getSkin(getPieceType());
        }
    }

    /**
     * Gets all possible moves for this piece.
     * 
     * @return all possible moves for this piece
     */
    public Iterator<Move> getPossibleMoves(Board board) {
        return behavior.getPossibleMoves(board, cell);
    }

    /**
     * Move the piece to a new cell.
     * 
     * @param newCell the cell that the piece is moving to
     */
    public void move(Board board, Cell newCell) {
        Cell oldCell = this.getCell();

        // Call the beforeMove method
        behavior.beforeMove(board, oldCell, newCell);

        // Move the piece
        cell.setPiece(null);
        cell = newCell;
        cell.setPiece(this);

        // Call the onMove method
        behavior.onMove(board, oldCell, newCell);
    }

    /**
     * Performs a fake move. Like a regular move but does not trigger events for beforeMove or onMove in piece behavior
     * 
     * @param newCell the cell that the piece is making a fake move to
     */
    public void fakeMove(Cell newCell) {
        // Move the piece
        cell.setPiece(null, false);
        cell = newCell;
        cell.setPiece(this, false);
    }
}
