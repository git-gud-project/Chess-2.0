package model;
import java.awt.*;

import utils.Event;

public class Team {

    //
    // Fields
    //

    private ChessModel model;

    private Color teamColor;

    private String fileSuffix;

    private String name;

    private Time time;

    private int pawnDirectionRow;

    private Piece enPassantPiece;

    // 
    // Events
    //

    private Event<String> onNameChangedEvent = new Event<>();

    private Event<Time> onTimeChangedEvent = new Event<>();

    //
    // Constructors
    //

    public Team(ChessModel model, Color color, String fileSuffix, String name, int pawnDirectionRow) {
        this.model = model;
        this.teamColor = color;
        this.fileSuffix = fileSuffix;
        this.name = name;
        this.time = new Time();
        this.pawnDirectionRow = pawnDirectionRow;
    }

    //
    // Getters
    //
    
    public ChessModel getModel() {
        return model;
    }

    public Color getColor() { 
        return teamColor;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public String getName() {
        return name;
    }

    public Time getTime() {
        return time;
    }

    public int getPawnDirectionRow() {
        return pawnDirectionRow;
    }

    public int getEnPassantRow() {
        return enPassantPiece.getCell().getRow() - pawnDirectionRow;
    }

    public int getEnPassantCol() {
        return enPassantPiece.getCell().getCol();
    }

    public Piece getEnPassantPiece() {
        return enPassantPiece;
    }

    public int getKingRow() {
        return pawnDirectionRow == -1 ? 7 : 0;
    }

    public int getKingCol() {
        return 4;
    }

    public int getPromotionRow() {
        return pawnDirectionRow == -1 ? 0 : 7;
    }

    //
    // Setters
    //

    public void setName(String name) {
        this.name = name;
        onNameChangedEvent.invoke(name);
    }

    //
    // Getters - Events
    //

    public Event<String> getOnNameChangedEvent() {
        return onNameChangedEvent;
    }

    public Event<Time> getOnTimeChangedEvent() {
        return onTimeChangedEvent;
    }

    //
    // Methods
    //

    public void tickTime() {
        time.tick();
        onTimeChangedEvent.invoke(time);
    }

    //
    // En Passant
    //

    public boolean isEnPassant(int row, int col) {
        return enPassantPiece != null && getEnPassantRow() == row && getEnPassantCol() == col;
    }

    public void setEnPassant(Piece piece) {
        enPassantPiece = piece;
    }

    public void clearEnPassant() {
        enPassantPiece = null;
    }

    //
    // Castling
    //

    public boolean hasCastlingRightKingSide() {
        Board board = model.getBoard();
        Piece king = board.getCell(getKingRow(), getKingCol()).getPiece();
        Piece rook = board.getCell(getKingRow(), getKingCol() + 3).getPiece();

        if (king == null || rook == null || king.getTeam() != this || rook.getTeam() != this || !(king.getPieceType() == PieceType.KING) || !(rook.getPieceType() == PieceType.ROOK)) {
            return false;
        }

        return !king.hasMoved() && !rook.hasMoved();
    }

    public void setHasCastlingRightKingSide(boolean hasCastlingRightKingSide) {
        Board board = model.getBoard();
        Piece king = board.getCell(getKingRow(), getKingCol()).getPiece();
        Piece rook = board.getCell(getKingRow(), getKingCol() + 3).getPiece();

        if (king == null || rook == null || king.getTeam() != this || rook.getTeam() != this || !(king.getPieceType() == PieceType.KING) || !(rook.getPieceType() == PieceType.ROOK)) {
            return;
        }

        king.setHasMoved(!hasCastlingRightKingSide);
        rook.setHasMoved(!hasCastlingRightKingSide);
    }

    public boolean hasCastlingRightQueenSide() {
        Board board = model.getBoard();
        Piece king = board.getCell(getKingRow(), getKingCol()).getPiece();
        Piece rook = board.getCell(getKingRow(), getKingCol() - 4).getPiece();

        if (king == null || rook == null || king.getTeam() != this || rook.getTeam() != this || !(king.getPieceType() == PieceType.KING) || !(rook.getPieceType() == PieceType.ROOK)) {
            return false;
        }

        return !king.hasMoved() && !rook.hasMoved();
    }

    public void setHasCastlingRightQueenSide(boolean hasCastlingRightQueenSide) {
        Board board = model.getBoard();
        Piece king = board.getCell(getKingRow(), getKingCol()).getPiece();
        Piece rook = board.getCell(getKingRow(), getKingCol() - 4).getPiece();

        if (king == null || rook == null || king.getTeam() != this || rook.getTeam() != this || !(king.getPieceType() == PieceType.KING) || !(rook.getPieceType() == PieceType.ROOK)) {
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
            if (model.getBoard().getCell(getKingRow(), i).getPiece() != null) {
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
            if (model.getBoard().getCell(getKingRow(), i).getPiece() != null) {
                return false;
            }
        }

        return true;
    }

    public Cell getCastlingKingSideCell() {
        return model.getBoard().getCell(getKingRow(), getKingCol() + 2);
    }

    public Cell getCastlingQueenSideCell() {
        return model.getBoard().getCell(getKingRow(), getKingCol() - 2);
    }
}
