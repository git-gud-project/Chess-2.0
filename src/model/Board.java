package model;

import java.awt.*;
import java.util.*;
import java.util.List;

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

    public Cell getCell(String position) {
        int row = position.charAt(1) - '1';
        int col = position.charAt(0) - 'a';
        row = _gameSize - row - 1;
        return getCell(row, col);
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

        Cell tempCell = piece.getCell();
        Piece originPiece = move.getCell().getPiece();
        boolean hasMoved = piece.hasMoved();
        piece.fakeMove(move.getCell()); //1. Flyttar cellen

        if(isCheck(piece.getTeam())){ //2. Kollar om det är schack efter flytten.
            piece.fakeMove(tempCell); //Om det är schack så flyttar vi tillbaks pjäsen.
            move.getCell().setPiece(originPiece);
            piece.setHasMoved(hasMoved);
            return false; //returnerar att det är falsk (en illegal move).
        }
        else{
            piece.fakeMove(tempCell); //Om det inte är schack så flyttar vi tillbaks pjäs
            move.getCell().setPiece(originPiece);
            piece.setHasMoved(hasMoved);
            return true;
        }
        /*
        System.out.println("Moves: ");
        List<Move> l = allEnemyMoves(piece.getTeam());
        Iterator<Move> it = l.iterator();
        while(it.hasNext()){
            System.out.print(it.next().getCell()+" ");
        }
        System.out.println(""); */
        //return false;
    }

    private List<Move> allEnemyMoves(Team playerTeam){
        List<Move> enemyMovesList =new ArrayList<>();
        for(int row=0;row<_gameSize;row++){
            for(int col=0;col<_gameSize;col++){
                if(_cellArray[row][col].getPiece()!=null && _cellArray[row][col].getPiece().getTeam()!=playerTeam){
                    Iterator<Move> it =_cellArray[row][col].getPiece().getPossibleMoves();
                    while(it.hasNext()) {
                        enemyMovesList.add(it.next());
                    }
                }
            }
        }
        return enemyMovesList;
    }

    private List<Move> allTeamMoves(Team playerTeam){
        List<Move> teamMovesList =new ArrayList<>();
        for(int row=0;row<_gameSize;row++){
            for(int col=0;col<_gameSize;col++){
                if(_cellArray[row][col].getPiece()!=null && _cellArray[row][col].getPiece().getTeam()==playerTeam){
                    Iterator<Move> it =_cellArray[row][col].getPiece().getPossibleMoves();
                    while(it.hasNext()) {
                        teamMovesList.add(it.next());
                    }
                }
            }
        }
        return teamMovesList;
    }

    public boolean isCheckmate(Team currentPlayerTeam){
        if(allTeamMoves(currentPlayerTeam).isEmpty()){
            System.out.println("SCHACKMATT!");
            return true;
        }
        else{
            return false;
        }
    }

    //Checks if the king is in check.
    public boolean isCheck(Team team){
        List<Move> allEnemyMoves = allEnemyMoves(team);
        for(Move m:allEnemyMoves){
            if(m.getCell().getPiece()!=null && m.getCell().getPiece().getTeam().equals(team) && m.getCell().getPiece().getPieceType().equals(PieceType.KING)){
                System.out.println("SCHACK!");
                return true;
            }
        }
        return false;
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

    public String positionToString(int row, int col) {
        // Row numbers are reversed
        row = _gameSize - row - 1;
        return "" + (char)('a' + col) + (row + 1);
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

        if(_model.getCurrentTeam()== piece.getTeam()) {
            validateMoves(piece, registry);
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
        
        // Add 'w' or 'b' for the side to move
        fen += " " + _model.getCurrentTeam().getFileSuffix();

        // Castling rights
        Team white = _model.getTeamWhite();
        Team black = _model.getTeamBlack();
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
        Team currentTeam = _model.getOtherTeam(_model.getCurrentTeam());

        if (currentTeam.getEnPassantPiece() != null) {
            Piece enPassantPiece = currentTeam.getEnPassantPiece();
            Cell enPassantCell = enPassantPiece.getCell();
            fen += " " + positionToString(enPassantCell.getRow(), enPassantCell.getCol());
        }
        else {
            fen += " -";
        }

        // Half move clock
        fen += " " + _model.getHalfMoves();

        // Full move number
        fen += " " + _model.getFullMoves();

        return fen; 
    }

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
                        Cell cell = getCell(row, col);
                        cell.setPiece(null);
                        col++;
                    }
                }
                else{
                    Piece piece = null;
                    Team team = c == Character.toUpperCase(c) ? _model.getTeamWhite() : _model.getTeamBlack();
                    Cell cell = getCell(row, col);
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
                    _cellArray[row][col].setPiece(piece);
                    col++;
                }
            }
            row++;
            col = 0;
        }

        Team team = parts[1].equals("w") ? _model.getTeamWhite() : _model.getTeamBlack();
        _model.setCurrentTeam(team);

        // Castling rights
        String castlingRights = parts[2];
        Team white = _model.getTeamWhite();
        Team black = _model.getTeamBlack();
        
        white.setHasCastlingRightKingSide(castlingRights.contains("K"));
        white.setHasCastlingRightQueenSide(castlingRights.contains("Q"));
        black.setHasCastlingRightKingSide(castlingRights.contains("k"));
        black.setHasCastlingRightQueenSide(castlingRights.contains("q"));

        // En passant target square
        if (parts[3].equals("-")) {
            _model.getOtherTeam(_model.getCurrentTeam()).clearEnPassant();
        }
        else {
            Cell cell = getCell(parts[3]);
            _model.getOtherTeam(_model.getCurrentTeam()).setEnPassant(cell.getPiece());
        }

        // Half move clock
        _model.setHalfMoves(Integer.parseInt(parts[4]));

        // Full move number
        _model.setFullMoves(Integer.parseInt(parts[5]));
    }
}
