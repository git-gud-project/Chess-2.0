package com.chess.model;

import java.util.HashMap;

public class TeamManager {

    private HashMap<Identifier, ChessTeam> teamMap;

    public TeamManager(ChessTeam white, ChessTeam black) {
        Identifier whiteIdentifier = new Identifier("white");
        Identifier blackIdentifier = new Identifier("black");

        teamMap = new HashMap<>();
        teamMap.put(whiteIdentifier, white);
        teamMap.put(blackIdentifier, black);
    }

    public ChessTeam getTeam(Identifier teamIdentifier) {
        return teamMap.get(teamIdentifier);
    }

}
