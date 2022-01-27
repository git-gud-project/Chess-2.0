package model;

import java.awt.*;

public class ChessModel {

    private static final int GAMESIZE = 8;

    private Team _teamWhite, _teamBlack;
    private Board _board;

    public ChessModel() {
        _teamWhite = new Team(Color.WHITE, "w", "Player 1", 60 * 5, -1, 0);
        _teamBlack = new Team(Color.BLACK, "b", "Player 2", 60 * 5, 1, 0);
        _board = new Board(this, GAMESIZE);
    }

    public Team getTeamWhite() { return this._teamWhite; }

    public Team getTeamBlack() { return this._teamBlack; }

    public Board getBoard() { return this._board; }

    public Team getOtherTeam(Team team) {
        if (team == _teamWhite) return _teamBlack;
        return _teamWhite;
    }
    
    public boolean isEnPassant(int row, int col) {
        return _teamWhite.isEnPassant(row, col) || _teamBlack.isEnPassant(row, col);
    }

    public void clearEnPassant() {
        _teamWhite.clearEnPassant();
        _teamBlack.clearEnPassant();
    }
}