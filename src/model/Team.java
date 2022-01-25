package model;
import java.awt.*;

public class Team {

    private Color _teamColor;
    private String _fileSuffix;

    public Team(Color color, String fileSuffix) {
        _teamColor = color;
        _fileSuffix = fileSuffix;
    }

    public Color getColor() { 
        return _teamColor;
    }

    public String getFileSuffix() {
        return _fileSuffix;
    }
}
