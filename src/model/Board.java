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
        for(int row = 0; row < gameSize; row++){
            for(int col = 0; col < gameSize; col++){
                _cellArray[row][col] = new Cell(this, row, col);
            }
        }
    }

    public Cell getCell(int row, int col) {
        return _cellArray[row][col];
    }

    public ChessModel getChessModel() { return this._model; }

    public int getGameSize() { return this._gameSize; }

    public boolean isEmpty(int row, int col) {
        return (_cellArray[row][col].getPiece() == null);
    }

    public boolean isValid(int row, int col) {
        return !(row > _gameSize || col > _gameSize);
    }

}
