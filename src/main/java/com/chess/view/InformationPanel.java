package com.chess.view;

import com.chess.model.chess.ChessModel;
import com.chess.utils.Event;

import javax.swing.*;
import java.awt.*;

/**
 * A class to represent a side panel that displays the information of the game.
 * On top are two panels, one for each player. These panels contain their
 * name and time.
 * The rest of the panel is a list of moves.
 */
public class InformationPanel extends JPanel {
    /**
     * A reference to the PlayerPanel corresponding to player1
     */
    private final PlayerPanel playerPanel1;
    /**
     * A reference to the PlayerPanel corresponding to player2.
     */
    private final PlayerPanel playerPanel2;

    /**
     * A reference to button used for pausing/starting the game.
     */
    private final JButton pauseButton;

    /**
     * A reference to the BottomPanel showing the FEN notation representing the current state of the game.
     */
    private final BottomPanel bottomPanel;
    /**
     * A reference to the MovesPanel showing the history of moves for the game currently being played.
     */
    private final MovesPanel movesPanel;

    /**
     * A collection of event to be triggered in the event that the game is paused.
     */
    private final Event<JButton> onPauseButtonClickedEvent = new Event<>();

    /**
     * Constructor for InformationPanel.
     * @param model A reference to the model containing the information about the state of the game.
     */
    public InformationPanel(ChessModel model) {

        this.setLayout(new BorderLayout());
        this.setBackground(ViewConstants.SECONDARY_COLOR);

        JPanel playerPanel = new JPanel();
        playerPanel1 = new PlayerPanel(model, model.getTeamWhite());
        playerPanel2 = new PlayerPanel(model, model.getTeamBlack());
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
        separator.setBackground(ViewConstants.BOARD_BACKGROUND_COLOR);
        separator.setForeground(ViewConstants.BOARD_BACKGROUND_COLOR);
        playerPanel.add(separator, c);
        c.gridx = 1;
        playerPanel.add(playerPanel2, c);
        this.add(playerPanel, BorderLayout.NORTH);

        playerPanel.setBackground(ViewConstants.SECONDARY_COLOR);

        // Add some margin to the player panels
        playerPanel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        playerPanel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Moves in center
        this.add(moves, BorderLayout.CENTER);

        // Add the inner panels to the list of moves
        moves.setLayout(new GridLayout(0, 1));
        moves.setBackground(ViewConstants.SECONDARY_COLOR);
        
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
        pauseButton.setBackground(ViewConstants.PRIMARY_SIDE_COLOR);
        pauseButton.setForeground(ViewConstants.SECONDARY_COLOR);
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

    //Getters

    /**
     * Gets the reference to the panel showing the move history of the match.
     * @return A reference to the panel showing the move history of the match.
     */
    public MovesPanel getMovesPanel() {
        return movesPanel;
    }

    /**
     * Gets the reference to the panel showing the information corresponding to Player1.
     * @return A reference to the panel showing the information corresponding to Player1.
     */
    public PlayerPanel getPlayerPanel1() {
        return playerPanel1;
    }

    /**
     * Gets the reference to the panel showing the information corresponding to Player2.
     * @return A reference to the panel showing the information corresponding to Player2.
     */
    public PlayerPanel getPlayerPanel2() {
        return playerPanel2;
    }

    /**
     * Gets the reference to the button used for pausing and starting the game.
     * @return A reference to the button used for pausing and starting the game.
     */
    public JButton getPauseButton() {
        return pauseButton;
    }

    /**
     * Gets the reference to the collection of events to be triggered should the pause button be pressed.
     * @return A reference to the collection of events to be triggered should the pause button be pressed.
     */
    public Event<JButton> getOnPauseButtonClickedEvent() {
        return onPauseButtonClickedEvent;
    }
}
