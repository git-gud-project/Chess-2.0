package com.chess.model.chess;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import com.chess.model.*;
import com.chess.utils.Event;

/**
 * Model for a chess game.
 */
public class ChessModel {

    /**
     * A chess board always has a size of 8x8.
     */
    private static final int GAMESIZE = 8;

    /**
     * The chess board.
     */
    private final ChessBoard board;

    /**
     * The chess board information.
     */
    private final ChessBoardInformation boardInformation;

    /**
     * The team manager.
     */
    private final TeamManager teamManager;

    /**
     * The rules of the game.
     */
    private final ChessRule rule;

    /**
     * The shared team parameters.
     */
    private final SharedChessTeamParameters sharedChessTeamParameters;

    /**
     * If the game is paused.
     */
    private boolean paused;

    /**
     * If the game has started.
     */
    private boolean started;

    /**
     * How many full moves have been made.
     */
    private int fullMoves;

    /**
     * Half move timer.
     */
    private int halfMoves;

    /**
     * If the game is over.
     */
    private boolean isGameOver;

    /**
     * List of moves.
     */
    private List<String> moveList;

    /**
     * The event which is triggered when the team changes.
     */
    private Event<ChessTeam> onTeamChangeEvent = new Event<>();

    /**
     * The event which is triggered when a move is made.
     */
    private Event<Move> onMoveEvent = new Event<>();

    /**
     * The event which is triggered when a game is loaded.
     */
    private Event<String> onGameLoadedEvent = new Event<>();

    /**
     * The event which is triggered when a game is loaded from a serial model.
     */
    private Event<SerialModel> onModelLoadedEvent = new Event<>();

    /**
     * The event which is triggered when the game time of a player turns to zero.
     */
    private Event<GameTime> onGameTimeZeroEvent = new Event<>();

    /**
     * Construct a new ChessModel.
     * 
     * This does load the default chess board.
     * 
     * @see ChessModel#resetState(GameTime)
     */
    public ChessModel() {
        // Create the shared team parameters
        sharedChessTeamParameters = new SharedChessTeamParameters();

        // White team parameters
        final ChessTeamParameters whiteParameters = new ChessTeamParameters(
                sharedChessTeamParameters, // shared
                ChessTeamIdentifier.WHITE, // identifier
                1, // pawnDirection
                0 // kingRow
        );

        // Set castling rights
        whiteParameters.setCanCastleKingside(true);
        whiteParameters.setCanCastleQueenside(true);

        // Set castling positions
        whiteParameters.setCastlingKingSidePosition(new Position("g1"));
        whiteParameters.setCastlingQueenSidePosition(new Position("c1"));

        // Create white team
        final ChessTeam whiteTeam = new ChessTeam(
            ChessTeamIdentifier.WHITE,
            Color.WHITE,
            "Player 1",
            new GameTime(5, 0, 0),
            whiteParameters
        );

        // Black team parameters
        final ChessTeamParameters blackParameters = new ChessTeamParameters(
                sharedChessTeamParameters, // shared
                ChessTeamIdentifier.BLACK, // identifier
                -1, // pawnDirection
                7 // kingRow
        );

        // Set castling rights
        blackParameters.setCanCastleKingside(true);
        blackParameters.setCanCastleQueenside(true);

        // Set castling positions
        blackParameters.setCastlingKingSidePosition(new Position("g8"));
        blackParameters.setCastlingQueenSidePosition(new Position("c8"));

        // Create black team
        final ChessTeam blackTeam = new ChessTeam(
            ChessTeamIdentifier.BLACK,
            Color.BLACK,
            "Player 2",
            new GameTime(5, 0, 0),
            blackParameters
        );

        // Setup team manager

        teamManager = new TeamManager(whiteTeam, blackTeam);

        // Setup board

        board = new ChessBoard(GAMESIZE);

        // Setup board information

        boardInformation = new ChessBoardInformation(board, teamManager);

        // Setup rule

        rule = new ChessRule(boardInformation, teamManager);

        // Setup other variables

        paused = true;
        fullMoves = 1;
        isGameOver = false;
        moveList = new ArrayList<>();
    }

    /**
     * Reset the state of this ChessModel to its starting state
     * 
     * @param time The time of the players when the game starts
     */
    public void resetState(GameTime time) {
        // Load initial chess positions
        loadFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        final ChessTeam whiteTeam = teamManager.getTeam(ChessTeamIdentifier.WHITE);

        whiteTeam.setTime(time);

        final ChessTeam blackTeam = teamManager.getTeam(ChessTeamIdentifier.BLACK);

        blackTeam.setTime(time);

        teamManager.setCurrentTeamIdentifier(ChessTeamIdentifier.WHITE);

        setPaused(true);
        setStarted(false);

        moveList = new ArrayList<>();

        onModelLoadedEvent.trigger(getSerialModel());
    }

    /**
     * Get a serial model representation of this ChessModel.
     * This may be serialized and then loaded again.
     * 
     * @return A serial model representation of this ChessModel
     */
    public SerialModel getSerialModel() {
        SerialModel serialModel = new SerialModel();
        
        serialModel.setFen(toFEN());
        serialModel.setMoveList(moveList);
        serialModel.setSkinMapWhite(getTeamWhite().getSkinMap());
        serialModel.setSkinMapBlack(getTeamBlack().getSkinMap());
        serialModel.setWhiteName(getTeamWhite().getName());
        serialModel.setBlackName(getTeamBlack().getName());
        serialModel.setWhiteTime(getTeamWhite().getTime());
        serialModel.setBlackTime(getTeamBlack().getTime());
        serialModel.setStarted(getStarted());

        return serialModel;
    }

