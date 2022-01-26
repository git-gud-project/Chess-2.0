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
        return ((row < _gameSize && row >= 0) && (col < _gameSize && col >= 0));
    }

    public String toFEN() {
        /**
         * https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation
         */
        String fen = "";
        for(int row = 0; row < _gameSize; row++){
            int emptyCells = 0;
            for(int col = 0; col < _gameSize; col++){
                if(_cellArray[row][col].getPiece() == null){
                    emptyCells++;
                }
                else{
                    if(emptyCells > 0){
                        fen += emptyCells;
                        emptyCells = 0;
                    }
                    Piece piece = _cellArray[row][col].getPiece();
                    String character = piece.getPieceType().getFilePrefix();
                    if (piece.getTeam() == _model.getTeamWhite()) {
                        character = character.toUpperCase();
                    }
                    fen += character;
                }
            }
            if(emptyCells > 0){
                fen += emptyCells;
            }
            if(row != _gameSize - 1){
                fen += "/";
            }
        }
        // TODO: Add castling, en passant, and halfmove clock
        return fen;
    }

}
