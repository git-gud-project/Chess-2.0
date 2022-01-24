public class Cell {
    private final int _xPos;
    private final int _yPos;
    private int _weatherEffect; //Datatype can be changed later.

    public Cell(int x, int y){
        this._xPos = x;
        this._yPos = y;
    }

    public int getWeatherEffect() {
        return _weatherEffect;
    }

    public int getxPos() {
        return _xPos;
    }

    public int getyPos(){
        return _yPos;
    }

    public void setWeatherEffect(int weatherEffect){
        this._weatherEffect = weatherEffect;
    }

}

