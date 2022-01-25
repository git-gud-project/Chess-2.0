package view;

import model.*;
import javax.swing.*;
import java.awt.*;

public class BoardGridPanel extends JPanel {
    private BoardCell[][] _board;
    private int _size;


    public BoardGridPanel(int size) {
        super(new GridLayout(size, size));
        _board = new BoardCell[size][size];
        _size = size;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                
                BoardCell button = new BoardCell();

                button.setMinimumSize(ChessView.CELL_MIN_SIZE);
                button.setPreferredSize(ChessView.CELL_IDEAL_SIZE);

                this.add(button);

                Color color = row % 2 == col % 2 ? ChessView.PRIMARY_COLOR : ChessView.SECONDARY_COLOR;

                // Set the background color of the button, balck or white
                button.setBackground(color);

                button.setHoverBackgroundColor(color);
                button.setPressedBackgroundColor(color);

                _board[row][col] = button;
            }
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
