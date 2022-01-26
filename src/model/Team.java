package model;
import java.awt.*;

public class Team {

    private Color _teamColor;
    private String _fileSuffix;
    private String _name;
    private float _time;

    public Team(Color color, String fileSuffix, String name, float time) {
        _teamColor = color;
        _fileSuffix = fileSuffix;
        _name = name;
        _time = time;
    }

    public Color getColor() { 
        return _teamColor;
    }

    public String getFileSuffix() {
        return _fileSuffix;
    }

    public String getName() {
        return _name;
    }

    public float getTime() {
        return _time;
    }
}
