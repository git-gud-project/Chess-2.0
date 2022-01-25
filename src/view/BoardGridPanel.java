package view;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class BoardGridPanel extends JPanel {
    private BoardCell[][] _board;
    private int _size;
    private ChessView _view;

    private ArrayList<BoardCell> _highlightedCells;

    public BoardGridPanel(ChessView view, int size) {
        super(new GridLayout(size, size));
        _board = new BoardCell[size][size];
        _size = size;
        _view = view;
        _highlightedCells = new ArrayList<BoardCell>();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                
                BoardCell button = new BoardCell(row, col);

                button.setMinimumSize(ChessView.CELL_MIN_SIZE);
                button.setPreferredSize(ChessView.CELL_IDEAL_SIZE);

                this.add(button);

                button.addActionListener((e) -> {
                    handleClick(button);
                });

                Color color = row % 2 == col % 2 ? ChessView.PRIMARY_COLOR : ChessView.SECONDARY_COLOR;

                // Set the background color of the button, balck or white
                button.setBackground(color);

                button.setHoverBackgroundColor(color);
                button.setPressedBackgroundColor(color);

                _board[row][col] = button;
            }
        }
    }

    private void handleClick(BoardCell boardCell) {
        for (BoardCell cell : _highlightedCells) {
            cell.unhighlight();
        }
        _highlightedCells.clear();

        ChessModel model = _view.getModel();

        Piece piece = model.getBoard().getCell(boardCell.getRow(), boardCell.getCol()).getPiece();

        if (piece == null) {
            return;
        }

        Iterator<Move> moves = piece.getPossibleMoves();
        while (moves.hasNext()) {
            Move move = moves.next();

            Cell cell = move.getCell();

            BoardCell possibleMove = _board[cell.getRow()][cell.getCol()];

            possibleMove.highlight();
            _highlightedCells.add(possibleMove);
        }
    }

    public void updateModel(ChessModel m) {
        model.Board b= m.getBoard();

        for(int row=0;row<_size;row++){
            for(int col=0;col<_size;col++){
                Piece p = b.getCell(row,col).getPiece();
               if(p == null){
                   _board[row][col].setIcon(null);
               }
               else{
                   _board[row][col].setIcon(new ImageIcon("res/"+p.getPieceType().getFilePrefix()+p.getTeam().getFileSuffix()+".png"));
               }
            }
        }
    }
}
