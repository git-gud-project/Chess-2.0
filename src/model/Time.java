package model;

public class Time {

    private int _minutes, _seconds, _mseconds;

    public Time() {
        this._minutes = 5;
        this._seconds = 0;
        this._mseconds = 0;
    }

    public Time(int minutes) {
        this._minutes = minutes;
        this._seconds = 0;
        this._mseconds = 0;
    }

    public int getMinutes() { return this._minutes; }
    public int getSeconds() { return this._seconds; }
    public int getMseconds() { return this._mseconds; }

    public void tick() {
        if(_minutes > 0 || _seconds > 0 ||_mseconds > 0) {
            _mseconds--;
            if (_mseconds == -1) {
                _mseconds = 9;
                _seconds--;
            }
            if (_seconds == -1) {
                _seconds = 59;
                _minutes--;
            }
        }
    }

    public void setTime(int minutes, int seconds, int mseconds){
        this._minutes = minutes;
        this._seconds = seconds;
        this._mseconds = mseconds;
    }

    public void reset() {
        this._minutes = 5;
        this._seconds = 0;
        this._mseconds = 0;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d", _minutes, _seconds, _mseconds);
    }
}
