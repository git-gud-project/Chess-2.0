package model;

public class Board {
    private Cell[][] _board;

    public Board(){
        for(int y = 1; y <= 8; y++){
            for(int x = 1; x <= 8; x++){
                _board[y][x] = new Cell(x, y);
            }
        }
    }
}
