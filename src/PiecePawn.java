import java.util.Iterator;

public class PiecePawn extends Piece{

    public PiecePawn(Cell cell, boolean team) {
        super(cell, team);
    }

    public Iterator<Cell> getPossibleMoves(){
            return null; //Fix so we get the possibleMoves for a pawn. Probably check get the current pos, get posX+1 and so on.
        }

}
