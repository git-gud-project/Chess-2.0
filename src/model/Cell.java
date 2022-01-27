package model;

public class Cell {

    private final int _row;
    private final int _col;
    private int _weatherEffect; //Datatype can be changed later.

    private Board _board;
    private Piece _piece;

    public Cell(Board board, int row, int col){
        this._board = board;
        this._row = row;
        this._col = col;
        selectPiece();
    }

    private void selectPiece() {
        switch (_row * _board.getGameSize() + _col) {
            case 8, 9, 10, 11, 12, 13, 14, 15 -> _piece = new PiecePawn(this, _board.getChessModel().getTeamBlack());
            case 0, 7 -> _piece = new PieceRook(this, _board.getChessModel().getTeamBlack());
            case 1, 6 -> _piece = new PieceKnight(this, _board.getChessModel().getTeamBlack());
            case 2, 5 -> _piece = new PieceBishop(this, _board.getChessModel().getTeamBlack());
            case 3 -> _piece = new PieceQueen(this, _board.getChessModel().getTeamBlack());
            case 4 -> _piece = new PieceKing(this, _board.getChessModel().getTeamBlack());
            case 48, 49, 50, 51, 52, 53, 54, 55 -> _piece = new PiecePawn(this, _board.getChessModel().getTeamWhite());
            case 56, 63 -> _piece = new PieceRook(this, _board.getChessModel().getTeamWhite());
            case 57, 62 -> _piece = new PieceKnight(this, _board.getChessModel().getTeamWhite());
            case 58, 61 -> _piece = new PieceBishop(this, _board.getChessModel().getTeamWhite());
            case 59 -> _piece = new PieceQueen(this, _board.getChessModel().getTeamWhite());
            case 60 -> _piece = new PieceKing(this, _board.getChessModel().getTeamWhite());
            default -> _piece = null;
        }
    }

    public Board getBoard () { return this._board; }

    public int getWeatherEffect() {
        return _weatherEffect;
    }

    public int getRow() {
        return _row;
    }

    public int getCol(){
        return _col;
    }

    public void setWeatherEffect(int weatherEffect){
        this._weatherEffect = weatherEffect;
    }

    public Piece getPiece() {
        return _piece;
    }

    public void setPiece(Piece piece) {
        this._piece = piece;
    }

    @Override
    public String toString() {
        return _board.positionToString(_row, _col);
    }
}

