package com.chess.view;

import com.chess.model.*;
import com.chess.utils.Event;

import javax.swing.*;
import java.awt.*;

public class InformationPanel extends JPanel {
    private final PlayerPanel playerPanel1;
    private final PlayerPanel playerPanel2;

    private final JButton pauseButton;

    private final BottomPanel bottomPanel;
    private final MovesPanel movesPanel;

    private final Event<JButton> onPauseButtonClickedEvent = new Event<>();

    /**
     * This is a side panel that displays the information of the game.
     *
     * On top are two panels, one for each player. These panels contain their
     * name and time.
     *
     * The rest of the panel is a list of moves.
     */
    public InformationPanel(ChessModel model) {

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
        c.gridx = 0;
        JSeparator separator = new JSeparator(JSeparator.VERTICAL);
        separator.setPreferredSize(new Dimension(10, 0));
        separator.setBackground(ChessView.BOARD_BACKGROUND_COLOR);
        separator.setForeground(ChessView.BOARD_BACKGROUND_COLOR);
        playerPanel.add(separator, c);
        c.gridx = 1;
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
        moves.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Add some margin to this panel
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel southContainer = new JPanel();
        southContainer.setLayout(new BorderLayout());
        this.add(southContainer, BorderLayout.SOUTH);

        // Pause button in the center
        pauseButton = new JButton("Pause");
        pauseButton.setPreferredSize(new Dimension(100, 50));
        pauseButton.setBackground(ChessView.PRIMARY_SIDE_COLOR);
        pauseButton.setForeground(ChessView.SECONDARY_COLOR);
        pauseButton.setFont(new Font("Arial", Font.BOLD, 20));
        pauseButton.setFocusPainted(false);
        southContainer.add(pauseButton, BorderLayout.NORTH);
        
        // Add the bottom panel
        bottomPanel = new BottomPanel(model);
        southContainer.add(bottomPanel, BorderLayout.SOUTH);

        //
        // Setup events
        //

        // Pause button
        pauseButton.addActionListener(e -> onPauseButtonClickedEvent.trigger(pauseButton));
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

    public JButton getPauseButton() {
        return pauseButton;
    }

    public Event<JButton> getOnPauseButtonClickedEvent() {
        return onPauseButtonClickedEvent;
    }
}
