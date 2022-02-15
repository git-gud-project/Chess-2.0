package com.chess.model;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import com.chess.model.pieces.*;
import com.chess.utils.Event;

public class ChessModel {

    private static final int GAMESIZE = 8;

    //
    // Fields
    //

    private Team teamWhite, teamBlack;

    private Board board;

    private Team currentTeam;

    private boolean paused;

    private boolean started;

    private int fullMoves;

    private int halfMoves;

    private boolean isGameOver;

    private List<String> moveList;

    //
    // Events
    //

    private Event<Team> onTeamChangeEvent = new Event<>();

    private Event<Move> onMoveEvent = new Event<>();

    private Event<String> onGameLoadedEvent = new Event<>();

    private Event<SerialModel> onModelLoadedEvent = new Event<>();

    //
    // Constructors 
    //

    /**
     * Construct a new ChessModel
     */
    public ChessModel() {
        teamWhite = new Team(this, Color.WHITE, "w", "Player 1",  -1);
        teamBlack = new Team(this, Color.BLACK, "b", "Player 2",  1);
        board = new Board(this, GAMESIZE);
        currentTeam = teamWhite;
        paused = true;
        fullMoves = 1;
        isGameOver = false;
        moveList = new ArrayList<>();
    }

    /**
     * Reset the state of this ChessModel to its starting state
     */
    public void resetState() {
        loadFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        teamBlack.getTime().reset();
        teamBlack.getOnTimeChangedEvent().invoke(teamBlack.getTime());
        teamWhite.getTime().reset();
        setPaused(true);
        setStarted(false);
        teamWhite.getOnTimeChangedEvent().invoke(teamWhite.getTime());
        moveList = new ArrayList<>();
        currentTeam = teamWhite;

        onModelLoadedEvent.invoke(new SerialModel(this));
    }

    /**
     * Load another model over this model
     * @param smodel The model to load over the existing model
     */
    public void loadModel(SerialModel smodel){
        teamWhite.setName(smodel.getWhiteName());
        teamBlack.setName(smodel.getBlackName());
        teamWhite.setTime(smodel.getWhiteTime());
        teamBlack.setTime(smodel.getBlackTime());
        moveList = smodel.getMoveList();
        loadFEN(smodel.getFen());
        setPaused(true);
        setStarted(smodel.getStarted());

        onModelLoadedEvent.invoke(smodel);
    }

    /**
     * Get the team class instance which represents the white team
     * @return The white team instance
     */
    public Team getTeamWhite() { return this.teamWhite; }

    /**
     * Get the team class instance which represents the black team
     * @return The black team instance
     */
    public Team getTeamBlack() { return this.teamBlack; }

    /**
     * Get the board for this model
     * @return The board for this model
     */
    public Board getBoard() { return this.board; }

    /**
     * Get which team whose turn it currently is
     * @return The current team
     */
    public Team getCurrentTeam() { return this.currentTeam; }

    /**
     * Get info of whether the game is paused or not
     * @return True if the game is paused
     */
    public boolean getPaused() { return this.paused; }

    /**
     * Get info of whether the game has started or not
     * @return True if the game has started
     */
    public boolean getStarted() { return this.started; }

    /**
     * Get the amount of full moves made this game
     * @return The number of full moves
     */
    public int getFullMoves() { return this.fullMoves; }

    /**
     * Get the amount of half moves made this game
     * @return The number of half moves
     */
    public int getHalfMoves() { return this.halfMoves; }

    /**
     * Get if this game is over
     * @return True if game is over
     */
    public boolean getGameOver(){return this.isGameOver;}

    //
    // Getters - Events
    //

    public Event<Team> getOnTeamChangeEvent() {
        return this.onTeamChangeEvent;
    }

    public Event<Move> getOnMoveEvent() {
        return this.onMoveEvent;
    }

    public Event<String> getOnGameLoadedEvent() {
        return this.onGameLoadedEvent;
    }

    public Event<SerialModel> getOnModelLoadedEvent() {
        return this.onModelLoadedEvent;
    }

    //
    // Setters
    //

    public void setCurrentTeam(Team team) { 
        this.currentTeam = team;
        this.onTeamChangeEvent.invoke(team);
    }

    public void setPaused(boolean paused){
        this.paused = paused;
    }

    public void setStarted(boolean started){
        this.started = started;
    }

    public void setFullMoves(int fullMoves) { this.fullMoves = fullMoves; }

    public void setHalfMoves(int halfMoves) { this.halfMoves = halfMoves; }

    public void setGameOver(){
        this.isGameOver = true;
    }

    //
    // Methods
    //

    public void registerMove(boolean halfMove, Move move) {
        // Increment full moves if it's black's turn
        if (currentTeam == teamBlack) {
            fullMoves++;
        }

        // Half moves are either incremented or reset
        if (halfMove) {
            halfMoves++;
        } else {
            halfMoves = 0;
        }

        // Switch teams
        if (currentTeam == teamWhite) {
            currentTeam = teamBlack;
        } else {
            currentTeam = teamWhite;
        }

        // Invoke events
        onTeamChangeEvent.invoke(currentTeam);

        // Add '#' if move resulted in checkmate on other team
        if(board.isGameOver(currentTeam) == 2){
            move.addCheckMate();
        }
        // Add '+' to notation if move resulted in check to other team
        else if(board.isCheck(currentTeam)) move.addCheck();

        moveList.add(move.toString());

        onMoveEvent.invoke(move);

    }
    
    public Team getOtherTeam(Team team) {
        if (team == teamWhite) return teamBlack;
        return teamWhite;
    }

    public boolean isEnPassant(int row, int col) {
        return teamWhite.isEnPassant(row, col) || teamBlack.isEnPassant(row, col);
    }

    public List<String> getMoveList() {
        return moveList;
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
                if(board.isEmpty(row, col)){
                    emptyCells++;
                }
                else{
                    if(emptyCells > 0){
                        fen += emptyCells;
                        emptyCells = 0;
                    }
                    Piece piece = board.getCell(row, col).getPiece();
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
            fen += " " + board.positionToString(currentTeam.getEnPassantRow(), currentTeam.getEnPassantCol());
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
                        Cell cell = board.getCell(row, col);
                        cell.setPiece(null);
                        col++;
                    }
                }
                else{
                    Piece piece = null;
                    Team team = c == Character.toUpperCase(c) ? getTeamWhite() : getTeamBlack();
                    Cell cell = board.getCell(row, col);
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
                    board.getCell(row, col).setPiece(piece);
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
            Cell cell = board.getCell(parts[3]);
            cell = board.getCell(cell.getRow() + other.getPawnDirectionRow(), cell.getCol());
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
}