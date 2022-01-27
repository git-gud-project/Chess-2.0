package model;
import java.awt.*;

public class Team {

    private Color _teamColor;
    private String _fileSuffix;
    private String _name;
    private float _time;
    private int _pawnDirectionRow;
    private int _pawnDirectionCol;
    private int _enPassantRow;
    private int _enPassantCol;
    private Piece _enPassantPiece;

    public Team(Color color, String fileSuffix, String name, float time, int pawnDirectionRow, int pawnDirectionCol) {
        _teamColor = color;
        _fileSuffix = fileSuffix;
        _name = name;
        _time = time;
        _pawnDirectionRow = pawnDirectionRow;
        _pawnDirectionCol = pawnDirectionCol;
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

    public float getTime() {
        return _time;
    }

    public int getPawnDirectionRow() {
        return _pawnDirectionRow;
    }

    public int getPawnDirectionCol() {
        return _pawnDirectionCol;
    }

    public boolean isEnPassant(int row, int col) {
        return row == _enPassantRow && col == _enPassantCol;
    }

    public Piece getEnPassantPiece() {
        return _enPassantPiece;
    }

    public void setEnPassant(int row, int col, Piece piece) {
        _enPassantRow = row;
        _enPassantCol = col;
        _enPassantPiece = piece;
    }

    public void clearEnPassant() {
        _enPassantRow = -1;
        _enPassantCol = -1;
        _enPassantPiece = null;
    }
}
