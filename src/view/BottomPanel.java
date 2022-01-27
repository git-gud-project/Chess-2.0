package view;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

import model.*;

public class BottomPanel extends JPanel {
    private JTextField _infoLabel;
    private JButton _copyButton;

    public BottomPanel() {
        _infoLabel = new JTextField();
        _infoLabel.setText("");
        _infoLabel.setFont(new Font("Arial", Font.BOLD, 8));
        _infoLabel.setForeground(ChessView.PRIMARY_SIDE_COLOR);
        _infoLabel.setEditable(false);
        this.setLayout(new BorderLayout());
        this.setBackground(ChessView.BOARD_BACKGROUND_COLOR);
        this.add(_infoLabel, BorderLayout.CENTER);

        _copyButton = new JButton("Copy");
        _copyButton.setFont(new Font("Arial", Font.BOLD, 10));
        _copyButton.setForeground(ChessView.SECONDARY_SIDE_COLOR);
        _copyButton.setBackground(ChessView.BOARD_BACKGROUND_COLOR);
        this.add(_copyButton, BorderLayout.EAST);

        // When the button is pressed, copy the text to the clipboard
        _copyButton.addActionListener((e) -> {
            StringSelection selection = new StringSelection(_infoLabel.getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
        });
    }

    public void updateModel(ChessModel model) {
        // Create a notation of the current board state
        Board board = model.getBoard();

        // Create a Forsyth–Edwards Notation (FEN) of the board
        String fen = board.toFEN();

        // Update the label with the FEN notation
        _infoLabel.setText(fen);
    }
}
