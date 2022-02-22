package com.chess.model.chess;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import com.chess.model.*;
import com.chess.model.chess.*;
import com.chess.utils.Event;

public class ChessModel {

    private static final int GAMESIZE = 8;

    //
    // Fields
    //

    private final ChessBoard board;

    private final ChessBoardInformation boardInformation;

    private final TeamManager teamManager;

    private final ChessRule rule;

    private final SharedChessTeamParameters sharedChessTeamParameters;

    private boolean paused;

    private boolean started;

    private int fullMoves;

    private int halfMoves;

    private boolean isGameOver;

    private List<String> moveList;

    //
    // Events
    //

    private Event<ChessTeam> onTeamChangeEvent = new Event<>();

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
        sharedChessTeamParameters = new SharedChessTeamParameters();

        final ChessTeamParameters whiteParameters = new ChessTeamParameters(
                sharedChessTeamParameters, // shared
                new Identifier("w"), // identifier
                1, // pawnDirection
                0 // kingRow
        );

        // Set castling rights
        whiteParameters.setCanCastleKingside(true);
        whiteParameters.setCanCastleQueenside(true);

        final ChessTeam whiteTeam = new ChessTeam(new Identifier("w"), Color.WHITE, "Player 1", new Time(5),
                whiteParameters);

        final ChessTeamParameters blackParameters = new ChessTeamParameters(
                sharedChessTeamParameters, // shared
                new Identifier("b"), // identifier
                -1, // pawnDirection
                7 // kingRow
        );

        // Set castling rights
        blackParameters.setCanCastleKingside(true);
        blackParameters.setCanCastleQueenside(true);

        final ChessTeam blackTeam = new ChessTeam(new Identifier("b"), Color.BLACK, "Player 2", new Time(5),
                blackParameters);

        // Setup team manager

        teamManager = new TeamManager(whiteTeam, blackTeam);

        // Setup board

        board = new ChessBoard(GAMESIZE);

        // Setup board information

        boardInformation = new ChessBoardInformation(board, teamManager);

        // Setup rule

        rule = new ChessRule(boardInformation);

        // Setup other variables

