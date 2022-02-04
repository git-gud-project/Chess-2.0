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

    /**
     * Convert the state of the game to Forsyth-Edwards Notation.
     * 
     * @return the Forsyth-Edwards Notation
     * @see <a href="http://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation">Forsyth-Edwards Notation</a>
     */
    public String toFEN() {
        String fen = "";
        for(int row = 0; row < GAMESIZE; row++){
            int emptyCells = 0;
            for(int col = 0; col < GAMESIZE; col++){
                if(_board.isEmpty(row, col)){
                    emptyCells++;
                }
                else{
                    if(emptyCells > 0){
                        fen += emptyCells;
                        emptyCells = 0;
                    }
                    Piece piece = _board.getCell(row, col).getPiece();
                    String character = piece.getPieceType().getFilePrefix();
                    if (piece.getTeam() == getTeamWhite()) {
                        character = character.toUpperCase();
                    }
                    fen += character;
                }
            }
            if(emptyCells > 0){
                fen += emptyCells;
            }
            if(row != GAMESIZE - 1){
                fen += "/";
            }
        }
        
        // Add 'w' or 'b' for the side to move
        fen += " " + getCurrentTeam().getFileSuffix();

        // Castling rights
        Team white = getTeamWhite();
        Team black = getTeamBlack();
        boolean whiteKingSide = white.hasCastlingRightKingSide();
        boolean whiteQueenSide = white.hasCastlingRightQueenSide();
        boolean blackKingSide = black.hasCastlingRightKingSide();
        boolean blackQueenSide = black.hasCastlingRightQueenSide();
        fen += " ";
        if (!whiteKingSide && !whiteQueenSide && !blackKingSide && !blackQueenSide) {
            fen += "-";
        } else {
            if (whiteKingSide) {
                fen += "K";
            }
            if (whiteQueenSide) {
                fen += "Q";
            }
            if (blackKingSide) {
                fen += "k";
            }
            if (blackQueenSide) {
                fen += "q";
            }
        }

        // En passant target square
        Team currentTeam = getOtherTeam(getCurrentTeam());

        if (currentTeam.getEnPassantPiece() != null) {
            Piece enPassantPiece = currentTeam.getEnPassantPiece();
            Cell enPassantCell = enPassantPiece.getCell();
            fen += " " + _board.positionToString(enPassantCell.getRow(), enPassantCell.getCol());
        }
        else {
            fen += " -";
        }

        // Half move clock
        fen += " " + getHalfMoves();

        // Full move number
        fen += " " + getFullMoves();

        return fen; 
    }

    /**
     * Load the state of the game from Forsyth-Edwards Notation.
     * 
     * @param fen the Forsyth-Edwards Notation
     * @see <a href="http://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation">Forsyth-Edwards Notation</a>
     */
    public void loadFEN(String fen) {
        String[] parts = fen.split(" ");
        String[] rows = parts[0].split("/");
        int row = 0;
        int col = 0;
        for(String rowString:rows){
            for(int i = 0; i < rowString.length(); i++){
                char c = rowString.charAt(i);
                if(Character.isDigit(c)){
                    for (int j = 0; j < Character.getNumericValue(c); j++) {
                        Cell cell = _board.getCell(row, col);
                        cell.setPiece(null);
                        col++;
                    }
                }
                else{
                    Piece piece = null;
                    Team team = c == Character.toUpperCase(c) ? getTeamWhite() : getTeamBlack();
                    Cell cell = _board.getCell(row, col);
                    switch(Character.toUpperCase(c)) {
                        case 'K':
                            piece = new PieceKing(cell, team);
                            break;
                        case 'Q':
                            piece = new PieceQueen(cell, team);
                            break;
                        case 'R':
                            piece = new PieceRook(cell, team);
                            break;
                        case 'B':
                            piece = new PieceBishop(cell, team);
                            break;
                        case 'N':
                            piece = new PieceKnight(cell, team);
                            break;
                        case 'P':
                            piece = new PiecePawn(cell, team);
                            piece.setHasMoved(cell.getRow() != team.getKingRow() + team.getPawnDirectionRow());
                            break;
                    }
                    _board.getCell(row, col).setPiece(piece);
                    col++;
                }
            }
            row++;
            col = 0;
        }

        Team team = parts[1].equals("w") ? getTeamWhite() : getTeamBlack();
        setCurrentTeam(team);

        // Castling rights
        String castlingRights = parts[2];
        Team white = getTeamWhite();
        Team black = getTeamBlack();
        
        white.setHasCastlingRightKingSide(castlingRights.contains("K"));
        white.setHasCastlingRightQueenSide(castlingRights.contains("Q"));
        black.setHasCastlingRightKingSide(castlingRights.contains("k"));
        black.setHasCastlingRightQueenSide(castlingRights.contains("q"));

        // En passant target square
        if (parts[3].equals("-")) {
            getOtherTeam(getCurrentTeam()).clearEnPassant();
        }
        else {
            Cell cell = _board.getCell(parts[3]);
            getOtherTeam(getCurrentTeam()).setEnPassant(cell.getPiece());
        }

        // Half move clock
        setHalfMoves(Integer.parseInt(parts[4]));

        // Full move number
        setFullMoves(Integer.parseInt(parts[5]));

        /**
         * Invoke events
         */
        getOnGameLoadedEvent().invoke(fen);
    }
}