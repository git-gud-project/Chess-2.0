package com.chess.view;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

import com.chess.model.*;

/**
 * Creates a view for showing Forsyth–Edwards Notation (FEN) representation of the current game. This class extends class JPanel.
 */
public class BottomPanel extends JPanel {
    private JTextField infoLabel;
    private JButton copyButton;
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
        infoLabel.setForeground(ChessView.PRIMARY_SIDE_COLOR);
        infoLabel.setEditable(false);
        this.setLayout(new BorderLayout());
        this.setBackground(ChessView.BOARD_BACKGROUND_COLOR);
        this.add(infoLabel, BorderLayout.CENTER);

        copyButton = new JButton("Copy");
        copyButton.setFont(new Font("Arial", Font.BOLD, 10));
        copyButton.setForeground(ChessView.SECONDARY_SIDE_COLOR);
        copyButton.setBackground(ChessView.BOARD_BACKGROUND_COLOR);
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
        
        model.getOnTeamChangeEvent().addDelegate(team -> {
            updateInfoLabel();
        });

        model.getOnGameLoadedEvent().addDelegate(fen -> {
            infoLabel.setText(fen);
        });

        updateInfoLabel();
    }

    /** Update the text field with the current FEN representation.
     *
     */
    private void updateInfoLabel() {
        // Create a Forsyth–Edwards Notation (FEN) of the board
        String fen = model.toFEN();

        // Update the label with the FEN notation
        infoLabel.setText(fen);
    }
}