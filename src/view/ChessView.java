package view;
import javax.swing.*;
import java.awt.*;

public class ChessView extends JFrame {
    
    public ChessView() {
        super("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(600, 600));

        // Create a grid, with 8 rows and 8 columns
        JPanel grid = new JPanel(new GridLayout(8, 8));

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                        
                CellButton button = new CellButton();

                grid.add(button);

                Color color = row % 2 == col % 2 ? Color.WHITE : Color.BLACK;

                // Set the background color of the button, balck or white
                button.setBackground(color);

                button.setHoverBackgroundColor(color);
                button.setPressedBackgroundColor(color);
            }
        }

        add(grid);

        setVisible(true);
    }
}
