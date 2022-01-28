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

    public PiecePawn(Cell cell, Team team) {
        super(cell, team, PieceType.PAWN);
    }

    //Getter for the iterator containing all possibleMoves.
    //TO-FIX: En croissant!
    public Iterator<Move> getPossibleMoves(){
        _possibleMoves = new ArrayList<>();

        Team team = this.getTeam();
        Board board = this.getCell().getBoard();

        int dirRow = team.getPawnDirectionRow();
        
        //If the pawn is on the first row, it can move 2 cells.
        if (firstMove) {
            board.calculateMoves(this, _possibleMoves, dirRow, 0, 2, false, true, false);
        } else {
            board.calculateMoves(this, _possibleMoves, dirRow, 0, 1, false, true, false);
        }
        
        board.calculateMoves(this, _possibleMoves, dirRow, 1, 1, false, false, true);
        board.calculateMoves(this, _possibleMoves, dirRow, -1, 1, false, false, true);

        return _possibleMoves.iterator();
    }

    @Override
    public void onMove(Cell oldCell, Cell newCell) {
        Team team = this.getTeam();
        Board board = this.getCell().getBoard();
        Team otherTeam = board.getChessModel().getOtherTeam(team);

        // Check if we moved 2 cells.
        if (firstMove && Math.abs(oldCell.getRow() - newCell.getRow()) == 2) {
            team.setEnPassant(this);
        }

        // Check if we did en passant.

        if (otherTeam.isEnPassant(newCell.getRow(), newCell.getCol())) {
            Piece piece = otherTeam.getEnPassantPiece();
            piece.getCell().setPiece(null);
        }


        firstMove = false;
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
