package view;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {
    public BoardPanel() {
        super(new GridLayout(8, 8));

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                        
                CellButton button = new CellButton();

                button.setMinimumSize(new Dimension(60, 60));
                button.setPreferredSize(new Dimension(80, 80));

                this.add(button);

                Color color = row % 2 == col % 2 ? Color.WHITE : Color.BLACK;

                // Set the background color of the button, balck or white
                button.setBackground(color);

                button.setHoverBackgroundColor(color);
                button.setPressedBackgroundColor(color);
            }
        }
    }
}
