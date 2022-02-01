package model;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

import utils.Event;

public class ChessModel implements Serializable {

    private static final int GAMESIZE = 8;

    //
    // Fields
    //

    private Team _teamWhite, _teamBlack;

    private Board _board;

    private Team _currentTeam;

    private int _fullMoves;

    private int _halfMoves;

    private ArrayList<MoveNotation> _moveList;

    //
    // Events
    //

    private Event<Team> _onTeamChangeEvent = new Event<>();

    private Event<MoveNotation> _onMoveEvent = new Event<>();

    private Event<String> _onGameLoadedEvent = new Event<>();

    //
    // Constructors 
    //

    public ChessModel() {
        _teamWhite = new Team(this, Color.WHITE, "w", "Player 1",  -1);
        _teamBlack = new Team(this, Color.BLACK, "b", "Player 2",  1);
        _board = new Board(this, GAMESIZE);
        _currentTeam = _teamWhite;
        _fullMoves = 1;
        _moveList = new ArrayList<>();
    }

    //
    // Getters
    //

    public Team getTeamWhite() { return this._teamWhite; }

    public Team getTeamBlack() { return this._teamBlack; }

    public Board getBoard() { return this._board; }

    public Team getCurrentTeam() { return this._currentTeam; }

    public int getFullMoves() { return this._fullMoves; }

    public int getHalfMoves() { return this._halfMoves; }

    //
    // Getters - Events
    //

    public Event<Team> getOnTeamChangeEvent() {
        return this._onTeamChangeEvent;
    }

    public Event<MoveNotation> getOnMoveEvent() {
        return this._onMoveEvent;
    }

    public Event<String> getOnGameLoadedEvent() {
        return this._onGameLoadedEvent;
    }

    //
    // Setters
    //

    public void setCurrentTeam(Team team) { 
        this._currentTeam = team;
        this._onTeamChangeEvent.invoke(team);
    }

    public void setFullMoves(int fullMoves) { this._fullMoves = fullMoves; }

    public void setHalfMoves(int halfMoves) { this._halfMoves = halfMoves; }

    //
    // Methods
    //

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

        // Invoke events
        _onTeamChangeEvent.invoke(_currentTeam);
        _onMoveEvent.invoke(mN);
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

    public Piece createPiece(PieceType type, Team team, Cell cell) {
        switch (type) {
            case PAWN:
                return new PiecePawn(cell, team);
            case ROOK:
                return new PieceRook(cell, team);
            case KNIGHT:
                return new PieceKnight(cell, team);
            case BISHOP:
                return new PieceBishop(cell, team);
            case QUEEN:
                return new PieceQueen(cell, team);
            case KING:
                return new PieceKing(cell, team);
            default:
                throw new IllegalArgumentException("Invalid piece type");
        }
    }
}