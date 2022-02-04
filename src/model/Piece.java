package model;

import java.util.Iterator;

/**
 * Base class for all chess pieces.
 */
public abstract class Piece {
    /**
     * The cell that the piece is currently in.
     */
    private Cell cell;

    /**
     * The team that the piece belongs to.
     */
    private final Team team;

    /**
     * The type of the piece.
     */
    private PieceType type;

    /**
     * If this piece has moved from its starting position.
     */
    private boolean hasMoved;

    /**
     * Constructs a new piece.
     * 
     * @param cell the cell that the piece is currently in
     * @param team the team that the piece belongs to
     * @param type the type of the piece
     */
    public Piece(Cell cell, Team team, PieceType type) {
        this.cell = cell;
        this.team = team;
        this.type = type;
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
        return type;
    }

    /**
     * Get if the piece has moved from its starting position.
     * 
     * @return if the piece has moved from its starting position
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * Sets if the piece has moved from its starting position.
     * 
     * @param hasMoved if the piece has moved from its starting position
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    /**
     * Get the path to the image file for the piece.
     * 
     * @return the path to the image file for the piece
     */
    public String getIconPath() {
        return "res/" + type.getFilePrefix() + team.getFileSuffix() + ".png";
    }

    /**
     * Gets all possible moves for this piece.
     * 
     * @return all possible moves for this piece
     */
    public abstract Iterator<Move> getPossibleMoves();

    /**
     * Called when the piece is moved.
     * 
     * @param oldCell the cell that the piece was in before it was moved
     * @param newCell the cell that the piece is now in
     * @param state if this is a fake move
     */
    public void onMove(Cell oldCell, Cell newCell, boolean state) {
        this.hasMoved = true;
    }

    /**
     * Called before the piece is moved.
     * 
     * @param oldCell the cell that the piece was in before it was moved
     * @param newCell the cell that the piece is now in
     */
    public void beforeMove(Cell oldCell, Cell newCell) {
        
    }

    /**
     * Move the piece to a new cell.
     * 
     * @param newCell the cell that the piece is now in
     */
    public void move(Cell newCell) {
        Cell oldCell = this.getCell();

        // Call the beforeMove method
        beforeMove(oldCell, newCell);

        // Move the piece
        cell.setPiece(null);
        cell = newCell;
        cell.setPiece(this);

        // Call the onMove method
        onMove(oldCell, newCell, true);
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
