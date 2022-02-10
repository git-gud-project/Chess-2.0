package com.chess.view;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

import com.chess.model.*;

public class BottomPanel extends JPanel {
    private JTextField infoLabel;
    private JButton copyButton;
    private ChessModel model;

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

        /**
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

    private void updateInfoLabel() {
        // Create a Forsyth–Edwards Notation (FEN) of the board
        String fen = model.toFEN();

        // Update the label with the FEN notation
        infoLabel.setText(fen);
    }
}
