package view;

import javax.swing.*;
import java.awt.*;
import model.ChessModel;

public class BoardPanel extends JPanel {
    private BoardGridPanel _boardPanel;

    public BoardPanel(ChessView view, int size) {
        CellButton button;

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        this.setBackground(ChessView.BOARD_BACKGROUND_COLOR);

        // Top
        for (int i = 0; i < size; ++i) {
            button = new CellButton(String.valueOf((char) (i + 'A')));
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = i + 1;
            c.gridy = 0;
            button.setColorAll((i % 2 == 0 ? ChessView.PRIMARY_SIDE_COLOR : ChessView.SECONDARY_SIDE_COLOR));
            this.add(button, c);
            button.setMinimumSize(ChessView.NUM_MIN_SIZE_H);
            button.setPreferredSize(ChessView.NUM_IDEAL_SIZE_H);
        }

        // Left
        for (int i = 0; i < size; ++i) {
            button = new CellButton(Integer.toString(size - i));
            c.fill = GridBagConstraints.VERTICAL;
            c.gridx = 0;
            c.gridy = i + 1;
            button.setColorAll((i % 2 == 0 ? ChessView.PRIMARY_SIDE_COLOR : ChessView.SECONDARY_SIDE_COLOR));
            this.add(button, c);
            button.setMinimumSize(ChessView.NUM_MIN_SIZE_V);
            button.setPreferredSize(ChessView.NUM_IDEAL_SIZE_V);
        }
        
        // Bottom
        for (int i = 0; i < size; ++i) {
            button = new CellButton(String.valueOf((char) (i + 'A')));
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = i + 1;
            c.gridy = size + 1;
            button.setColorAll((i % 2 == 1 ? ChessView.PRIMARY_SIDE_COLOR : ChessView.SECONDARY_SIDE_COLOR));
            this.add(button, c);
            button.setMinimumSize(ChessView.NUM_MIN_SIZE_H);
            button.setPreferredSize(ChessView.NUM_IDEAL_SIZE_H);
        }

        // Right
        for (int i = 0; i < size; ++i) {
            button = new CellButton(Integer.toString(size - i));
            c.fill = GridBagConstraints.VERTICAL;
            c.gridx = size + 1;
            c.gridy = i + 1;
            button.setColorAll((i % 2 == 1 ? ChessView.PRIMARY_SIDE_COLOR : ChessView.SECONDARY_SIDE_COLOR));
            this.add(button, c);
            button.setMinimumSize(ChessView.NUM_MIN_SIZE_V);
            button.setPreferredSize(ChessView.NUM_IDEAL_SIZE_V);
        }

        // Add to the corners
        {
            button = new CellButton("");
            c.fill = GridBagConstraints.BOTH;
            c.gridx = 0;
            c.gridy = 0;
            button.setColorAll(ChessView.SECONDARY_SIDE_COLOR);
            this.add(button, c);
            button.setMinimumSize(ChessView.NUM_MIN_SIZE_C);
            button.setPreferredSize(ChessView.NUM_IDEAL_SIZE_C);
        }
        {
            button = new CellButton("");
            c.fill = GridBagConstraints.BOTH;
            c.gridx = size + 1;
            c.gridy = 0;
            button.setColorAll(ChessView.PRIMARY_SIDE_COLOR);
            this.add(button, c);
            button.setMinimumSize(ChessView.NUM_MIN_SIZE_C);
            button.setPreferredSize(ChessView.NUM_IDEAL_SIZE_C);
        }
        {
            button = new CellButton("");
            c.fill = GridBagConstraints.BOTH;
            c.gridx = 0;
            c.gridy = size + 1;
            button.setColorAll(ChessView.PRIMARY_SIDE_COLOR);
            this.add(button, c);
            button.setMinimumSize(ChessView.NUM_MIN_SIZE_C);
            button.setPreferredSize(ChessView.NUM_IDEAL_SIZE_C);
        }
        {
            button = new CellButton("");
            c.fill = GridBagConstraints.BOTH;
            c.gridx = size + 1;
            c.gridy = size + 1;
            button.setColorAll(ChessView.SECONDARY_SIDE_COLOR);
            this.add(button, c);
            button.setMinimumSize(ChessView.NUM_MIN_SIZE_C);
            button.setPreferredSize(ChessView.NUM_IDEAL_SIZE_C);
        }

        _boardPanel = new BoardGridPanel(view, size);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.0;
        c.gridwidth = size;
        c.gridheight = size;
        c.gridx = 1;
        c.gridy = 1;
        this.add(_boardPanel, c);
    }

    public BoardGridPanel getBoardGridPanel() {
        return _boardPanel;
    }

    public void updateModel(ChessModel model) {
        _boardPanel.updateModel(model);
    }
}
