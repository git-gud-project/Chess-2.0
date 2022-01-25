package model;

public class ChessModel {

    private static final int GAMESIZE = 8;

    private Cell[][] _cellArray;

    public ChessModel() {
        initCellArray();
    }

    private void initCellArray() {
        _cellArray = new Cell[GAMESIZE][GAMESIZE];
        for(int i = 0; i < GAMESIZE; i++){
            for(int j = 0; j < GAMESIZE; j++){
                _cellArray[i][j] = new Cell(i,j);
            }
        }
    }

    public Cell getCell(int x, int y) { return _cellArray[x][y]; }

}