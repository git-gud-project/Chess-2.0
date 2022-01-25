package model;

public class Board {

    private Cell[][] _cellArray;

    private ChessModel _model;
    private int _gameSize;

    public Board(ChessModel model, int gameSize){
        this._model = model;
        this._gameSize = gameSize;
        initCellArray(gameSize);
    }

    private void initCellArray(int gameSize) {
        _cellArray = new Cell[gameSize][gameSize];
        for(int i = 0; i < gameSize; i++){
            for(int j = 0; j < gameSize; j++){
                _cellArray[i][j] = new Cell(this, i, j);
            }
        }
    }

    public Cell getCell(int x, int y) { return _cellArray[x][y]; }

    public ChessModel getChessModel() { return this._model; }

    public int getGameSize() { return this._gameSize; }
}
