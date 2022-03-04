package com.chess.model.chess;

import com.chess.model.*;

import java.util.Iterator;

/**
 * Class for all chess pieces.
 * @author Marcus Phu
 * @version 2022-03-02
 */
public class ChessPiece implements Piece {

    /**
     * The behavior of this piece.
     */
    private final PieceBehavior behavior;

    /**
     * The team that the piece belongs to.
     */
    private final ChessTeam team;

    /**
     * Constructs a new piece.
     *
     * @param behavior the PieceBehavior for this new piece
     * @param team the team that the piece belongs to
     */
    public ChessPiece(PieceBehavior behavior, ChessTeam team) {
        this.behavior = behavior;
        this.team = team;
    }

    /**
     * Returns the type of the piece.
     *
     * @return the type of the piece
     */
    public Identifier getTeamIdentifier() {
        return team.getTeamIdentifier();
    }

    /**
     * Returns the type of the piece.
     *
     * @return the type of the piece
     */
    public Identifier getTypeIdentifier() {
        return behavior.getTypeIdentifier();
    }

    /**
     * Get the path to the image file for the piece.
     * 
     * @return the path to the image file for the piece
     */
    public String getIconPath() {
        if (!team.getOwnSkin(getTypeIdentifier())) {
            if (team.getSkinIndex(getTypeIdentifier()) == 0) {
                return "/images/" + team.getSkin(getTypeIdentifier());
            } else {
                return "/skins/" + team.getSkin(getTypeIdentifier());
            }
        } else {
            return team.getSkin(getTypeIdentifier());
        }
    }

    @Override
    public Iterator<Move> getPossibleMoves(Rule rule, Position position) {
        // Forwards the call to the PieceBehavior.
        return behavior.getPossibleMoves(rule, position, getTeamIdentifier());
    }

    @Override
    public void afterMove(Rule rule, Position from, Position to) {
        // Forwards the call to the PieceBehavior.
        behavior.afterMove(rule, from, to);
    }

    @Override
    public void beforeMove(Rule rule, Position from, Position to) {
        // Forwards the call to the PieceBehavior.
        behavior.beforeMove(rule, from, to);
    }
}
