package model;

import java.util.Iterator;

public interface PieceBehavior {
    public PieceType getPieceType();

    public Iterator<Move> getPossibleMoves(Cell cell);

    public default boolean hasMoved() {
        return true;
    }

    public default void setHasMoved(boolean hasMoved) {
        //
    }

    /**
     * Called when the piece is moved.
     * 
     * @param oldCell the cell that the piece was in before it was moved
     * @param newCell the cell that the piece is now in
     * @param state if this is a fake move
     */
    public default void onMove(Cell oldCell, Cell newCell, boolean state) {
        setHasMoved(true);
    }

    /**
     * Called before the piece is moved.
     * 
     * @param oldCell the cell that the piece was in before it was moved
     * @param newCell the cell that the piece is now in
     */
    public default void beforeMove(Cell oldCell, Cell newCell) {
        //
    }
}
