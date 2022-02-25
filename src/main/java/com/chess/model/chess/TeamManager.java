package com.chess.model.chess;

import java.util.HashMap;

import com.chess.model.Identifier;

public class TeamManager {

    public static final Identifier WHITE = ChessIdentifier.WHITE;
    public static final Identifier BLACK = ChessIdentifier.BLACK;

    private final HashMap<Identifier, ChessTeam> teamMap;

    private Identifier currentTeamIdentifier;

    public TeamManager(ChessTeam white, ChessTeam black) {

        teamMap = new HashMap<>();
        teamMap.put(WHITE, white);
        teamMap.put(BLACK, black);

        currentTeamIdentifier = WHITE;
    }

    /**
     * Get a team by its identifier.
     * 
     * @param teamIdentifier The identifier of the team to get.
     * @return The team with the specified identifier.
     * @throws IllegalArgumentException If the team identifier is invalid.
     */
    public ChessTeam getTeam(Identifier teamIdentifier) throws IllegalArgumentException {
        if (!teamMap.containsKey(teamIdentifier)) {
            throw new IllegalArgumentException("Invalid team identifier: " + teamIdentifier);
        }

        return teamMap.get(teamIdentifier);
    }

    /**
     * Get the team parameters for a team.
     * 
     * @param teamIdentifier The identifier of the team to get the parameters for.
     * @return The team parameters for the team with the specified identifier.
     * @throws IllegalArgumentException If no team exists with the specified identifier.
     */
    public ChessTeamParameters getTeamParameters(Identifier teamIdentifier) throws IllegalArgumentException {
        ChessTeam team = getTeam(teamIdentifier);

        return team.getTeamParameters();
    }

    /**
     * Get the current team.
     * 
     * @return the current team
     */
    public Identifier getCurrentTeamIdentifier() {
        return currentTeamIdentifier;
    }

    /**
     * Set the current team.
     * 
     * @param teamIdentifier The identifier of the team to set as the current team.
     */
    public void setCurrentTeamIdentifier(Identifier teamIdentifier) {
        currentTeamIdentifier = teamIdentifier;
    }

    /**
     * Gets the other team identifier based on a given team identifier.
     * 
     * @param teamIdentifier The identifier of the team to get the other team of.
     * @return The identifier of the other team.
     * @throws IllegalArgumentException If the team identifier is invalid.
     */
    public Identifier getOtherTeamIdentifier(Identifier teamIdentifier) throws IllegalArgumentException {
        if (teamIdentifier.equals(WHITE)) {
            return BLACK;
        } else if (teamIdentifier.equals(BLACK)) {
            return WHITE;
        } else {
            throw new IllegalArgumentException("Invalid team identifier: " + teamIdentifier);
        }
    }

    /**
     * Switch the current team.
     */
    public void switchCurrentTeam() {
        currentTeamIdentifier = getOtherTeamIdentifier(currentTeamIdentifier);
    }
}
