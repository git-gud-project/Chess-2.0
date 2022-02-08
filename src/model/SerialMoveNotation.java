package model;

import java.awt.*;
import java.io.Serializable;

public class SerialMoveNotation implements Serializable {

    private int _row,_col, _fromCol;
    private String _team, _piece;
    private boolean _enPassant, _promotion, _eliminates;

    public SerialMoveNotation(Move mN) {
        /*
        this._row = mN.getRow();
        this._col = mN.getCol();
        this._fromCol = mN.getFromCol();
        this._enPassant = mN.getEnPassant();
        this._promotion = mN.getPromotion();
        this._eliminates = mN.getEliminates();
        this._team = mN.getPiece().getTeam().getColor().equals(Color.WHITE) ? "white" : "black";
        if(mN.getPiece() instanceof PiecePawn){
            this._piece = "pawn";
        } else if (mN.getPiece() instanceof PieceRook){
            this._piece = "rook";
        } else if (mN.getPiece() instanceof PieceBishop){
            this._piece = "bishop";
        } else if (mN.getPiece() instanceof PieceKnight){
            this._piece = "knight";
        } else if (mN.getPiece() instanceof PieceQueen){
            this._piece = "queen";
        } else if (mN.getPiece() instanceof PieceKing){
            this._piece = "king";
        }
        */
    }

    public int getRow() { return this._row; }
    public int getCol() { return this._col; }
    public int getFromCol() { return this._fromCol; }
    public String getTeam() { return this._team; }
    public String getPiece() {return this._piece; }
    public boolean getEnPassant() { return this._enPassant; }
    public boolean getPromotion() { return this._promotion; }
    public boolean getEliminates() { return this._promotion; }


}
