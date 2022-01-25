package view;
import javax.swing.*;
import java.awt.*;

public class ChessView extends JFrame {
    
    public ChessView() {
        super("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(600, 600));

        GridBagLayout layout = new GridBagLayout();
        
        JPanel outer = new JPanel();

        // Create a grid, with 8 rows and 8 columns
        JPanel grid = new BoardPanel();

        add(grid);

        setVisible(true);
    }
}
