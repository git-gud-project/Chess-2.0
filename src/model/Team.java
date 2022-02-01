package model;
import java.awt.*;

import utils.Event;

public class Team {

    //
    // Fields
    //

    private ChessModel _model;

    private Color _teamColor;

    private String _fileSuffix;

    private String _name;

    private Time _time;

    private int _pawnDirectionRow;

    private Piece _enPassantPiece;

    // 
    // Events
    //

    private Event<String> _onNameChangedEvent = new Event<>();

    private Event<Time> _onTimeChangedEvent = new Event<>();

    //
    // Constructors
    //

    public Team(ChessModel model, Color color, String fileSuffix, String name, int pawnDirectionRow) {
        _model = model;
        _teamColor = color;
        _fileSuffix = fileSuffix;
        _name = name;
        _time = new Time();
        _pawnDirectionRow = pawnDirectionRow;
    }

    //
    // Getters
    //

    public Color getColor() { 
        return _teamColor;
    }

    public String getFileSuffix() {
        return _fileSuffix;
    }

    public String getName() {
        return _name;
    }

    public Time getTime() {
        return _time;
    }

    public int getPawnDirectionRow() {
        return _pawnDirectionRow;
    }

    public int getEnPassantRow() {
        return _enPassantPiece.getCell().getRow() - _pawnDirectionRow;
    }

    public int getEnPassantCol() {
        return _enPassantPiece.getCell().getCol();
    }

    public Piece getEnPassantPiece() {
        return _enPassantPiece;
    }

    public int getKingRow() {
        return _pawnDirectionRow == -1 ? 7 : 0;
    }

    public int getKingCol() {
        return 4;
    }

    public int getPromotionRow() {
        return _pawnDirectionRow == -1 ? 0 : 7;
    }

    //
    // Getters - Events
    //

    public Event<String> getOnNameChangedEvent() {
        return _onNameChangedEvent;
    }

    public Event<Time> getOnTimeChangedEvent() {
        return _onTimeChangedEvent;
    }

    //
    // Methods
    //

    public void tickTime() {
        _time.tick();
        _onTimeChangedEvent.invoke(_time);
    }

    //
    // En Passant
    //

    public boolean isEnPassant(int row, int col) {
        return _enPassantPiece != null && getEnPassantRow() == row && getEnPassantCol() == col;
    }

    public void setEnPassant(Piece piece) {
        _enPassantPiece = piece;
    }

    public void clearEnPassant() {
        _enPassantPiece = null;
    }

    //
    // Castling
    //

    public boolean hasCastlingRightKingSide() {
        Board board = _model.getBoard();
        Piece king = board.getCell(getKingRow(), getKingCol()).getPiece();
        Piece rook = board.getCell(getKingRow(), getKingCol() + 3).getPiece();

        if (king == null || rook == null || king.getTeam() != this || rook.getTeam() != this || !(king instanceof PieceKing) || !(rook instanceof PieceRook)) {
            return false;
        }

        return !king.hasMoved() && !rook.hasMoved();
    }

    public void setHasCastlingRightKingSide(boolean hasCastlingRightKingSide) {
        Board board = _model.getBoard();
        Piece king = board.getCell(getKingRow(), getKingCol()).getPiece();
        Piece rook = board.getCell(getKingRow(), getKingCol() + 3).getPiece();

        if (king == null || rook == null || king.getTeam() != this || rook.getTeam() != this || !(king instanceof PieceKing) || !(rook instanceof PieceRook)) {
            return;
        }

        king.setHasMoved(!hasCastlingRightKingSide);
        rook.setHasMoved(!hasCastlingRightKingSide);
    }

    public boolean hasCastlingRightQueenSide() {
        Board board = _model.getBoard();
        Piece king = board.getCell(getKingRow(), getKingCol()).getPiece();
        Piece rook = board.getCell(getKingRow(), getKingCol() - 4).getPiece();

        if (king == null || rook == null || king.getTeam() != this || rook.getTeam() != this || !(king instanceof PieceKing) || !(rook instanceof PieceRook)) {
            return false;
        }

        return !king.hasMoved() && !rook.hasMoved();
    }

    public void setHasCastlingRightQueenSide(boolean hasCastlingRightQueenSide) {
        Board board = _model.getBoard();
        Piece king = board.getCell(getKingRow(), getKingCol()).getPiece();
        Piece rook = board.getCell(getKingRow(), getKingCol() - 4).getPiece();

        if (king == null || rook == null || king.getTeam() != this || rook.getTeam() != this || !(king instanceof PieceKing) || !(rook instanceof PieceRook)) {
            return;
        }

        king.setHasMoved(!hasCastlingRightQueenSide);
        rook.setHasMoved(!hasCastlingRightQueenSide);
    }

    public boolean canCastleKingSide() {
        if (!hasCastlingRightKingSide()) {
            return false;
        }

        for (int i = getKingCol() + 1; i < getKingCol() + 3; i++) {
            if (_model.getBoard().getCell(getKingRow(), i).getPiece() != null) {
                return false;
            }
        }

        return true;
    }

    public boolean canCastleQueenSide() {
        if (!hasCastlingRightQueenSide()) {
            return false;
        }

        for (int i = getKingCol() - 1; i > getKingCol() - 4; i--) {
            if (_model.getBoard().getCell(getKingRow(), i).getPiece() != null) {
                return false;
            }
        }

        return true;
    }

    public Cell getCastlingKingSideCell() {
        return _model.getBoard().getCell(getKingRow(), getKingCol() + 2);
    }

    public Cell getCastlingQueenSideCell() {
        return _model.getBoard().getCell(getKingRow(), getKingCol() - 2);
    }
}
