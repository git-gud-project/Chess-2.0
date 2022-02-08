package model;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

import model.pieces.*;
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

    private ArrayList<Move> _moveList;

    //
    // Events
    //

    private Event<Team> _onTeamChangeEvent = new Event<>();

    private Event<Move> _onMoveEvent = new Event<>();

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

    public Event<Move> getOnMoveEvent() {
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

    public void resetState() {
        loadFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        _teamBlack.getTime().reset();
        _teamBlack.getOnTimeChangedEvent().invoke(_teamBlack.getTime());
        _teamWhite.getTime().reset();
        this._fullMoves = 1;
        this._halfMoves = 0;
        _moveList = new ArrayList<>();
        _currentTeam = _teamWhite;
    }

    //
    // Methods
    //

    public void registerMove(boolean halfMove, Move move) {
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

        _moveList.add(move);

        // Invoke events
        _onTeamChangeEvent.invoke(_currentTeam);
        _onMoveEvent.invoke(move);
    }
    
    public Team getOtherTeam(Team team) {
        if (team == _teamWhite) return _teamBlack;
        return _teamWhite;
    }

    public boolean isEnPassant(int row, int col) {
        return _teamWhite.isEnPassant(row, col) || _teamBlack.isEnPassant(row, col);
    }

    public ArrayList<Move> getMoveList() {
        return _moveList;
    }

    public Piece createPiece(PieceType type, Team team, Cell cell) {
        switch (type) {
            case PAWN:
                return new Piece(new PiecePawn(), cell, team);
            case ROOK:
                return new Piece(new PieceRook(), cell, team);
            case KNIGHT:
                return new Piece(new PieceKnight(), cell, team);
            case BISHOP:
                return new Piece(new PieceBishop(), cell, team);
            case QUEEN:
                return new Piece(new PieceQueen(), cell, team);
            case KING:
                return new Piece(new PieceKing(), cell, team);
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
            fen += " " + _board.positionToString(currentTeam.getEnPassantRow(), currentTeam.getEnPassantCol());
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
                            piece = new Piece(new PieceKing(), cell, team);
                            break;
                        case 'Q':
                            piece = new Piece(new PieceQueen(), cell, team);
                            break;
                        case 'R':
                            piece = new Piece(new PieceRook(), cell, team);
                            break;
                        case 'B':
                            piece = new Piece(new PieceBishop(), cell, team);
                            break;
                        case 'N':
                            piece = new Piece(new PieceKnight(), cell, team);
                            break;
                        case 'P':
                            piece = new Piece(new PiecePawn(), cell, team);
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
            Team other = getOtherTeam(getCurrentTeam());
            Cell cell = _board.getCell(parts[3]);
            cell = _board.getCell(cell.getRow() + other.getPawnDirectionRow(), cell.getCol());
            other.setEnPassant(cell.getPiece());
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

    public void setupMoveList(SerialModel smodel){
        _moveList = new ArrayList<>();
        /*
        ArrayList<SerialMoveNotation> modelList = smodel.getSerialMoveList();
        for (SerialMoveNotation sMN : modelList) {
            Team team = sMN.getTeam().equals("white") ? new Team(this, Color.WHITE, "w", _teamWhite.getName(), -1) :
                    new Team(this, Color.BLACK, "b", _teamBlack.getName(), 1);
            Piece piece;
            switch (sMN.getPiece()) {
                case "rook": piece = new PieceRook(new Cell(this.getBoard(), sMN.getRow(), sMN.getCol()), team); break;
                case "bishop": piece = new PieceBishop(new Cell(this.getBoard(), sMN.getRow(), sMN.getCol()), team); break;
                case "knight": piece = new PieceKnight(new Cell(this.getBoard(), sMN.getRow(), sMN.getCol()), team); break;
                case "queen": piece = new PieceQueen(new Cell(this.getBoard(), sMN.getRow(), sMN.getCol()), team); break;
                case "king": piece = new PieceKing(new Cell(this.getBoard(), sMN.getRow(), sMN.getCol()), team); break;
                default: piece = new PiecePawn(new Cell(this.getBoard(), sMN.getRow(), sMN.getCol()), team); break;
            }
            MoveNotation mN = new MoveNotation(sMN.getFromCol(), sMN.getRow(), sMN.getCol(), piece, sMN.getEliminates(), this.getBoard());
            _moveList.add(mN);
        }
        */
    }

    public void loadModel(SerialModel smodel){
        _teamWhite.setName(smodel.getWhiteName());
        _teamBlack.setName(smodel.getBlackName());
        _teamWhite.getTime().setTime(smodel.getWhiteMinutes(), smodel.getWhiteSeconds(), smodel.getWhiteMseconds());
        _teamBlack.getTime().setTime(smodel.getBlackMinutes(), smodel.getBlackSeconds(), smodel.getBlackMseconds());
        _fullMoves = smodel.getFullMoves();
        _halfMoves = smodel.getHalfMoves();
        setupMoveList(smodel);
        loadFEN(smodel.getFEN());
    }
}