package com.chess.model;
import java.awt.*;
import java.util.HashMap;

import com.chess.model.chess.ChessTeamParameters;
import com.chess.model.chess.PieceType;
import com.chess.utils.Event;

public class ChessTeam implements TeamInterface{

    //
    // Fields
    //

    private Color teamColor;

    private String name;

    private Time time;

    private Identifier teamIdentifier;

    private ChessTeam enemyTeam = null;

    private ChessTeamParameters teamParameters;

    private int pawnDirectionRow;

    private Piece enPassantPiece;

    private boolean hasAuthority;

    private HashMap<Identifier, String> skinMap;

    private boolean[] ownSkin = {false, false, false, false, false, false};
    private int[] skinIndex = {0, 0, 0, 0, 0, 0};

    private static final Identifier[] orderedNames = {PieceType.PAWN, PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP, PieceType.QUEEN, PieceType.KING};
    private static final String[] whiteNames = {"pw.png", "rw.png", "nw.png", "bw.png", "qw.png", "kw.png"};
    private static final String[] blackNames = {"pb.png", "rb.png", "nb.png", "bb.png", "qb.png", "kb.png"};

    //
    // Events
    //

    private Event<String> onNameChangedEvent = new Event<>();

    private Event<Time> onTimeChangedEvent = new Event<>();

    private Event<Boolean> onAuthorityChangedEvent = new Event<>();

    //
    // Constructors
    //

    public ChessTeam(Identifier teamIdentifier, Color color, String name, int pawnDirectionRow, ChessTeam enemyTeam) {
        this.teamColor = color;
        this.teamIdentifier = teamIdentifier;
        this.name = name;
        this.time = new Time(5);
        this.pawnDirectionRow = pawnDirectionRow;
        this.hasAuthority = true;
        this.enemyTeam = enemyTeam;
        initHashMap();
    }

    public ChessTeam(Color color, String teamIdentifier, String name, Time time, int pawnDirectionRow, Piece enPassantPiece, boolean hasAuthority, HashMap<Identifier, String> skinMap, boolean[] ownSkin, int[] skinIndex){
        this.teamColor = color;
        this.teamIdentifier = new Identifier(teamIdentifier);
        this.name = name;
        this.time = time.cloneTime();
        this.pawnDirectionRow = pawnDirectionRow;
        this.enPassantPiece = enPassantPiece;
        this.hasAuthority = hasAuthority;
        this.skinMap = cloneHashMap(skinMap);
        this.ownSkin = ownSkin.clone();
        this.skinIndex = skinIndex.clone();
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

    public Time getTime() {
        return time;
    }

    public int getPawnDirectionRow() {
        return pawnDirectionRow;
    } //Moved to ChessTeamParameters

    public Piece getEnPassantPiece() {
        return enPassantPiece;
    } //Moved to ChessTeamParameters

    public boolean getHasAuthority() {
        return hasAuthority;
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

    public void setTime(Time time) {
        this.time = time;
        onTimeChangedEvent.trigger(time);
    }

    public void setEnemyTeam(ChessTeam enemyTeam){
        this.enemyTeam = enemyTeam;
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

    public Event<Boolean> getOnAuthorityChangedEvent() {
        return onAuthorityChangedEvent;
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
            for(int i = 0; i < orderedNames.length; i++){
                this.skinMap.put(orderedNames[i], whiteNames[i]);
            }
        } else {
            for(int i = 0; i < orderedNames.length; i++){
                this.skinMap.put(orderedNames[i], blackNames[i]);
            }
        }
    }

    public String getSkin(Identifier p){
        return this.skinMap.get(p);
    }

    public void setSkin(Identifier p, String s){
        this.skinMap.put(p, s);
    }

    public int getSkinIndex(int i){
        return this.skinIndex[i];
    }

    public void setSkinIndex(int index, int newValue){
        this.skinIndex[index] = newValue;
    }

    public boolean getOwnSkin(int i){
        return this.ownSkin[i];
    }

    public void setOwnSkin(int i, boolean b){
        this.ownSkin[i] = b;
    }

    private HashMap<Identifier, String> cloneHashMap(HashMap<Identifier, String> skinMap){
        HashMap<Identifier, String> clone = new HashMap<>();
        for(Identifier p : orderedNames){
            clone.put(p, skinMap.get(p));
        }
        return clone;
    }

    public ChessTeam cloneTeam(){
        return new ChessTeam(this.teamColor, this.teamIdentifier.toString(), this.name, this.time, this.pawnDirectionRow, this.enPassantPiece, this.hasAuthority, this.skinMap, this.ownSkin, this.skinIndex);
    }

    public void setSkinMap(HashMap<Identifier, String> skinMap) { this.skinMap = skinMap; }
    public void setOwnSkin(boolean[] ownSkin) { this.ownSkin = ownSkin; }
    public void setSkinIndex(int[] skinIndex) { this.skinIndex = skinIndex; }

    public HashMap<Identifier, String> getSkinMap() { return this.skinMap; }
    public boolean[] getOwnSkin() { return this.ownSkin; }
    public int[] getSkinIndex() { return this.skinIndex; }

}
