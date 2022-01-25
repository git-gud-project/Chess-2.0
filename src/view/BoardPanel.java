package view;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {
    public BoardPanel(int size) {
        CellButton button;

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Top
        for (int i = 0; i < size; ++i) {
            button = new CellButton(String.valueOf((char) (i + 'A')));
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = i + 1;
            c.gridy = 0;
            this.add(button, c);
            button.setMinimumSize(new Dimension(60, 20));
            button.setPreferredSize(new Dimension(80, 40));
        }

        // Left
        for (int i = 0; i < size; ++i) {
            button = new CellButton(Integer.toString(size - i));
            c.fill = GridBagConstraints.VERTICAL;
            c.gridx = 0;
            c.gridy = i + 1;
            this.add(button, c);
            button.setMinimumSize(new Dimension(20, 60));
            button.setPreferredSize(new Dimension(40, 80));
        }
        
        // Bottom
        for (int i = 0; i < size; ++i) {
            button = new CellButton(String.valueOf((char) (i + 'A')));
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = i + 1;
            c.gridy = size + 1;
            this.add(button, c);
            button.setMinimumSize(new Dimension(60, 20));
            button.setPreferredSize(new Dimension(80, 40));
        }

        // Right
        for (int i = 0; i < size; ++i) {
            button = new CellButton(Integer.toString(size - i));
            c.fill = GridBagConstraints.VERTICAL;
            c.gridx = size + 1;
            c.gridy = i + 1;
            this.add(button, c);
            button.setMinimumSize(new Dimension(20, 60));
            button.setPreferredSize(new Dimension(40, 80));
        }

        JPanel grid = new BoardGridPanel(size);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.0;
        c.gridwidth = 8;
        c.gridheight = 8;
        c.gridx = 1;
        c.gridy = 1;
        this.add(grid, c);
    }
}
