package model;

public class Cell {

    private final int _xPos;
    private final int _yPos;
    private int _weatherEffect; //Datatype can be changed later.

    private Board _board;
    private Piece _piece;

    public Cell(Board board, int x, int y){
        this._board = board;
        this._xPos = x;
        this._yPos = y;
        selectPiece();
    }

    private void selectPiece() {
        switch(_xPos*_board.getGameSize() + _yPos){
            case 0, 1, 2, 3, 4, 5, 6, 7: _piece = new PiecePawn(this, _board.getChessModel().getTeamBlack() ); break;
            case 8, 15: _piece = new PieceRook(this, _board.getChessModel().getTeamBlack() ); break;
            case 9, 14: _piece = new PieceKnight(this, _board.getChessModel().getTeamBlack() ); break;
            case 10, 13: _piece = new PieceBishop(this, _board.getChessModel().getTeamBlack() ); break;
            case 11: _piece = new PieceQueen(this, _board.getChessModel().getTeamBlack() ); break;
            case 12: _piece = new PieceKing(this, _board.getChessModel().getTeamBlack() ); break;
            case 56, 57, 58, 59, 60, 61, 62, 63: _piece = new PiecePawn(this, _board.getChessModel().getTeamWhite() ); break;
            case 48, 55: _piece = new PieceRook(this, _board.getChessModel().getTeamWhite() ); break;
            case 49, 54: _piece = new PieceKnight(this, _board.getChessModel().getTeamWhite() ); break;
            case 50, 53: _piece = new PieceBishop(this, _board.getChessModel().getTeamWhite() ); break;
            case 51: _piece = new PieceQueen(this, _board.getChessModel().getTeamWhite() ); break;
            case 52: _piece = new PieceKing(this, _board.getChessModel().getTeamWhite() ); break;
            default: _piece = null; break;
        }
    }

    public Board getBoard () { return this._board; }

    public int getWeatherEffect() {
        return _weatherEffect;
    }

    public int getxPos() {
        return _xPos;
    }

    public int getyPos(){
        return _yPos;
    }

    public void setWeatherEffect(int weatherEffect){
        this._weatherEffect = weatherEffect;
    }

}

