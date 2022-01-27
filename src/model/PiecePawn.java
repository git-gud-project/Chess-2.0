package model;

import model.Piece;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class for the Pawn.
 *
 * Variables:
 * - firstMove: Contains the information if it is the pawns firstMove. Is true when it is the first move.
 * - _possibleMoves: Contains all the moves that the pawn can do.
 * - tempBoard: References to the game board.
 */
public class PiecePawn extends Piece {

    private boolean firstMove = true;
    private ArrayList<Move> _possibleMoves;
    private final Board tempBoard = this.getCell().getBoard();

    public PiecePawn(Cell cell, Team team) {
        super(cell, team, PieceType.PAWN);
    }

    //Getter for the iterator containing all possibleMoves.
    //TO-FIX: En croissant!
    public Iterator<Move> getPossibleMoves(){
        _possibleMoves = new ArrayList<>();
        int row = this.getCell().getRow();
        int col = this.getCell().getCol();
        if(this.getTeam().getColor().equals(Color.white)){
            if(firstMove){
                //Unnecessary but need to check if this is valid moves.
                _possibleMoves.add(new Move(tempBoard.getCell(row - 1, col), false));
                _possibleMoves.add(new Move(tempBoard.getCell(row - 2, col), false));

                if(tempBoard.isValid(row - 1, col - 1) && tempBoard.isValid(row - 1, col + 1)){
                    if(checkEliminate(new Move(tempBoard.getCell(row - 1, col - 1)))){
                        _possibleMoves.add(new Move(tempBoard.getCell(row - 1, col - 1), true));
                    }
                    if(checkEliminate(new Move(tempBoard.getCell(row - 1, col + 1)))){
                        _possibleMoves.add(new Move(tempBoard.getCell(row - 1, col + 1), true));
                    }
                }
            }
            else{
                if(tempBoard.isValid(row - 1, col)) {
                    if(!checkEliminate(new Move(tempBoard.getCell(row - 1, col)))) {
                        _possibleMoves.add(new Move(tempBoard.getCell(row - 1, col), false));
                    }
                }
                if(tempBoard.isValid(row - 1, col - 1) && tempBoard.isValid(row - 1, col + 1)){
                    if(checkEliminate(new Move(tempBoard.getCell(row - 1, col - 1)))){
                        _possibleMoves.add(new Move(tempBoard.getCell(row - 1, col - 1), true));
                    }
                    if(checkEliminate(new Move(tempBoard.getCell(row - 1, col + 1)))){
                        _possibleMoves.add(new Move(tempBoard.getCell(row - 1, col + 1), true));
                    }
                }
            }
        }
        else{
            if(firstMove){
                //Unnecessary but need to check if this is valid moves.
                _possibleMoves.add(new Move(tempBoard.getCell(row + 1, col), false));
                _possibleMoves.add(new Move(tempBoard.getCell(row + 2, col), false));
            }
            else{
                if(tempBoard.isValid(row + 1, col)) {
                    if(!checkEliminate(new Move(tempBoard.getCell(row + 1, col)))) {
                        _possibleMoves.add(new Move(tempBoard.getCell(row + 1, col), false));
                    }
                }
                if(tempBoard.isValid(row + 1, col - 1) && tempBoard.isValid(row + 1, col + 1)){ //change to check if move isValid()
                    if(checkEliminate(new Move(tempBoard.getCell(row + 1, col - 1)))){
                        _possibleMoves.add(new Move(tempBoard.getCell(row + 1, col - 1), true));
                    }
                    if(checkEliminate(new Move(tempBoard.getCell(row + 1, col + 1)))){
                        _possibleMoves.add(new Move(tempBoard.getCell(row + 1, col + 1), true));
                    }
                }
            }
        }
        return _possibleMoves.iterator();
    }

    /**
     * Sets the firstMove variable to false indicating that the piece have moved once.
     */
    public void setFirstMove(){
        this.firstMove = false;
    }

    @Override
    public String toString() {
        if(getTeam().getColor().equals(Color.WHITE)) return "WPawn";
        return "BPawn";
    }
}
