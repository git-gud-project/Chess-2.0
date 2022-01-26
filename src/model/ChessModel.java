package model;

import java.awt.*;

public class ChessModel {

    private static final int GAMESIZE = 8;

    private Team _teamWhite, _teamBlack;
    private Board _board;

    public ChessModel() {
        _teamWhite = new Team(Color.WHITE, "w", "Player 1", 60 * 5);
        _teamBlack = new Team(Color.BLACK, "b", "Player 2", 60 * 5);
        _board = new Board(this, GAMESIZE);
    }

    public Team getTeamWhite() { return this._teamWhite; }

    public Team getTeamBlack() { return this._teamBlack; }

    public Board getBoard() { return this._board; }

}