package com.chess.view;

import com.chess.model.*;
import com.chess.model.chess.ChessModel;
import com.chess.model.chess.ChessTypeIdentifier;
import com.chess.utils.Resources;

import javax.swing.*;
import java.awt.*;

/**
 * View for a chess game.
 */
public class ChessView extends JFrame {

    /**
     * The default value for the width of the window containing the GUI for the game.
     */
    public final static int DEFAULT_WINDOW_WIDTH = 1190;
    /**
     * The default value for the height of the window containing the GUI for the game.
     */
    public final static int DEFAULT_WINDOW_HEIGHT = 773;
    /**
     * The default mode for the ability to resize the window containing the GUI for the game.
     */
    public final static boolean DEFAULT_RESIZABLE = true;
    /**
     * The default title for the window containing the GUI for the game.
     */
    public final static String DEFAULT_TITLE = "Chess 2.0";

    /**
     * The minimum size a cell on the board can take up.
     */
    public final static Dimension CELL_MIN_SIZE = new Dimension(60, 60);
    /**
     * The ideal size for the cells displayed on the board.
     */
    public final static Dimension CELL_IDEAL_SIZE = new Dimension(80, 80);

    /**
     * The minimum size for the cells containing column indexes.
     */
    public final static Dimension NUM_MIN_SIZE_H = new Dimension(60, 20);
    /**
     * The ideal size for the cells containing column indexes.
     */
    public final static Dimension NUM_IDEAL_SIZE_H = new Dimension(80, 40);

    /**
     * The minimum size for the cells containing row indexes.
     */
    public final static Dimension NUM_MIN_SIZE_V = new Dimension(20, 60);
    /**
     * The ideal size for the cells containing row indexes.
     */
    public final static Dimension NUM_IDEAL_SIZE_V = new Dimension(40, 80);

    /**
     * The minimum size for the cells situated at the corner of the board.
     */
    public final static Dimension NUM_MIN_SIZE_C = new Dimension(20, 20);
    /**
     * The ideal size for the cells situated at the corner of the board.
     */
    public final static Dimension NUM_IDEAL_SIZE_C = new Dimension(40, 40);

    /**
     * The primary color for the outer cells of the board
     */
    public final static Color PRIMARY_COLOR = Color.GRAY;
    /**
     * The alternating color for the outer cells of the board.
     */
    public final static Color SECONDARY_COLOR = Color.LIGHT_GRAY;

    /**
     * The primary color for the inner cells of the board.
     */
    public final static Color PRIMARY_SIDE_COLOR = Color.DARK_GRAY.brighter();
    /**
     * The alternating color for the inner cells of the board.
     */
    public final static Color SECONDARY_SIDE_COLOR = Color.GRAY.brighter();

    /**
     * The background color for the board.
     */
    public final static Color BOARD_BACKGROUND_COLOR = Color.BLACK;

    /**
     * The color used to highlight cells to which a selected piece can move.
     */
    public final static Color HIGHLIGHT_COLOR_MOVE = Color.GREEN;
    /**
     * The color used to highlight cells which a selected piece kan attack.
     */
    public final static Color HIGHLIGHT_COLOR_ATTACK = Color.RED;
    /**
     * The color used to hihglight a cell containing a selected piece.
     */
    public final static Color HIGHLIGHT_COLOR_PIECE = Color.YELLOW;

    /**
     * Modification parameter used to scale certain RGB values for highlighting.
     */
    public final static float HIGHLIGHT_ALPHA = 0.5f;

    /**
     * The aspect ratio value used when resizing the game.
     */
    public final static float ASPECT_RATIO = (float) DEFAULT_WINDOW_WIDTH / DEFAULT_WINDOW_HEIGHT;

    /**
     * A reference to the game model containing all the information about the state of the game.
     */
    private ChessModel model;

    /**
     * A reference to the menu panel shown at the top of the window containing the GUI for the game.
     */
    private Menu menuPanel;

