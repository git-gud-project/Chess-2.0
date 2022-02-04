package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PiecePawn extends Piece {

    private ArrayList<Move> possibleMoves;

    public PiecePawn(Cell cell, Team team) {
        super(cell, team, PieceType.PAWN);
    }

    //Getter for the iterator containing all possibleMoves.
    //TO-FIX: En croissant!
    public Iterator<Move> getPossibleMoves(){
        possibleMoves = new ArrayList<>();

        Team team = this.getTeam();
        Board board = this.getCell().getBoard();

        int dirRow = team.getPawnDirectionRow();
        
        //If the pawn is on the first row, it can move 2 cells.
        if (!hasMoved()) {
            board.calculateMoves(this, possibleMoves, dirRow, 0, 2, false, true, false);
        } else {
            board.calculateMoves(this, possibleMoves, dirRow, 0, 1, false, true, false);
        }
        
        board.calculateMoves(this, possibleMoves, dirRow, 1, 1, false, false, true);
        board.calculateMoves(this, possibleMoves, dirRow, -1, 1, false, false, true);

        return possibleMoves.iterator();
    }

    @Override
    public void onMove(Cell oldCell, Cell newCell, boolean state) {
        Team team = this.getTeam();
        Board board = this.getCell().getBoard();
        Team otherTeam = board.getChessModel().getOtherTeam(team);

        // Check if we moved 2 cells.
        if (!hasMoved() && Math.abs(oldCell.getRow() - newCell.getRow()) == 2) {
            team.setEnPassant(this);
        }

        // Check if we did en passant.

        if (otherTeam.isEnPassant(newCell.getRow(), newCell.getCol())) {
            Piece piece = otherTeam.getEnPassantPiece();
            piece.getCell().setPiece(null);
        }
        
        super.onMove(oldCell, newCell, state);
    }

    @Override
    public String toString() {
        if(getTeam().getColor().equals(Color.WHITE)) return "WPawn";
        return "BPawn";
    }
}
