package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class for the Queen.
 *
 * Variables:
 * - _possibleMoves: Contains all the moves that the pawn can do.
 * - tempBoard: References to the game board.
 */
public class PieceQueen extends Piece {
    private ArrayList<Move> _possibleMoves;
    private final Board tempBoard = this.getCell().getBoard();

    public PieceQueen(Cell cell, Team team) {
        super(cell, team,PieceType.QUEEN);
    }

    /**
     * Returns the iterator with all possible move for this Queen-piece.
     * @return _possibleMoves.iterator() containing all moves.
     */
    public Iterator<Move> getPossibleMoves(){
        _possibleMoves = new ArrayList<Move>();
        getNextCellBishop(tempBoard);
        getNextCellRook(tempBoard);
        return _possibleMoves.iterator(); //Fix so we get the possibleMoves for a pawn. Probably check get the current pos, get posX+1 and so on.
    }

    @Override
    public String toString() {
        if(getTeam().getColor().equals(Color.WHITE)) return "WQueen";
        return "BQueen";
    }

    /**
     * Helps add all the diagonally moves to _possibleMove array that the Queen can move to.
     * @param currentBoard - The board containing all the cells.
     */
    private void getNextCellBishop(Board currentBoard){
        int row = this.getCell().getRow();
        int col = this.getCell().getCol();

        int inc = 1;
        while(currentBoard.isValid(row - inc, col - inc)){
            if(currentBoard.getCell(row - inc, col - inc).getPiece() == null){
                _possibleMoves.add(new Move(currentBoard.getCell(row - inc, col - inc), false));
                inc++;
            }
            else if(checkEliminate(new Move(currentBoard.getCell(row - inc, col - inc)))){
                _possibleMoves.add(new Move(currentBoard.getCell(row - inc, col - inc), true));
                inc = 10;
            }
            else{
                break;
            }
        }
        inc = 1;
        while(currentBoard.isValid(row - inc, col + inc)){
            if(currentBoard.getCell(row - inc, col + inc).getPiece() == null){
                _possibleMoves.add(new Move(currentBoard.getCell(row - inc, col + inc), false));
                inc++;
            }
            else if(checkEliminate(new Move(currentBoard.getCell(row - inc, col + inc)))){
                _possibleMoves.add(new Move(currentBoard.getCell(row - inc, col + inc), true));
                inc = 10;
            }
            else{
                break;
            }
        }
        inc = 1;
        while(currentBoard.isValid(row + inc, col + inc)){
            if(currentBoard.getCell(row + inc, col + inc).getPiece() == null){
                _possibleMoves.add(new Move(currentBoard.getCell(row + inc, col + inc), false));
                inc++;
            }
            else if(checkEliminate(new Move(currentBoard.getCell(row + inc, col + inc)))){
                _possibleMoves.add(new Move(currentBoard.getCell(row + inc, col + inc), true));
                inc = 10;
            }
            else{
                break;
            }
        }
        inc = 1;
        while(currentBoard.isValid(row + inc, col - inc)){
            if(currentBoard.getCell(row + inc, col - inc).getPiece() == null){
                _possibleMoves.add(new Move(currentBoard.getCell(row + inc, col - inc), false));
                inc++;
            }
            else if(checkEliminate(new Move(currentBoard.getCell(row + inc, col - inc)))){
                _possibleMoves.add(new Move(currentBoard.getCell(row + inc, col - inc), true));
                inc = 10;
            }
            else{
                break;
            }
        }
    }

    /**
     * Helps add all the horizontal and vertical moves to _possibleMove array that the Queen can move to.
     * @param currentBoard - The board containing all the cells.
     */
    private void getNextCellRook(Board currentBoard){
        int row = this.getCell().getRow();
        int col = this.getCell().getCol();

        int inc = 1;
        while(currentBoard.isValid(row, col - inc)){
            if(currentBoard.getCell(row, col - inc).getPiece() == null){
                _possibleMoves.add(new Move(currentBoard.getCell(row, col - inc), false));
                inc++;
            }
            else if(checkEliminate(new Move(currentBoard.getCell(row, col - inc)))){
                _possibleMoves.add(new Move(currentBoard.getCell(row, col - inc), true));
                inc = 10;
            }
            else{
                break;
            }
        }
        inc = 1;
        while(currentBoard.isValid(row, col + inc)){
            if(currentBoard.getCell(row, col + inc).getPiece() == null){
                _possibleMoves.add(new Move(currentBoard.getCell(row, col + inc), false));
                inc++;
            }
            else if(checkEliminate(new Move(currentBoard.getCell(row, col + inc)))){
                _possibleMoves.add(new Move(currentBoard.getCell(row, col + inc), true));
                inc = 10;
            }
            else{
                break;
            }
        }
        inc = 1;
        while(currentBoard.isValid(row + inc, col)){
            if(currentBoard.getCell(row + inc, col).getPiece() == null){
                _possibleMoves.add(new Move(currentBoard.getCell(row + inc, col), false));
                inc++;
            }
            else if(checkEliminate(new Move(currentBoard.getCell(row + inc, col)))){
                _possibleMoves.add(new Move(currentBoard.getCell(row + inc, col), true));
                inc = 10;
            }
            else{
                break;
            }
        }
        inc = 1;
        while(currentBoard.isValid(row - inc, col)){
            if(currentBoard.getCell(row - inc, col).getPiece() == null){
                _possibleMoves.add(new Move(currentBoard.getCell(row - inc, col), false));
                inc++;
            }
            else if(checkEliminate(new Move(currentBoard.getCell(row - inc, col)))){
                _possibleMoves.add(new Move(currentBoard.getCell(row - inc, col), true));
                inc = 10;
            }
            else{
                break;
            }
        }
    }
}
