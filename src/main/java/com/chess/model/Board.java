package com.chess.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The board that the game is played on.
 */
public class Board {

    /**
     * Matrix of cells.
     */
    private Cell[][] cellArray;

    /**
     * The chess model.
     */
    private ChessModel model;

    /**
     * The size of the board.
     */
    private int gameSize;

    /**
     * Constructs a new board.
     * 
     * @param model the chess model
     * @param gameSize the size of the board
     */
    public Board(ChessModel model, int gameSize){
        this.model = model;
        this.gameSize = gameSize;
        initCellArray(gameSize);
    }

    /**
     * Initializes the cell matrix.
     * 
     * @param gameSize the size of the board
     */
    private void initCellArray(int gameSize) {
        cellArray = new Cell[gameSize][gameSize];
        for(int row = 0; row < gameSize; row++) {
            for(int col = 0; col < gameSize; col++) {
                cellArray[row][col] = new Cell(row, col);
            }
        }
    }

    /**
     * Get a cell from the cell matrix.
     * 
     * @param row the row of the cell
     * @param col the column of the cell
     * @return the cell at the specified position
     */
    public Cell getCell(int row, int col) {
        return cellArray[row][col];
    }

    /**
     * Get a cell from the cell matrix from a string, like "a1".
     * 
     * @param position the position of the cell
     * @return the cell at the specified position
     */
    public Cell getCell(String position) {
        int row = position.charAt(1) - '1';
        int col = position.charAt(0) - 'a';
        row = gameSize - row - 1;
        return getCell(row, col);
    }

    /**
     * Get the chess model.
     * 
     * @return the chess model
     */
    public ChessModel getChessModel() { return this.model; }

    /**
     * Get the game size.
     * 
     * @return the game size
     */
    public int getGameSize() { return this.gameSize; }

    /**
     * If a cell is empty.
     * 
     * @param row the row of the cell
     * @param col the column of the cell
     * @return if the cell is empty
     */
    public boolean isEmpty(int row, int col) {
        return (cellArray[row][col].getPiece() == null);
    }

    /**
     * If a position is valid.
     * 
     * @param row the row of the position
     * @param col the column of the position
     * @return if the position is valid
     */
    public boolean isValid(int row, int col) {
        return ((row < gameSize && row >= 0) && (col < gameSize && col >= 0));
    }

    /**
     * If a piece can capture in this position.
     * 
     * @param piece the piece
     * @param row the row of the position
     * @param col the column of the position
     */
    public boolean canCapture(Piece piece, int row, int col) {
        if(!isValid(row, col)) return false;
        if(isEmpty(row, col)) return false;
        if(piece.getTeam() == cellArray[row][col].getPiece().getTeam()) return false;
        return true;
    }

    /**
     * If a certain move is legal.
     * 
     * @param piece the piece
     * @param move the move
     * @return if the move is legal
     */
    public boolean isLegalMove(Piece piece, Move move) {

        Cell tempCell = piece.getCell();
        Piece originPiece = move.getToCell().getPiece();
        boolean hasMoved = piece.hasMoved();
        piece.fakeMove(move.getToCell()); //1. Flyttar cellen

        if(isCheck(piece.getTeam())){ //2. Kollar om det är schack efter flytten.
            piece.fakeMove(tempCell); //Om det är schack så flyttar vi tillbaks pjäsen.
            move.getToCell().setPiece(originPiece);
            piece.setHasMoved(hasMoved);
            return false; //returnerar att det är falsk (en illegal move).
        }
        else{
            piece.fakeMove(tempCell); //Om det inte är schack så flyttar vi tillbaks pjäs
            move.getToCell().setPiece(originPiece);
            piece.setHasMoved(hasMoved);
            return true;
        }
    }

    /**
     * Get a list of all enemy moves.
     * 
     * @param playerTeam the team of the player
     * @return the list of all enemy moves
     */
    private List<Move> allEnemyMoves(Team playerTeam){
        List<Move> enemyMovesList =new ArrayList<>();
        for(int row=0;row<gameSize;row++){
            for(int col=0;col<gameSize;col++){
                if(cellArray[row][col].getPiece()!=null && cellArray[row][col].getPiece().getTeam()!=playerTeam){
                    Iterator<Move> it =cellArray[row][col].getPiece().getPossibleMoves(this);
                    while(it.hasNext()) {
                        enemyMovesList.add(it.next());
                    }
                }
            }
        }
        return enemyMovesList;
    }

