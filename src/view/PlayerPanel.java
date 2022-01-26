package view;

import model.*;

import javax.swing.*;
import java.awt.*;

public class PlayerPanel extends JPanel {
    private JLabel _playerName;
    private JLabel _playerTime;

    public PlayerPanel() {
        /**
         * This panel displays the information of the player.
         * 
         * On the top is the player's name, centered.
         * 
         * On the bottom is the player's time, centered.
         */
        this.setPreferredSize(new Dimension(200, 100));

        this.setLayout(new BorderLayout());
        this.setBackground(ChessView.BOARD_BACKGROUND_COLOR);
        
        _playerName = new JLabel("Player Name");
        _playerName.setHorizontalAlignment(JLabel.CENTER);
        _playerName.setFont(new Font("Arial", Font.BOLD, 20));
        _playerName.setForeground(ChessView.PRIMARY_SIDE_COLOR);
        this.add(_playerName, BorderLayout.NORTH);

        _playerTime = new JLabel("00:00");
        _playerTime.setHorizontalAlignment(JLabel.CENTER);
        _playerTime.setFont(new Font("Arial", Font.BOLD, 20));
        _playerTime.setForeground(ChessView.SECONDARY_SIDE_COLOR);
        this.add(_playerTime, BorderLayout.SOUTH);
    }

    public void updateFromTeam(Team team) {
        _playerName.setText(team.getName());
        float time = team.getTime();
        int minutes = (int) time / 60;
        int seconds = (int) time % 60;
        _playerTime.setText(String.format("%02d:%02d", minutes, seconds));
    }
}
