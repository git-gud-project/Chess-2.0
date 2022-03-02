package com.chess.model.chess;

import java.util.HashMap;

import com.chess.model.Identifier;

/**
 * The team manager for chess.
 */
public class TeamManager {
    /**
     * Maps each team identifier to an instance of chess team to keep track of the teams playing the game.
     */
    private final HashMap<Identifier, ChessTeam> teamMap;

    /**
     * Keeps track of the team whose turn it is currently.
     */
    private Identifier currentTeamIdentifier;

    /**
     * Constructor for TeamManager.
     * @param white The instance of chess team corresponding to the team playing with the white pieces. This team starts playing.
     * @param black The instance of chess team corresponding to the team playing with the black pieces.
     */
    public TeamManager(ChessTeam white, ChessTeam black) {

        teamMap = new HashMap<>();
        teamMap.put(white.getTeamIdentifier(), white);
        teamMap.put(black.getTeamIdentifier(), black);

        currentTeamIdentifier = white.getTeamIdentifier();
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
        if (teamIdentifier.equals(ChessTeamIdentifier.WHITE)) {
            return ChessTeamIdentifier.BLACK;
        } else if (teamIdentifier.equals(ChessTeamIdentifier.BLACK)) {
            return ChessTeamIdentifier.WHITE;
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


    /**
     * Changes the values of the instance of ChessTeam stored in the TeamManager for the given identifier.
     * @param teamIdentifier The identifier of the team whose parameters will be changed.
     * @param team The new instance of team containing the updated values for said team.
     */
    public void setTeam(Identifier teamIdentifier, ChessTeam team){
        teamMap.put(teamIdentifier, team);
    }
}
