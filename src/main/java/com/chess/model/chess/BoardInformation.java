package com.chess.model.chess;


import com.chess.model.Identifier;
import com.chess.model.MovesCalculator;
import com.chess.model.Position;

public interface BoardInformation {
    public boolean isEmpty(Position position);

    public boolean isEmpty(int row, int col);

    public boolean isValid(Position position);

    public Identifier getTeamIdentifier(Position position);

    public Identifier getTeamIdentifier(int row, int col);

    public Identifier getTypeIdentifier(Position position);

    public Identifier getTypeIdentifier(int row, int col);

    public int getBoardSize();

    public void setPiece(Position position, Identifier piece, Identifier team, boolean isFinalMove);

    public void clearPiece(Position position, boolean isFinalMove);

    public boolean isElimination(Position position, Identifier piece, Identifier team);

    public MovesCalculator getPossibleMovesIterator(Position position);
}
