package view;

import model.ChessModel;

import javax.swing.*;
import java.awt.*;

public class BoardGridPanel extends JPanel {
    private BoardCell[][] _board;

    public void Update(ChessModel model) {
        
    }

    public BoardGridPanel(int size) {
        super(new GridLayout(size, size));
        _board = new BoardCell[size][size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                
                BoardCell button = new BoardCell();

                button.setMinimumSize(ChessView.CELL_MIN_SIZE);
                button.setPreferredSize(ChessView.CELL_IDEAL_SIZE);
                button.setIcon(new ImageIcon("res/kb.png"));

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
}
