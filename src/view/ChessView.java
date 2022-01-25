package view;
import javax.swing.*;
import java.awt.*;

public class ChessView extends JFrame {

    public final int DEFAULT_WINDOW_WIDTH = 600;
    public final int DEFAULT_WINDOW_HEIGHT = 600;
    public final boolean DEFAULT_RESIZABLE = false;
    public final String DEFAULT_TITLE = "Chess Game";

    private BoardPanel _boardPanel;
    
    public ChessView() {
        this.setTitle(DEFAULT_TITLE);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setSize(new Dimension(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT));
        
        this.setResizable(DEFAULT_RESIZABLE);

        this.setLayout(new BorderLayout());

        // Create board panel
        _boardPanel = new BoardPanel(8);

        this.add(_boardPanel, BorderLayout.CENTER);

        this.pack();

        this.setVisible(true);
    }
}
