package model;

import java.awt.*;

public class ChessModel {

    private static final int GAMESIZE = 8;

    private Team _teamWhite, _teamBlack;
    private Board _board;

    public ChessModel() {
        _teamWhite = new Team(Color.WHITE);
        _teamBlack = new Team(Color.BLACK);
        _board = new Board(this, GAMESIZE);
    }

    public Team getTeamWhite() { return this._teamWhite; }

    public Team getTeamBlack() { return this._teamBlack; }

}