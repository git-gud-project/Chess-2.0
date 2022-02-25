package com.chess.model.chess;
import java.awt.*;
import java.util.HashMap;

import com.chess.model.*;

import com.chess.utils.Event;

public class ChessTeam implements Team {

    //
    // Fields
    //

    private final Color teamColor;

    private String name;

    private GameTime time;

    private final Identifier teamIdentifier;

    private final ChessTeamParameters teamParameters;

    private boolean hasAuthority;

    private HashMap<Identifier, ChessSkinInfo> skinMap;

    private static final ChessTypeIdentifier[] orderedNames = {
        ChessTypeIdentifier.PAWN,
        ChessTypeIdentifier.ROOK,
        ChessTypeIdentifier.KNIGHT,
        ChessTypeIdentifier.BISHOP,
        ChessTypeIdentifier.QUEEN,
        ChessTypeIdentifier.KING
    };

    //
    // Events
    //

    private Event<String> onNameChangedEvent = new Event<>();

    private Event<GameTime> onTimeChangedEvent = new Event<>();

    private Event<Boolean> onAuthorityChangedEvent = new Event<>();

    //
    // Constructors
    //

    public ChessTeam(Identifier teamIdentifier, Color color, String name, GameTime time, ChessTeamParameters teamParameters) {
        this.teamColor = color;
        this.teamIdentifier = teamIdentifier;
        this.name = name;
        this.time = time.clone();
        this.hasAuthority = true;
        this.teamParameters = teamParameters;
        initHashMap();
    }

    public ChessTeam(Identifier teamIdentifier, Color color, String name, GameTime time, ChessTeamParameters teamParameters, HashMap<Identifier, ChessSkinInfo> skinMap) {
        this.teamColor = color;
        this.teamIdentifier = teamIdentifier;
        this.name = name;
        this.teamParameters = teamParameters;
        this.time = time.clone();
        this.skinMap = cloneHashMap(skinMap);
    }

    public ChessTeam clone() {
        return new ChessTeam(teamIdentifier, teamColor, name, time, teamParameters, skinMap);
    }

    //
    // Getters
    //

    public Color getColor() {
        return teamColor;
    }

    public Identifier getTeamIdentifier() {
        return teamIdentifier;
    }

    public String getName() {
        return name;
    }

    public GameTime getTime() {
        return time;
    }

    public boolean getHasAuthority() {
        return hasAuthority;
    }

    public ChessTeamParameters getTeamParameters() {
        return teamParameters;
    }

    //
    // Setters
    //

    public void setName(String name) {
        this.name = name;
        onNameChangedEvent.trigger(name);
    }

    public void setHasAuthority(boolean hasAuthority) {
        this.hasAuthority = hasAuthority;
        onAuthorityChangedEvent.trigger(hasAuthority);
    }

    public void setTime(GameTime time) {
        this.time = time;
        onTimeChangedEvent.trigger(time);
    }

    //
    // Getters - Events
    //

    public Event<String> getOnNameChangedEvent() {
        return onNameChangedEvent;
    }

    public Event<GameTime> getOnTimeChangedEvent() {
        return onTimeChangedEvent;
    }

    public Event<Boolean> getOnAuthorityChangedEvent() {
        return onAuthorityChangedEvent;
    }

    //
    // Utility
    //

    public void tickTime() {
        time.tick();
        onTimeChangedEvent.trigger(time);
    }

    /*
    //
    // Getters - Utility
    //

    public int getKingRow() {
        return pawnDirectionRow == 1 ? 7 : 0;
    }

    public int getKingCol() {
        return 4;
    }

    public int getPromotionRow() {
        return pawnDirectionRow == 1 ? 0 : 7;
    }

    //
    // SKIN - NOT TOUCHED
    //

    public int getPawnSkin() { return this.skinIndex[0]; }
    public int getRookSkin() { return this.skinIndex[1]; }
    public int getKnightSkin() { return this.skinIndex[2]; }
    public int getBishopSkin() { return this.skinIndex[3]; }
    public int getQueenSkin() { return this.skinIndex[4]; }
    public int getKingSkin() { return this.skinIndex[5]; }

    public Color getOpponentColor() {
        return new Color(255 - teamColor.getRed(), 255 - teamColor.getGreen(), 255 - teamColor.getBlue());
    }

    //
    // Setters - Utility
    //

    public void incPawnSkin() { this.skinIndex[0]++; }
    public void incRookSkin() { this.skinIndex[1]++; }
    public void incKnightSkin() { this.skinIndex[2]++; }
    public void incBishopSkin() { this.skinIndex[3]++; }
    public void incQueenSkin() { this.skinIndex[4]++; }
    public void incKingSkin() { this.skinIndex[5]++; }

    public void decPawnSkin() { this.skinIndex[0]--; }
    public void decRookSkin() { this.skinIndex[1]--; }
    public void decKnightSkin() { this.skinIndex[2]--; }
    public void decBishopSkin() { this.skinIndex[3]--; }
    public void decQueenSkin() { this.skinIndex[4]--; }
    public void decKingSkin() { this.skinIndex[5]--; }

    //
    // Methods
    //

    public void tickTime() {
        time.tick();
        onTimeChangedEvent.trigger(time);
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

    public String toString(){
        return this.name;
    }
*/
    private void initHashMap(){
        this.skinMap = new HashMap<>();
        if(this.teamColor.equals(Color.WHITE)){
            for (ChessTypeIdentifier orderedName : orderedNames) {
                this.skinMap.put(orderedName, new ChessSkinInfo(orderedName, ChessTeamIdentifier.WHITE));
            }
        } else {
            for(ChessTypeIdentifier orderedName : orderedNames ){
                this.skinMap.put(orderedName, new ChessSkinInfo(orderedName, ChessTeamIdentifier.BLACK));
            }
        }
    }

    public String getSkin(Identifier p){
        return this.skinMap.get(p).getSkinPath();
    }

    public void setSkin(Identifier p, String s){
        this.skinMap.get(p).setSkinPath(s);
    }

    public int getSkinIndex(Identifier p){
        return this.skinMap.get(p).getSkinIndex();
    }

    public void setSkinIndex(Identifier p, int newValue){
        this.skinMap.get(p).setSkinIndex(newValue);
    }

    public boolean getOwnSkin(Identifier p){
        return this.skinMap.get(p).getOwnSkin();
    }

    public void setOwnSkin(Identifier p, boolean newOwn){
        this.skinMap.get(p).setOwnSkin(newOwn);
    }

    private HashMap<Identifier, ChessSkinInfo> cloneHashMap(HashMap<Identifier, ChessSkinInfo> skinMap){
        HashMap<Identifier, ChessSkinInfo> clone = new HashMap<>();
        for(Identifier p : orderedNames){
            clone.put(p, skinMap.get(p));
        }
        return clone;
    }

    public void setSkinMap(HashMap<Identifier, ChessSkinInfo> skinMap) { this.skinMap = skinMap; }

    public HashMap<Identifier, ChessSkinInfo> getSkinMap() { return this.skinMap; }

}
