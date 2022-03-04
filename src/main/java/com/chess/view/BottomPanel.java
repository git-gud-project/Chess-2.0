package com.chess.view;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

import com.chess.model.chess.ChessModel;

/**
 * Creates a view for showing Forsyth–Edwards Notation (FEN) representation of the current game. This class extends class JPanel.
 * @author Wincent Stålbert Holm
 * @version 2022-02-03
 */
public class BottomPanel extends JPanel {
    /**
     * the text field containing the FEN string representing the state of the game.
     */
    private JTextField infoLabel;
    /**
     * The button that can be used to pause/start the game.
     */
    private JButton copyButton;
    /**
     * A reference to the model containing the information about the state of the game.
     */
    private ChessModel model;

    /** Creates a panel with a JLabel field to show the FEN representation and a button used to copy the FEN string
     * @param model The model/current game to be represented in the notation.
     */
    public BottomPanel(ChessModel model) {
        super();

        this.model = model;

        infoLabel = new JTextField();
        infoLabel.setText("");
        infoLabel.setFont(new Font("Arial", Font.BOLD, 8));
        infoLabel.setForeground(ViewConstants.PRIMARY_SIDE_COLOR);
        infoLabel.setEditable(false);
        this.setLayout(new BorderLayout());
        this.setBackground(ViewConstants.BOARD_BACKGROUND_COLOR);
        this.add(infoLabel, BorderLayout.CENTER);

        copyButton = new JButton("Copy");
        copyButton.setFont(new Font("Arial", Font.BOLD, 10));
        copyButton.setForeground(ViewConstants.SECONDARY_SIDE_COLOR);
        copyButton.setBackground(ViewConstants.BOARD_BACKGROUND_COLOR);
        this.add(copyButton, BorderLayout.EAST);

        // When the button is pressed, copy the text to the clipboard
        copyButton.addActionListener((e) -> {
            StringSelection selection = new StringSelection(infoLabel.getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
        });

        /*
         * Setup events
         */
        
        model.getOnTeamChangeEvent().addDelegate(team ->
            updateInfoLabel()
        );

        model.getOnGameLoadedEvent().addDelegate(fen ->
            infoLabel.setText(fen)
        );

        updateInfoLabel();
    }

    /**
     * Update the text field with the current FEN representation.
     */
    private void updateInfoLabel() {
        // Create a Forsyth–Edwards Notation (FEN) of the board
        String fen = model.toFEN();

        // Update the label with the FEN notation
        infoLabel.setText(fen);
    }
}
