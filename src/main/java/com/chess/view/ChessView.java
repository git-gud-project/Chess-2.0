package com.chess.view;

import com.chess.model.*;
import com.chess.model.chess.ChessModel;
import com.chess.model.chess.ChessTypeIdentifier;
import com.chess.utils.Resources;

import javax.swing.*;
import java.awt.*;


/**
 * View for a chess game.
 * @author Wincent Stålbert Holm
 * @version 2022-03-02
 */
public class ChessView extends JFrame {


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
     * A reference to the information panel which displays some useful information about the game that is currently being played.
     */
    private InformationPanel infoPanel;

    /**
     * A thread used for maintaining the aspect ratio when resizing the window containing the GUI for he game.
     */
    private Thread resizeThread;

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

        this.setTitle(ViewConstants.DEFAULT_TITLE);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setSize(new Dimension(ViewConstants.DEFAULT_WINDOW_WIDTH, ViewConstants.DEFAULT_WINDOW_HEIGHT));
        
        this.setResizable(ViewConstants.DEFAULT_RESIZABLE);

        this.setLayout(new BorderLayout());

        // On Resize
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                
                // In 100ms, ensure aspect ratio
                if (resizeThread != null) resizeThread.interrupt();
                resizeThread = new Thread(() -> {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        return;
                    }
                    int w = getWidth();
                    int h = getHeight();
                    if (w / h > ViewConstants.ASPECT_RATIO) {
                        setSize(new Dimension((int) (h * ViewConstants.ASPECT_RATIO), h));
                    } else {
                        setSize(new Dimension(w, (int) (w / ViewConstants.ASPECT_RATIO)));
                    }

                    if (w < ViewConstants.DEFAULT_WINDOW_WIDTH || h < ViewConstants.DEFAULT_WINDOW_HEIGHT) {
                        setSize(new Dimension(ViewConstants.DEFAULT_WINDOW_WIDTH, ViewConstants.DEFAULT_WINDOW_HEIGHT));
                    }
                });
                resizeThread.start();
            }
        });

        // Create board panel
        boardPanel = new BoardPanel(model, 8);

        this.add(boardPanel, BorderLayout.CENTER);

        // Add the information panel
        infoPanel = new InformationPanel(model);

        this.add(infoPanel, BorderLayout.EAST);

        // Add menu bar
        menuPanel = new Menu(model, infoPanel.getMovesPanel());

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

    /**
     * Shows a message in te GUI for a pre-set amount of time.
     * @param message The message to be shown on the GUI.
     */
    public void showMessage(String message) {
        // Create a new JPanel to display the message floating in the middle of the screen
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.BLACK);
        panel.setOpaque(true);

        // Create a new JLabel to display the message
        JLabel label = new JLabel(message);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);

        // Add the label to the panel
        panel.add(label, BorderLayout.CENTER);

        // Limit the size of the panel to the size of the message
        panel.setPreferredSize(new Dimension(label.getPreferredSize().width + 20, label.getPreferredSize().height + 20));

        // Add the panel to the frame
        this.add(panel, BorderLayout.CENTER);

        // After 2 seconds, remove the panel from the frame
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                return;
            }
            this.remove(panel);
            this.revalidate();
            this.repaint();
        }).start();
    }
}
