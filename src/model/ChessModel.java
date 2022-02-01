package model;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class ChessModel implements Serializable {

    private static final int GAMESIZE = 8;

    private Team _teamWhite, _teamBlack;

    private Board _board;

    private Team _currentTeam;

    private int _fullMoves;

    private int _halfMoves;

    private ArrayList<MoveNotation> _moveList;

    public ChessModel() {
        _teamWhite = new Team(this, Color.WHITE, "w", "Player 1",  -1);
        _teamBlack = new Team(this, Color.BLACK, "b", "Player 2",  1);
        _board = new Board(this, GAMESIZE);
        _currentTeam = _teamWhite;
        _fullMoves = 1;
        _moveList = new ArrayList<>();
    }

    public Team getTeamWhite() { return this._teamWhite; }

    public Team getTeamBlack() { return this._teamBlack; }

    public Board getBoard() { return this._board; }

    public Team getCurrentTeam() { return this._currentTeam; }

    public int getFullMoves() { return this._fullMoves; }

    public int getHalfMoves() { return this._halfMoves; }
    
    public void setCurrentTeam(Team team) { this._currentTeam = team; }

    public void setFullMoves(int fullMoves) { this._fullMoves = fullMoves; }

    public void setHalfMoves(int halfMoves) { this._halfMoves = halfMoves; }

    public void registerMove(boolean halfMove, MoveNotation mN) {
        // Increment full moves if it's black's turn
        if (_currentTeam == _teamBlack) {
            _fullMoves++;
        }

        // Half moves are either incremented or reset
        if (halfMove) {
            _halfMoves++;
        } else {
            _halfMoves = 0;
        }

        // Switch teams
        if (_currentTeam == _teamWhite) {
            _currentTeam = _teamBlack;
        } else {
            _currentTeam = _teamWhite;
        }

        _moveList.add(mN);

    }
    
    public Team getOtherTeam(Team team) {
        if (team == _teamWhite) return _teamBlack;
        return _teamWhite;
    }

    public boolean isEnPassant(int row, int col) {
        return _teamWhite.isEnPassant(row, col) || _teamBlack.isEnPassant(row, col);
    }

    public ArrayList<MoveNotation> getMoveList() {
        return _moveList;
    }
}