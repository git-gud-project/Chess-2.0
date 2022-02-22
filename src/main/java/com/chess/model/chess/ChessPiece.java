package com.chess.model.chess;

import javax.swing.*;

import com.chess.model.*;

import java.awt.*;
import java.util.Iterator;

/**
 * Class for all chess pieces.
 */
public class ChessPiece implements Piece {

    /**
     * The type of the piece.
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
        int n;
        switch(behavior.getTypeIdentifier().toString()){
            case "r": n = 1; break;
            case "n": n = 2; break;
            case "b": n = 3; break;
            case "q": n = 4; break;
            case "k": n = 5; break;
            default: n = 0;
        }
        if (!team.getOwnSkin(n)) {
            if (team.getSkinIndex(n) == 0) {
                return "/images/" + team.getSkin(behavior.getTypeIdentifier());
            } else {
                return "/skins/" + team.getSkin(getPieceType());
            }
        } else {
            //TODO: Behaves a bit mysteriously if a file is deleted or moved in between pop-ups. Could try to fix this some way.
            return team.getSkin(getPieceType());
        }
    }

    /**
     * Gets all possible moves for this piece.
     * 
     * @return all possible moves for this piece
     */
    public Iterator<Move> getPossibleMoves(Rule rule, Position position) {
        return behavior.getPossibleMoves(rule, team.getTeamIdentifier(), position);
    }
}
