package view;

import model.*;

import javax.swing.*;
import java.awt.*;

public class ChessView extends JFrame {

    public final static int DEFAULT_WINDOW_WIDTH = 600;
    public final static int DEFAULT_WINDOW_HEIGHT = 600;
    public final static boolean DEFAULT_RESIZABLE = false;
    public final static String DEFAULT_TITLE = "Chess Game";

    public final static Dimension CELL_MIN_SIZE = new Dimension(60, 60);
    public final static Dimension CELL_IDEAL_SIZE = new Dimension(80, 80);

    public final static Dimension NUM_MIN_SIZE_H = new Dimension(60, 20);
    public final static Dimension NUM_IDEAL_SIZE_H = new Dimension(80, 40);

    public final static Dimension NUM_MIN_SIZE_V = new Dimension(20, 60);
    public final static Dimension NUM_IDEAL_SIZE_V = new Dimension(40, 80);

    public final static Color PRIMARY_COLOR = Color.DARK_GRAY;
    public final static Color SECONDARY_COLOR = Color.GRAY;

    public final static Color PRIMARY_SIDE_COLOR = Color.GRAY;
    public final static Color SECONDARY_SIDE_COLOR = Color.LIGHT_GRAY;

    public final static Color BOARD_BACKGROUND_COLOR = Color.BLACK;

    public final static Color HIGHLIGHT_COLOR_MOVE = Color.GREEN;
    public final static Color HIGHLIGHT_COLOR_ATTACK = Color.RED;
    public final static Color HIGHLIGHT_COLOR_PIECE = Color.YELLOW;
    
    public final static float HIGHLIGHT_ALPHA = 0.5f;

    private BoardPanel _boardPanel;

    private ChessModel _model;
    
    public ChessView(ChessModel model) {
        this.setTitle(DEFAULT_TITLE);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setSize(new Dimension(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT));
        
        this.setResizable(DEFAULT_RESIZABLE);

        this.setLayout(new BorderLayout());

        // Create board panel
        _boardPanel = new BoardPanel(this, 8);

        this.add(_boardPanel, BorderLayout.CENTER);

        this.pack();

        this.setVisible(true);

        _model = model;

        updateModel();
    }

    public ChessModel getModel() {
        return _model;
    }
    
    public void updateModel() {
        _boardPanel.updateModel(_model);
    }
}
