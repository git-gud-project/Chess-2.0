public abstract class Piece {
    private Cell _pos;
    private boolean _team; //TRUE FOR WHITE, FALSE FOR BLACK.

    public void move(Cell newPos){
        this._pos = newPos;
    }


}
