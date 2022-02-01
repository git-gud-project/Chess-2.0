package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PieceKing extends Piece {
    private ArrayList<Move> _possibleMoves;

    public PieceKing(Cell cell, Team team) {
        super(cell, team,PieceType.KING);
        _possibleMoves = new ArrayList<Move>();
    }

    public Iterator<Move> getPossibleMoves(){
        _possibleMoves.clear();

        Board board = this.getCell().getBoard();
        
        board.calculateMoves(this, _possibleMoves, 1, 1, 1);
        board.calculateMoves(this, _possibleMoves, -1, 1, 1);
        board.calculateMoves(this, _possibleMoves, 1, -1, 1);
        board.calculateMoves(this, _possibleMoves, -1, -1, 1);
        board.calculateMoves(this, _possibleMoves, 1, 0, 1);
        board.calculateMoves(this, _possibleMoves, -1, 0, 1);
        board.calculateMoves(this, _possibleMoves, 0, 1, 1);
        board.calculateMoves(this, _possibleMoves, 0, -1, 1);

        Team team = getTeam();
        boolean canCastleKingSide = team.canCastleKingSide();
        boolean canCastleQueenSide = team.canCastleQueenSide();

        if (canCastleKingSide) {
            Move move = new Move(team.getCastlingKingSideCell());
            move.setIsCastleKingSide(true);
            _possibleMoves.add(move);
        }

        if (canCastleQueenSide) {
            Move move = new Move(team.getCastlingQueenSideCell());
            move.setIsCastleQueenSide(true);
            _possibleMoves.add(move);
        }

        return _possibleMoves.iterator();
    }

    @Override
    public void beforeMove(Cell oldCell, Cell newCell) {
        Team team = getTeam();
        Board board = this.getCell().getBoard();
        boolean canCastleKingSide = team.canCastleKingSide();
        boolean canCastleQueenSide = team.canCastleQueenSide();

        if (canCastleKingSide && newCell == team.getCastlingKingSideCell()) {
            Cell rookCell = board.getCell(newCell.getRow(), newCell.getCol() + 1);
            Piece rook = rookCell.getPiece();
            rook.move(board.getCell(newCell.getRow(), newCell.getCol() - 1));
        }

        if (canCastleQueenSide && newCell == team.getCastlingQueenSideCell()) {
            Cell rookCell = board.getCell(newCell.getRow(), newCell.getCol() - 2);
            Piece rook = rookCell.getPiece();
            rook.move(board.getCell(newCell.getRow(), newCell.getCol() + 1));
        }

        super.beforeMove(oldCell, newCell);
    }

    @Override
    public String toString() {
        if(getTeam().getColor().equals(Color.WHITE)) return "WKing";
        return "BKing";
    }

}
