package com.chess.model;

public class Move {
    private Position moveCell, fromCell;
    private Identifier pieceType;
    private boolean elimination;
    private boolean isEnPassant;
    private boolean isCastleKingSide;
    private boolean isCastleQueenSide;
    private boolean isPromotion;
    private boolean isCheck;
    private boolean isCheckMate;

    /**
     * Constructor for Move with normal from cell to cell movement
     *
     * @param toCell The cell which the piece performing the move will be moved to
     * @param fromCell The cell which the piece performing the move moves away from
     */
    public Move(Position toCell, Position fromCell, Identifier pieceType) {
        this.moveCell = toCell;
        this.fromCell = fromCell;
        this.pieceType = pieceType;
        this.isCheck = false;
        this.isCheckMate = false;
    }

    /**
     * Constructor for a move with a capturing movement
     *
     * @param toCell The cell which the piece performing the move will be moved to
     * @param fromCell The cell which the piece performing the move moves away from
     * @param eliminatable Is true if move results in capture of another piece
     */
    public Move(Position toCell, Position fromCell, Identifier pieceType, boolean eliminatable) {
        this.moveCell = toCell;
        this.fromCell = fromCell;
        this.pieceType = pieceType;
        this.elimination = eliminatable;
        this.isCheck = false;
        this.isCheckMate = false;
    }

    /**
     * Constructor for a move that results in promotion
     *
     * @param toCell The cell which the piece performing the move will be moved to
     * @param type The piece type which the pawn is promoted to
     */
    public Move(Position toCell, Identifier promotionType) {
        this.moveCell = toCell;
        this.isPromotion = true;
        this.pieceType = promotionType;
        this.isCheck = false;
        this.isCheckMate = false;
    }

    /**
     * Get the cell that the piece is moving to
     * @return The cell which the piece is moving to
     */
    public Position getToCell() {
        return moveCell;
    }

    /**
     * Get the cell that the piece is moving from
     * @return The cell which the piece is moving away from
     */
    public Position getFromCell() {
        return fromCell;
    }

    /**
     * Get bool for if this move will result in a capture
     * @return True if move results in capture
     */
    public boolean isEliminatable() {
        return elimination;
    }

    /**
     * Get bool for if this move is En Passant
     * @return True if move is En Passant
     */
    public boolean getIsEnPassant() {
        return isEnPassant;
    }

    /**
     * Set this move to be En Passant
     * @param isEnPassant The value for En Passant
     */
    public void setIsEnPassant(boolean isEnPassant) {
        this.isEnPassant = isEnPassant;
    }

    /**
     * Get bool for if this move is castling on the kings side
     * @return True if this move is a castling move on the kings side
     */
    public boolean getIsCastleKingSide() {
        return isCastleKingSide;
    }

    /**
     * Indicate that this move is castling on the kings side
     * @param isCastleKingSide True if this move results in castling on the kings side
     */
    public void setIsCastleKingSide(boolean isCastleKingSide) {
        this.isCastleKingSide = isCastleKingSide;
    }

    /**
     * Get bool for if this move is castling on the queens side
     * @return True if this move is a castling move on the queens side
     */
    public boolean getIsCastleQueenSide() {
        return isCastleQueenSide;
    }

    /**
     * Indicate that this move is castling on the queens side
     * @param isCastleQueenSide True if this move results in castling on the queens side
     */
    public void setIsCastleQueenSide(boolean isCastleQueenSide) {
        this.isCastleQueenSide = isCastleQueenSide;
    }

    /**
     * Get the piece type that this move is being performed on
     * @return The piece type of the piece that this move is performed on
     */
    public Identifier getPieceType() {
        return this.pieceType;
    }

    /**
     * Indicate that this move results in check on other team
     */
    public void addCheck() {
        this.isCheck = true;
    }

    /**
     * Indicate that this move results in check mate on other team
     */
    public void addCheckMate() {
        this.isCheckMate = true;
    }

    /**
     * The toString of this class. Makes a string in the form of chess notation based on this move
     * @return A chess notation in String form
     */
    @Override
    public String toString() {
        if(isCastleKingSide) return "0-0";
        else if(isCastleQueenSide) return "0-0-0";
        else if(isPromotion) {
            String colAndRow = moveCell.toString();
            String piecePrefix = pieceType.toString();
            return colAndRow + piecePrefix;
        }
        else {
            StringBuilder result = new StringBuilder("");
            String colAndRow = moveCell.toString();
            String piecePrefix = pieceType.toString();
            if(piecePrefix.equals("p")) {
                if(elimination) {
                    if(isEnPassant) result.append(new Position(0, fromCell.getCol()).toString().charAt(0)+"x"+colAndRow + " e.p");
                    else result.append(new Position(0, fromCell.getCol()).toString().charAt(0)+"x"+colAndRow);
                }
                else result.append(colAndRow);
            }
            else if(elimination) {
                result.append(piecePrefix+"x"+colAndRow);
            }
            else result.append(piecePrefix+colAndRow);

            if(isCheck) result.append("+");
            else if(isCheckMate) result.append("#");

            return result.toString();
        }
    }
}