    /**
     * Load a serial model representation of this ChessModel.
     * 
     * @param smodel The model to load over the existing model
     */
    public void loadModel(SerialModel smodel) {
        final ChessTeam whiteTeam = teamManager.getTeam(ChessTeamIdentifier.WHITE);
        final ChessTeam blackTeam = teamManager.getTeam(ChessTeamIdentifier.BLACK);

        whiteTeam.setName(smodel.getWhiteName());
        blackTeam.setName(smodel.getBlackName());

        whiteTeam.setTime(smodel.getWhiteTime());
        blackTeam.setTime(smodel.getBlackTime());

        whiteTeam.setSkinMap(smodel.getWhiteSkinMap());
        blackTeam.setSkinMap(smodel.getBlackSkinMap());

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
     *        it is checkmate
     */
    public int isGameOver(Identifier enemyTeamIdentifier) {
        return rule.isGameOver(enemyTeamIdentifier);
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

                if (piece.getTeamIdentifier().equals(teamIdentifier) && piece.getTypeIdentifier().equals(ChessTypeIdentifier.KING)) {
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
        return teamManager.getTeam(ChessTeamIdentifier.WHITE);
    }

    /**
     * Get the team class instance which represents the black team
     * 
     * @return The black team instance
     */
    public ChessTeam getTeamBlack() {
        return teamManager.getTeam(ChessTeamIdentifier.BLACK);
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

    public Event<GameTime> getOnGameTimeZeroEvent(){ return this.onGameTimeZeroEvent; }

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
        if (teamManager.getCurrentTeamIdentifier().equals(ChessTeamIdentifier.BLACK)) {
            fullMoves++;
        }

        Identifier enPassantTeam = sharedChessTeamParameters.getEnPassantTeam();

        // Clear en passant if it's not the current team
        if (!enPassantTeam.equals(teamManager.getCurrentTeamIdentifier())) {
            sharedChessTeamParameters.setEnPassantTeam(ChessTypeIdentifier.NULL);
            sharedChessTeamParameters.setEnPassantPosition(Position.INVALID);
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
            move.setCheckMate(true);
        }

        // Add '+' to notation if move resulted in check to other team
        else if (rule.isCheck(teamManager.getCurrentTeamIdentifier())) {
            move.setCheck(true);
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
     * Take a turn, moving a piece from one position to another
     * 
     * @param from The position the piece is moving from
     * @param to The position the piece is moving to
     * @throws IllegalArgumentException If the move is invalid
     */
    public void takeTurn(Position from, Position to) throws IllegalArgumentException {
        // Get the piece to move
        final Cell fromCell = board.getCell(from);
        final Cell toCell = board.getCell(to);
        
        // Get the piece to move
        final Piece piece = fromCell.getPiece();

        if (!piece.getTeamIdentifier().equals(teamManager.getCurrentTeamIdentifier())) {
            throw new IllegalArgumentException("Cannot move a piece that is not yours");
        }

        Iterator<Move> moves = piece.getPossibleMoves(rule, from);

        // Check that 'to' is a valid move
        boolean validMove = false;

        while (moves.hasNext()) {
            Move move = moves.next();

            if (move.getToCell().equals(to)) {
                validMove = true;
                break;
            }
        }

        if (!validMove) {
            throw new IllegalArgumentException("Cannot move to that position");
        }

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
     * @see <a href="http://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation">Forsyth-Edwards Notation</a>
     */
    public String toFEN() {
        String fen = "";
        for (int rowi = 0; rowi < GAMESIZE; rowi++) {
            int emptyCells = 0;
            int row = GAMESIZE - rowi - 1;
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
                    if (piece.getTeamIdentifier().equals(ChessTeamIdentifier.WHITE)) {
                        character = character.toUpperCase();
                    }
                    fen += character;
                }
            }
            if (emptyCells > 0) {
                fen += emptyCells;
            }
            if (row != 0) {
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

        if (!enPassantPosition.equals(Position.INVALID)) {
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
     * @see <a href="http://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation">Forsyth-Edwards Notation</a>
     * @throws IllegalArgumentException if the FEN is invalid
     */
    public void loadFEN(String fen) throws IllegalArgumentException {
        System.out.println("Loading FEN: " + fen);

        String[] parts = fen.split(" ");
        String[] rows = parts[0].split("/");

        // Check that we have the correct number of rows
        if (rows.length != GAMESIZE) {
            throw new IllegalArgumentException("Invalid number of rows, got " + rows.length + ", expected " + GAMESIZE);
        }

        // Empty all cells
        for (int row = 0; row < GAMESIZE; row++) {
            for (int col = 0; col < GAMESIZE; col++) {
                board.getCell(row, col).emptyCell(true);
            }
        }

        int row = GAMESIZE - 1;
        int col = 0;
        for (String rowString : rows) {
            for (int i = 0; i < rowString.length(); i++) {
                // Check that we have the correct number of columns
                if (col > GAMESIZE) {
                    throw new IllegalArgumentException("Invalid number of columns, got " + col + ", expected a max of " + GAMESIZE);
                }

                Cell cell = board.getCell(row, col);

                char c = rowString.charAt(i);
                if (Character.isDigit(c)) {
                    for (int j = 0; j < Character.getNumericValue(c); j++) {
                        
                        cell.emptyCell(true);
                        col++;
                    }
                } else {
                    ChessTeam team = c == Character.toUpperCase(c) ? getTeamWhite() : getTeamBlack();

                    Identifier typeIdentifier = ChessTypeIdentifier.getIdentifier(String.valueOf(Character.toLowerCase(c)));

                    Piece piece = ChessPieceFactory.createPiece(typeIdentifier, team);

                    cell.updatePiece(piece, true);

                    col++;
                }
            }
            row--;
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