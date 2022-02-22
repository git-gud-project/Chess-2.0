package com.chess.model;

public interface BoardInformation {
    public boolean isEmpty(Position position);
    public boolean isEmpty(int row, int col);

    public Identifier getTeamIdentifier(Position position);

    public Identifier getTypeIdentifier(Position position);

    public int getBoardSize();
}