        paused = true;
        fullMoves = 1;
        isGameOver = false;
        moveList = new ArrayList<>();
    }

    /**
     * Reset the state of this ChessModel to its starting state
     */
    public void resetState(Time time) {
        loadFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        final ChessTeam whiteTeam = teamManager.getTeam(TeamManager.WHITE);

        whiteTeam.setTime(time);

        final ChessTeam blackTeam = teamManager.getTeam(TeamManager.BLACK);

        blackTeam.setTime(time);

        teamManager.setCurrentTeamIdentifier(TeamManager.WHITE);

        setPaused(true);
        setStarted(false);

        moveList = new ArrayList<>();

        onModelLoadedEvent.trigger(new SerialModel(this));
    }

    /**
     * Load another model over this model
     * 
     * @param smodel The model to load over the existing model
     */
    public void loadModel(SerialModel smodel) {
        final ChessTeam whiteTeam = teamManager.getTeam(TeamManager.WHITE);
        final ChessTeam blackTeam = teamManager.getTeam(TeamManager.BLACK);

        whiteTeam.setName(smodel.getWhiteName());
        blackTeam.setName(smodel.getBlackName());

        whiteTeam.setTime(smodel.getWhiteTime());
        blackTeam.setTime(smodel.getBlackTime());

        whiteTeam.setSkinMap(smodel.getWhiteSkinMap());
        blackTeam.setSkinMap(smodel.getBlackSkinMap());

        whiteTeam.setOwnSkin(smodel.getOwnSkinWhite());
        blackTeam.setOwnSkin(smodel.getOwnSkinBlack());

        whiteTeam.setSkinIndex(smodel.getSkinIndexWhite());
        blackTeam.setSkinIndex(smodel.getSkinIndexBlack());

        moveList = smodel.getMoveList();

        loadFEN(smodel.getFen());

        setPaused(true);
        setStarted(smodel.getStarted());

        onModelLoadedEvent.trigger(smodel);
    }

    /**
     * Checks if it is checkmate or stalemate. If it is checkmate it returns 2. If
     * it is stalemate it return 1. Else 0.
     * 
     * @param enemyTeamIdentifier the team of the enemy player
     * @return 0 if it is not checkmate or stalemate, 1 if it is stalemate, 2 if
     */
    public int isGameOver(Identifier enemyTeamIdentifier) {
        if (rule.allTeamMoves(enemyTeamIdentifier).isEmpty() && rule.isCheck(enemyTeamIdentifier)) {
            return 2;
        } else if (rule.allTeamMoves(enemyTeamIdentifier).isEmpty() && !rule.isCheck(enemyTeamIdentifier)) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Check if the king for a team is in check
     * @param teamIdentifier the team identifier
     * @return true if the king is in check
     */
    public boolean isCheck(Identifier teamIdentifier) {
        return rule.isCheck(teamIdentifier);
    }

    /**
     * Get the king cell for a team
     * @param teamIdentifier the team identifier
     * @return the king cell
     */
    public Cell getKingCell(Identifier teamIdentifier) {
        // Loop through all cells
        for (int row = 0; row < GAMESIZE; row++) {
            for (int col = 0; col < GAMESIZE; col++) {
                final Cell cell = board.getCell(row, col);

                if (cell.isEmpty()) {
                    continue;
                }

                final Piece piece = cell.getPiece();

                if (piece.getTeamIdentifier().equals(teamIdentifier) && piece.getTypeIdentifier().equals(PieceType.KING)) {
                    return cell;
                }
            }
        }
        
        throw new IllegalStateException("King not found");
    }

    /**
     * Get the team class instance which represents the white team
     * 
     * @return The white team instance
     */
    public ChessTeam getTeamWhite() {
        return teamManager.getTeam(TeamManager.WHITE);
    }

    /**
     * Get the team class instance which represents the black team
     * 
     * @return The black team instance
     */
    public ChessTeam getTeamBlack() {
        return teamManager.getTeam(TeamManager.BLACK);
    }

    /**
     * Get the board for this model
     * 
     * @return The board for this model
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Get the board information for this model
     * 
     * @return The board information for this model
     */
    public ChessBoardInformation getBoardInformation() {
        return this.boardInformation;
    }

    /**
     * Get the team manager for this model
     * 
     * @return The team manager for this model
     */
    public TeamManager getTeamManager() {
        return this.teamManager;
    }

    /**
     * Get the rule for this model
     * 
     * @return The rule for this model
     */
    public ChessRule getRule() {
        return this.rule;
    }

    /**
     * Get which team whose turn it currently is
     * 
     * @return The current team
     */
    public ChessTeam getCurrentTeam() {
        return teamManager.getTeam(teamManager.getCurrentTeamIdentifier());
    }

    /**
     * Get a team by its identifier
     * @param teamIdentifier The identifier of the team
     * @return The team with the given identifier
     */
    public ChessTeam getTeam(Identifier teamIdentifier) {
        return teamManager.getTeam(teamIdentifier);
    }

    /**
     * Get info of whether the game is paused or not
     * 
     * @return True if the game is paused
     */
    public boolean getPaused() {
        return this.paused;
    }

    /**
     * Get info of whether the game has started or not
     * 
     * @return True if the game has started
     */
    public boolean getStarted() {
        return this.started;
    }

    /**
     * Get the amount of full moves made this game
     * 
     * @return The number of full moves
     */
    public int getFullMoves() {
        return this.fullMoves;
    }

    /**
     * Get the amount of half moves made this game
     * 
     * @return The number of half moves
     */
    public int getHalfMoves() {
        return this.halfMoves;
    }

    /**
     * Get if this game is over
     * 
     * @return True if game is over
     */
    public boolean getGameOver() {
        return this.isGameOver;
    }

    //
    // Getters - Events
    //

    public Event<ChessTeam> getOnTeamChangeEvent() {
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

    public void setCurrentTeam(ChessTeam team) {
        teamManager.setCurrentTeamIdentifier(team.getTeamIdentifier());

        this.onTeamChangeEvent.trigger(team);
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void setFullMoves(int fullMoves) {
        this.fullMoves = fullMoves;
    }

    public void setHalfMoves(int halfMoves) {
        this.halfMoves = halfMoves;
    }

    public void setGameOver() {
        this.isGameOver = true;
    }

    //
    // Methods
    //

    /**
     * @param halfMove
     * @param move
     */
    public void registerMove(boolean halfMove, Move move) {
        // Increment full moves if it's black's turn
        if (teamManager.getCurrentTeamIdentifier().equals(TeamManager.BLACK)) {
            fullMoves++;
        }

        // Half moves are either incremented or reset
        if (halfMove) {
            halfMoves++;
        } else {
            halfMoves = 0;
        }

        // Switch teams
        teamManager.switchCurrentTeam();

        // Invoke events
        onTeamChangeEvent.trigger(getCurrentTeam());

        // Add '#' if move resulted in checkmate on other team
        if (isGameOver(teamManager.getCurrentTeamIdentifier()) == 2) {
            move.addCheckMate();
        }
        // Add '+' to notation if move resulted in check to other team
        else if (rule.isCheck(teamManager.getCurrentTeamIdentifier())) {
            move.addCheck();
        }

        moveList.add(move.toString());

        onMoveEvent.trigger(move);
    }

    /**
     * Move a piece from one position to another
     * 
     * @param from The position the piece is moving from
     * @param to The position the piece is moving to
     */
    public void movePiece(Position from, Position to) {
        // Get the piece to move
        final Cell fromCell = board.getCell(from);
        final Cell toCell = board.getCell(to);
        
        // Get the piece to move
        final Piece piece = fromCell.getPiece();

        // Call the before move event
        piece.beforeMove(rule, from, to);

        // Move the piece
        toCell.updatePiece(piece, true);

        // Remove the piece from the from cell
        fromCell.emptyCell(true);

        // Call the after move event
        piece.afterMove(rule, from, to);
    }

    /**
     * Given a team, this method returns the opposite team
     * 
     * @param team The team you want to find opposite team for
     * @return The opposite team
     */
    public ChessTeam getOtherTeam(ChessTeam team) {
        return teamManager.getTeam(
                teamManager.getOtherTeamIdentifier(team.getTeamIdentifier()));
    }

    /**
     * Given a team identifier, this method returns the opposite team identifier
     * 
     * @param teamIdentifier The team identifier you want to find opposite team for
     * @return The opposite team identifier
     */
    public Identifier getOtherTeamIdentifier(Identifier teamIdentifier) {
        return teamManager.getOtherTeamIdentifier(teamIdentifier);
    }

    /**
     * Clear en passant square
     */
    public void clearEnPassantSquare() {
        sharedChessTeamParameters.setEnPassantPosition(Position.INVALID);
    }

    /**
     * Getter for move list
     * 
     * @return list of moves as strings.
     */
    public List<String> getMoveList() {
        return moveList;
    }

    /**
     * Convert the state of the game to Forsyth-Edwards Notation.
     * 
     * @return the Forsyth-Edwards Notation
     * @see <a href=
     *      "http://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation">Forsyth-Edwards
     *      Notation</a>
     */
    public String toFEN() {
        String fen = "";
        for (int row = 0; row < GAMESIZE; row++) {
            int emptyCells = 0;
            for (int col = 0; col < GAMESIZE; col++) {
                if (boardInformation.isEmpty(row, col)) {
                    emptyCells++;
                } else {
                    if (emptyCells > 0) {
                        fen += emptyCells;
                        emptyCells = 0;
                    }
                    Piece piece = board.getCell(row, col).getPiece();
                    String character = piece.getTypeIdentifier().toString();
                    if (piece.getTeamIdentifier().equals(TeamManager.WHITE)) {
                        character = character.toUpperCase();
                    }
                    fen += character;
                }
            }
            if (emptyCells > 0) {
                fen += emptyCells;
            }
            if (row != GAMESIZE - 1) {
                fen += "/";
            }
        }

        // Add 'w' or 'b' for the side to move
        fen += " " + teamManager.getCurrentTeamIdentifier();

        // Castling rights
        ChessTeam white = getTeamWhite();
        ChessTeam black = getTeamBlack();
        ChessTeamParameters whiteParams = white.getTeamParameters();
        ChessTeamParameters blackParams = black.getTeamParameters();
        boolean whiteKingSide = whiteParams.canCastleKingside();
        boolean whiteQueenSide = whiteParams.canCastleQueenside();
        boolean blackKingSide = blackParams.canCastleKingside();
        boolean blackQueenSide = blackParams.canCastleQueenside();

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
        Position enPassantPosition = sharedChessTeamParameters.getEnPassantPosition();

        if (enPassantPosition.equals(Position.INVALID)) {
            fen += " " + enPassantPosition;
        } else {
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
     * @see <a href=
     *      "http://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation">Forsyth-Edwards
     *      Notation</a>
     */
    public void loadFEN(String fen) {
        String[] parts = fen.split(" ");
        String[] rows = parts[0].split("/");
        int row = 0;
        int col = 0;
        for (String rowString : rows) {
            for (int i = 0; i < rowString.length(); i++) {
                char c = rowString.charAt(i);
                if (Character.isDigit(c)) {
                    for (int j = 0; j < Character.getNumericValue(c); j++) {
                        Cell cell = board.getCell(row, col);
                        cell.emptyCell(true);
                        col++;
                    }
                } else {
                    ChessTeam team = c == Character.toUpperCase(c) ? getTeamWhite() : getTeamBlack();

                    Identifier typeIdentifier = new Identifier(String.valueOf(Character.toLowerCase(c)));

                    Cell cell = board.getCell(row, col);

                    Piece piece = ChessPieceFactory.createPiece(typeIdentifier, team);

                    cell.updatePiece(piece, true);

                    col++;
                }
            }
            row++;
            col = 0;
        }

        ChessTeam team = parts[1].equals("w") ? getTeamWhite() : getTeamBlack();
        setCurrentTeam(team);

        // Castling rights
        String castlingRights = parts[2];
        ChessTeam white = getTeamWhite();
        ChessTeam black = getTeamBlack();
        ChessTeamParameters whiteParams = white.getTeamParameters();
        ChessTeamParameters blackParams = black.getTeamParameters();

        whiteParams.setCanCastleKingside(castlingRights.contains("K"));
        whiteParams.setCanCastleQueenside(castlingRights.contains("Q"));
        blackParams.setCanCastleKingside(castlingRights.contains("k"));
        blackParams.setCanCastleQueenside(castlingRights.contains("q"));

        // En passant target square
        if (parts[3].equals("-")) {
            sharedChessTeamParameters.setEnPassantPosition(Position.INVALID);
        } else {
            Cell cell = board.getCell(parts[3]);
            
            sharedChessTeamParameters.setEnPassantPosition(cell.getPosition());
        }

        // Half move clock
        setHalfMoves(Integer.parseInt(parts[4]));

        // Full move number
        setFullMoves(Integer.parseInt(parts[5]));

        /*
         * Invoke events
         */
        getOnGameLoadedEvent().trigger(fen);
    }
}