    /**
     * Get a list of all moves.
     * 
     * @param playerTeam the team of the player
     * @return the list of all moves
     */
    private List<Move> allTeamMoves(Team playerTeam){
        List<Move> teamMovesList =new ArrayList<>();
        for(int row=0;row<gameSize;row++){
            for(int col=0;col<gameSize;col++){
                if(cellArray[row][col].getPiece()!=null && cellArray[row][col].getPiece().getTeam()==playerTeam){
                    Iterator<Move> it =cellArray[row][col].getPiece().getPossibleMoves(this);
                    while(it.hasNext()) {
                        teamMovesList.add(it.next());
                    }
                }
            }
        }
        return teamMovesList;
    }

    /**
     * Checks if it is checkmate or stalemate. If it is checkmate it returns 2. If it is stalemate it return 1. Else 0.
     * 
     * @param enemyPlayerTeam the team of the enemy player
     */
    public int isGameOver(Team enemyPlayerTeam){
        if(allTeamMoves(enemyPlayerTeam).isEmpty() && isCheck(enemyPlayerTeam)){
            return 2;
        }
        else if(allTeamMoves(enemyPlayerTeam).isEmpty() && !isCheck(enemyPlayerTeam)){
            return 1;
        }
        else{
            return 0;
        }
    }

    /**
     * Check if it is check.
     * 
     * @param team the team of the player
     * @return if it is check
     */
    public boolean isCheck(Team team){
        if(isGameOver(model.getOtherTeam(team)) != 0){
            return false;
        }
        List<Move> allEnemyMoves = allEnemyMoves(team);
        for(Move m:allEnemyMoves){
            if(m.getToCell().getPiece()!=null && m.getToCell().getPiece().getTeam().equals(team) && m.getToCell().getPiece().getPieceType().equals(PieceType.KING)){
                return true;
            }
        }
        return false;
    }

    /**
     * Get the cell where the king is.
     * 
     * @param team the team of the player
     * @return the cell where the king is
     */
    public Cell getKingCell(Team team){
        Cell kingCell=null;
        for(int row=0;row<gameSize;row++){
            for(int col=0;col<gameSize;col++){
                if(cellArray[row][col].getPiece()!=null
                   && cellArray[row][col].getPiece().getPieceType().equals(PieceType.KING)
                   && cellArray[row][col].getPiece().getTeam().equals(team)){
                    kingCell =  cellArray[row][col];
                }
            }
        }
        return kingCell;
    }

    /**
     * Validate a list of moves, filter out illegal moves.
     * 
     * @param piece the piece
     * @param moves the list of moves
     */
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

    /**
     * Transform a row/col to a string.
     * 
     * @param row the row of the cell
     * @param col the column of the cell
     * @return the string
     */
    public String positionToString(int row, int col) {
        // Row numbers are reversed
        row = gameSize - row - 1;
        return "" + (char)('a' + col) + (row + 1);
    }

    /**
     * Calculate a moveset.
     * 
     * @param cell the cell containing the piece
     * @param registry a registry of the moves, out parameter
     * @param dirRow the row direction
     * @param dirCol the column direction
     * @param maxSteps the maximum amount of steps
     * @param skipOwn whether to skip own pieces
     * @param cantCapture whether to stop if a piece is found
     * @param requireCapture whether to require capturing
     */
    public void calculateMoves(Cell cell, List<Move> registry, int dirRow, int dirCol, int maxSteps, boolean skipOwn, boolean cantCapture, boolean requireCapture) {
        int row = cell.getRow();
        int col = cell.getCol();
        Piece piece = cell.getPiece();
        Team team = piece.getTeam();
        int step = 1;

        // If maxSteps is 0, then the piece can move infinitely in that direction
        if (maxSteps == 0) {
            maxSteps = gameSize;
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
                if (requireCapture) {
                    break;
                }
                
                registry.add(new Move(nextCell, cell,false));
            }
            else if (otherPiece.getTeam() != team) {
                if (cantCapture) {
                    break;
                }

                registry.add(new Move(nextCell, cell,true));
                break;
            }
            else if (!skipOwn) {
                break;
            }
        }

        if(model.getCurrentTeam() == team) {
            validateMoves(piece, registry);
        }

    }

    /**
     * Calculate a moveset.
     * 
     * @param cell the cell containing the piece
     * @param registry a registry of the moves, out parameter
     * @param dirRow the row direction
     * @param dirCol the column direction
     * @param maxSteps the maximum amount of steps
     */
    public void calculateMoves(Cell cell, List<Move> registry, int dirRow, int dirCol, int maxSteps) {
        calculateMoves(cell, registry, dirRow, dirCol, maxSteps, false, false, false);
    }

    /**
     * Calculate a moveset.
     * 
     * @param cell the cell containing the piece
     * @param registry a registry of the moves, out parameter
     * @param dirRow the row direction
     * @param dirCol the column direction
     */
    public void calculateMoves(Cell cell, List<Move> registry, int dirRow, int dirCol) {
        calculateMoves(cell, registry, dirRow, dirCol, 0, false, false, false);
    }
}
