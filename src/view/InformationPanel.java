package view;

import model.*;

import javax.swing.*;
import java.awt.*;

public class InformationPanel extends JPanel {
    private PlayerPanel playerPanel1;
    private PlayerPanel playerPanel2;

    private BottomPanel bottomPanel;
    private MovesPanel movesPanel;

    public InformationPanel(ChessModel model) {
        /**
         * This is a side panel that displays the information of the game.
         * 
         * On top are two panels, one for each player. These panels contain their
         * name and time.
         * 
         * The rest of the panel is a list of moves.
         */
        this.setLayout(new BorderLayout());
        this.setBackground(ChessView.SECONDARY_COLOR);

        JPanel playerPanel = new JPanel();
        playerPanel1 = new PlayerPanel(model.getTeamWhite());
        playerPanel2 = new PlayerPanel(model.getTeamBlack());
        JPanel moves = new JPanel();

        // Player 1 in north west, player 2 in north east, with a separator in the middle
        playerPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        playerPanel.add(playerPanel1, c);
        c.gridx = 1;
        JSeparator separator = new JSeparator(JSeparator.VERTICAL);
        separator.setPreferredSize(new Dimension(10, 0));
        separator.setBackground(ChessView.BOARD_BACKGROUND_COLOR);
        separator.setForeground(ChessView.BOARD_BACKGROUND_COLOR);
        playerPanel.add(separator, c);
        c.gridx = 2;
        playerPanel.add(playerPanel2, c);
        this.add(playerPanel, BorderLayout.NORTH);

        playerPanel.setBackground(ChessView.SECONDARY_COLOR);

        // Add some margin to the player panels
        playerPanel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        playerPanel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Moves in center
        this.add(moves, BorderLayout.CENTER);

        // Add the inner panels to the list of moves
        moves.setLayout(new GridLayout(0, 1));
        moves.setBackground(ChessView.SECONDARY_COLOR);
        
        // Create a panel to place the moves in
        movesPanel = new MovesPanel(model);
        moves.add(movesPanel);

        // Add a border to the moves panel
        moves.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add some margin to this panel
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add the bottom panel
        bottomPanel = new BottomPanel(model);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    public BottomPanel getBottomPanel() {
        return bottomPanel;
    }

    public MovesPanel getMovesPanel() {
        return movesPanel;
    }

    public PlayerPanel getPlayerPanel1() {
        return playerPanel1;
    }

    public PlayerPanel getPlayerPanel2() {
        return playerPanel2;
    }
}
