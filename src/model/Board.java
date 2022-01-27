package model;

import java.util.*;

public class Board {

    private Cell[][] _cellArray;

    private ChessModel _model;
    private int _gameSize;

    public Board(ChessModel model, int gameSize){
        this._model = model;
        this._gameSize = gameSize;
        initCellArray(gameSize);
    }

    private void initCellArray(int gameSize) {
        _cellArray = new Cell[gameSize][gameSize];
        for(int row = 0; row < gameSize; row++){
            for(int col = 0; col < gameSize; col++){
                _cellArray[row][col] = new Cell(this, row, col);
            }
        }
    }

    public Cell getCell(int row, int col) {
        return _cellArray[row][col];
    }

    public ChessModel getChessModel() { return this._model; }

    public int getGameSize() { return this._gameSize; }

    public boolean isEmpty(int row, int col) {
        return (_cellArray[row][col].getPiece() == null);
    }

    public boolean isValid(int row, int col) {
        return ((row < _gameSize && row >= 0) && (col < _gameSize && col >= 0));
    }

    public boolean canCapture(Piece piece, int row, int col) {
        if(!isValid(row, col)) return false;
        if(isEmpty(row, col)) return false;
        if(piece.getTeam() == _cellArray[row][col].getPiece().getTeam()) return false;
        return true;
    }

    public boolean isLegalMove(Piece piece, Move move) {
        return true; // TODO: Check for if this creates an illegal checkmate
    }

    public void validateMoves(Piece piece, List<Move> moves) {
        // Remove all illegal moves from the list
        Iterator<Move> it = moves.iterator();
        while(it.hasNext()) {
            Move move = it.next();
            if(!isLegalMove(piece, move)) {
                it.remove();
            }
        }
    }

    public void calculateMoves(Piece piece, List<Move> registry, int dirRow, int dirCol, int maxSteps, boolean skipOwn, boolean cantCapture, boolean requireCapture) {
        Team team = piece.getTeam();
        int row = piece.getCell().getRow();
        int col = piece.getCell().getCol();
        int step = 1;

        // If maxSteps is 0, then the piece can move infinitely in that direction
        if (maxSteps == 0) {
            maxSteps = _gameSize;
        }

        while (step <= maxSteps) {
            int nextRow = row + step * dirRow;
            int nextCol = col + step * dirCol;
            step++;
            
            if (!isValid(nextRow, nextCol)) {
                break;
            }

            Cell nextCell = getCell(nextRow, nextCol);
            
            Piece otherPiece = nextCell.getPiece();
            
            if (otherPiece == null) {
                if (piece instanceof PiecePawn && requireCapture) {
                    Team otherTeam = _model.getOtherTeam(team);

                    // Check if we can capture en passant
                    if (otherTeam.isEnPassant(nextRow, nextCol)) {
                        Move move = new Move(nextCell, true);
                        move.setIsEnPassant(true);
                        registry.add(move);

                        break;
                    }
                }

                if (requireCapture) {
                    break;
                }
                
                registry.add(new Move(nextCell, false));
            }
            else if (otherPiece.getTeam() != team) {
                if (cantCapture) {
                    break;
                }

                registry.add(new Move(nextCell, true));
                break;
            }
            else if (!skipOwn) {
                break;
            }
        }
    }

    public void calculateMoves(Piece piece, List<Move> registry, int dirRow, int dirCol, int maxSteps) {
        calculateMoves(piece, registry, dirRow, dirCol, maxSteps, false, false, false);
    }

    public void calculateMoves(Piece piece, List<Move> registry, int dirRow, int dirCol) {
        calculateMoves(piece, registry, dirRow, dirCol, 0, false, false, false);
    }

    public String toFEN() {
        /**
         * https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation
         */
        String fen = "";
        for(int row = 0; row < _gameSize; row++){
            int emptyCells = 0;
            for(int col = 0; col < _gameSize; col++){
                if(_cellArray[row][col].getPiece() == null){
                    emptyCells++;
                }
                else{
                    if(emptyCells > 0){
                        fen += emptyCells;
                        emptyCells = 0;
                    }
                    Piece piece = _cellArray[row][col].getPiece();
                    String character = piece.getPieceType().getFilePrefix();
                    if (piece.getTeam() == _model.getTeamWhite()) {
                        character = character.toUpperCase();
                    }
                    fen += character;
                }
            }
            if(emptyCells > 0){
                fen += emptyCells;
            }
            if(row != _gameSize - 1){
                fen += "/";
            }
        }
        // TODO: Add castling, en passant, and halfmove clock
        return fen;
    }

}
