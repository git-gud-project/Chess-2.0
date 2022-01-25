package view;

import javax.swing.*;

import model.ChessModel;

import java.awt.*;

public class BoardPanel extends JPanel {
    private CellButton[][] _board;

    public final Dimension MIN_SIZE = new Dimension(60, 60);
    public final Dimension MAX_SIZE = new Dimension(80, 80);

    public void Update(ChessModel model) {
        
    }

    public BoardPanel(int size) {
        super(new GridLayout(size, size));
        _board = new CellButton[size][size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                        
                CellButton button = new CellButton();

                button.setMinimumSize(MIN_SIZE);
                button.setPreferredSize(MAX_SIZE);
                button.setIcon(new ImageIcon("res/kb.png"));

                this.add(button);

                Color color = row % 2 == col % 2 ? Color.WHITE : Color.BLACK;

                // Set the background color of the button, balck or white
                button.setBackground(color);

                button.setHoverBackgroundColor(color);
                button.setPressedBackgroundColor(color);

                _board[row][col] = button;
            }
        }
    }
}
