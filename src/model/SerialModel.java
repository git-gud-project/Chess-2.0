package model;

import java.io.Serializable;
import java.util.*;

public class SerialModel implements Serializable {
    private List<String> moveList;
    private String fen;
    private String whiteName, blackName;
    private Time whiteTime, blackTime;

    public SerialModel(ChessModel model) {
        moveList = model.getMoveList();
        fen = model.toFEN();
        whiteName = model.getTeamWhite().getName();
        blackName = model.getTeamBlack().getName();
        whiteTime = model.getTeamWhite().getTime();
        blackTime = model.getTeamBlack().getTime();
    }

    public List<String> getMoveList() {
        return moveList;
    }

    public String getFen() {
        return fen;
    }

    public String getWhiteName() {
        return whiteName;
    }
    
    public String getBlackName() {
        return blackName;
    }
    
    public Time getWhiteTime() {
        return whiteTime;
    }
    
    public Time getBlackTime() {
        return blackTime;
    }
}
