package view;

import model.*;

import javax.swing.*;
import java.awt.*;

public class ChessView extends JFrame {

    public final static int DEFAULT_WINDOW_WIDTH = 1190;
    public final static int DEFAULT_WINDOW_HEIGHT = 773;
    public final static boolean DEFAULT_RESIZABLE = true;
    public final static String DEFAULT_TITLE = "Chess Game";

    public final static Dimension CELL_MIN_SIZE = new Dimension(60, 60);
    public final static Dimension CELL_IDEAL_SIZE = new Dimension(80, 80);

    public final static Dimension NUM_MIN_SIZE_H = new Dimension(60, 20);
    public final static Dimension NUM_IDEAL_SIZE_H = new Dimension(80, 40);

    public final static Dimension NUM_MIN_SIZE_V = new Dimension(20, 60);
    public final static Dimension NUM_IDEAL_SIZE_V = new Dimension(40, 80);

    public final static Dimension NUM_MIN_SIZE_C = new Dimension(20, 20);
    public final static Dimension NUM_IDEAL_SIZE_C = new Dimension(40, 40);

    public final static Color PRIMARY_COLOR = Color.GRAY;
    public final static Color SECONDARY_COLOR = Color.LIGHT_GRAY;

    public final static Color PRIMARY_SIDE_COLOR = Color.DARK_GRAY.brighter();
    public final static Color SECONDARY_SIDE_COLOR = Color.GRAY.brighter();

    public final static Color BOARD_BACKGROUND_COLOR = Color.BLACK;

    public final static Color HIGHLIGHT_COLOR_MOVE = Color.GREEN;
    public final static Color HIGHLIGHT_COLOR_ATTACK = Color.RED;
    public final static Color HIGHLIGHT_COLOR_PIECE = Color.YELLOW;
    
    public final static float HIGHLIGHT_ALPHA = 0.5f;

    public final static float ASPECT_RATIO = (float) DEFAULT_WINDOW_WIDTH / DEFAULT_WINDOW_HEIGHT;

    private Menu _menuPanel;

    private BoardPanel _boardPanel;

    private ChessModel _model;

    private InformationPanel _infoPanel;

    private java.lang.Thread _thread;
    
    public ChessView(ChessModel model) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            // This may fail without any consequences
        }

        Toolkit.getDefaultToolkit().setDynamicLayout(false);
        

        this.setTitle(DEFAULT_TITLE);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setSize(new Dimension(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT));
        
        this.setResizable(DEFAULT_RESIZABLE);

        this.setLayout(new BorderLayout());

        // On Resize
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                // Make sure the left mouse button is not pressed
                if (evt.getComponent().getMousePosition() != null) {
                    if (evt.getComponent().getMousePosition().getX() > 0) {
                        return;
                    }
                }
                
                // In 100ms, ensure aspect ratio
                if (_thread != null) _thread.interrupt();
                _thread = new Thread(() -> {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        return;
                    }
                    int w = getWidth();
                    int h = getHeight();
                    if (w / h > ASPECT_RATIO) {
                        setSize(new Dimension((int) (h * ASPECT_RATIO), h));
                    } else {
                        setSize(new Dimension(w, (int) (w / ASPECT_RATIO)));
                    }

                    if (w < DEFAULT_WINDOW_WIDTH || h < DEFAULT_WINDOW_HEIGHT) {
                        setSize(new Dimension(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT));
                    }
                });
                _thread.start();
            }
        });

        // Create board panel
        _boardPanel = new BoardPanel(this, 8);

        this.add(_boardPanel, BorderLayout.CENTER);

        // Add the information panel
        _infoPanel = new InformationPanel();

        this.add(_infoPanel, BorderLayout.EAST);

        // Add menu bar
        _menuPanel = new Menu(this);

        this.setJMenuBar(_menuPanel);

        this.pack();

        this.setVisible(true);

        _model = model;

        updateModel();
    }

    public Menu getMenu() {
        return _menuPanel;
    }

    public ChessModel getModel() {
        return _model;
    }
    
    public BoardPanel getBoardPanel() {
        return _boardPanel;
    }

    public InformationPanel getInfoPanel() {
        return _infoPanel;
    }

    public BoardGridPanel getBoardGridPanel() {
        return _boardPanel.getBoardGridPanel();
    }

    public void setModel(ChessModel model) { this._model = model; }
    
    public void updateModel() {
        _boardPanel.updateModel(_model);
        _infoPanel.updateModel(_model);
    }
}
