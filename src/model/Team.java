package model;
import java.awt.*;

public class Team {

    private Color _teamColor;
    private String _fileSuffix;
    private String _name;
    private Time _time;
    private int _pawnDirectionRow;
    private Piece _enPassantPiece;

    public Team(Color color, String fileSuffix, String name, int pawnDirectionRow) {
        _teamColor = color;
        _fileSuffix = fileSuffix;
        _name = name;
        _time = new Time();
        _pawnDirectionRow = pawnDirectionRow;
    }

    public Color getColor() { 
        return _teamColor;
    }

    public String getFileSuffix() {
        return _fileSuffix;
    }

    public String getName() {
        return _name;
    }

    public Time getTime() {
        return _time;
    }

    public int getPawnDirectionRow() {
        return _pawnDirectionRow;
    }

    public int getEnPassantRow() {
        return _enPassantPiece.getCell().getRow() - _pawnDirectionRow;
    }

    public int getEnPassantCol() {
        return _enPassantPiece.getCell().getCol();
    }

    public boolean isEnPassant(int row, int col) {
        return _enPassantPiece != null && getEnPassantRow() == row && getEnPassantCol() == col;
    }

    public Piece getEnPassantPiece() {
        return _enPassantPiece;
    }

    public void setEnPassant(Piece piece) {
        _enPassantPiece = piece;
    }

    public void clearEnPassant() {
        _enPassantPiece = null;
    }
}