    /**
     * A reference to the board panel representing board on which the game is played.
     */
    private BoardPanel boardPanel;

    /**
     * A reference to the inormation panel which displays some useful information about the game that is currently being played.
     */
    private InformationPanel infoPanel;

    /**
     * A thread used for maintaining the aspect ratio when resizing the window containing the GUI for he game.
     */
    private Thread thread;

    /**
     * Constructor for ChessView.
     * @param model A reference to the model containing the information about the game state.
     */
    public ChessView(ChessModel model) {
        this.model = model;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            // This may fail without any consequences
        }

        Toolkit.getDefaultToolkit().setDynamicLayout(false);
        
        this.setIconImage(Resources.getImage("/images/icon.png"));

        this.setTitle(DEFAULT_TITLE);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setSize(new Dimension(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT));
        
        this.setResizable(DEFAULT_RESIZABLE);

        this.setLayout(new BorderLayout());

        // On Resize
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                /*
                // Make sure the left mouse button is not pressed
                if (evt.getComponent().getMousePosition() != null) {
                    if (evt.getComponent().getMousePosition().getX() > 0) {
                        return;
                    }
                }
                */
                
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
        boardPanel = new BoardPanel(model, 8);

        this.add(boardPanel, BorderLayout.CENTER);

        // Add the information panel
        infoPanel = new InformationPanel(model);

        this.add(infoPanel, BorderLayout.EAST);

        // Add menu bar
        menuPanel = new Menu(model);

        this.setJMenuBar(menuPanel);

        this.pack();

        this.setVisible(true);
    }

    /**
     * Gets the reference to the chess model containing the information about the game state.
     * @return A reference to the chess model containing the information about the game state.
     */
    public ChessModel getModel() {
        return model;
    }

    /**
     * Gets the reference to the menu panel displayed at the top of the window containing the GUI for the chess game.
     * @return reference to the menu panel displayed at the top of the window containing the GUI for the chess game.
     */
    public Menu getMenu() {
        return menuPanel;
    }

    /**
     * Gets the reference to the board panel which represents the grid on which the game is played.
     * @return A reference to the board panel which represents the grid on which the game is played.
     */
    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    /**
     * Gets the reference to the information panel displaying certain aspects of the game state.
     * @return A reference to the information panel displaying certain aspects of the game state.
     */
    public InformationPanel getInfoPanel() {
        return infoPanel;
    }

    /**
     * Gets a reference to the board grid panel containing the grid of cells represented by the chess board.
     * @return A reference to the board grid panel containing the grid of cells represented by the chess board.
     */
    public BoardGridPanel getBoardGridPanel() {
        return boardPanel.getBoardGridPanel();
    }

    /**
     * Sets a new instance of the model which stores the information about the game state.
     * @param model The new instance of the model containing the information about a new game state.
     */
    public void setModel(ChessModel model) { this.model = model; }

    /**
     * Handles the pop-up dialogue to be shown when a pawn is promoted to a different piece.
     * @return The Identifier for the type of piece chosen by the user to promote their pawn to.
     */
    public Identifier promotePawn() {
        // Create a dialog to ask the user what piece to promote to
        Object[] options = {"Queen", "Rook", "Bishop", "Knight"};
        int n = JOptionPane.showOptionDialog(this, "What piece do you want to promote to?", "Promote Pawn", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (n == JOptionPane.CLOSED_OPTION) {
            return null;
        }
        switch (n) {
            case 0:
                return ChessTypeIdentifier.QUEEN;
            case 1:
                return ChessTypeIdentifier.ROOK;
            case 2:
                return ChessTypeIdentifier.BISHOP;
            case 3:
                return ChessTypeIdentifier.KNIGHT;
            default:
                return null;
        }
    }

    /**
     * Gets the reference to the String representing what sound map is currently selected.
     * @return A reference to the String representing what sound map is currently selected.
     */
    public String getSoundMap() {
        return menuPanel.getSoundMap();
    }
}
