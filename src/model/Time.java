package model;

public class Time {

    private int _minutes, _seconds, _mseconds;

    public Time() {
        _minutes = 5;
        _seconds = 0;
        _mseconds = 0;
    }

    public Time(int minutes) {
        this._minutes = minutes;
        _seconds = 0;
        _mseconds = 0;
    }

    public int getMinutes() { return _minutes; }
    public int getSeconds() { return _seconds; }
    public int getMseconds() { return _mseconds; }

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

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d", _minutes, _seconds, _mseconds);
    }
}
