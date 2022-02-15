package com.chess.view;

import com.chess.model.*;

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

    private Menu menuPanel;

    private BoardPanel boardPanel;

    private ChessModel model;

    private InformationPanel infoPanel;

    private Thread thread;
    
    public ChessView(ChessModel model) {
        this.model = model;

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
                if (thread != null) thread.interrupt();
                thread = new Thread(() -> {
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
                thread.start();
            }
        });

        // Create board panel
        boardPanel = new BoardPanel(this, 8);

        this.add(boardPanel, BorderLayout.CENTER);

        // Add the information panel
        infoPanel = new InformationPanel(model);

        this.add(infoPanel, BorderLayout.EAST);

        // Add menu bar
        menuPanel = new Menu(this);

        this.setJMenuBar(menuPanel);

        this.pack();

        this.setVisible(true);
    }

    public Menu getMenu() {
        return menuPanel;
    }

    public ChessModel getModel() {
        return model;
    }
    
    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public InformationPanel getInfoPanel() {
        return infoPanel;
    }

    public BoardGridPanel getBoardGridPanel() {
        return boardPanel.getBoardGridPanel();
    }

    public void setModel(ChessModel model) { this.model = model; }

    public PieceType promotePawn() {
        // Create a dialog to ask the user what piece to promote to
        Object[] options = {"Queen", "Rook", "Bishop", "Knight"};
        int n = JOptionPane.showOptionDialog(this, "What piece do you want to promote to?", "Promote Pawn", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (n == JOptionPane.CLOSED_OPTION) {
            return null;
        }
        switch (n) {
            case 0:
                return PieceType.QUEEN;
            case 1:
                return PieceType.ROOK;
            case 2:
                return PieceType.BISHOP;
            case 3:
                return PieceType.KNIGHT;
            default:
                return null;
        }
    }

    public String getSoundMap() {
        return menuPanel.getSoundMap();
    }

}
