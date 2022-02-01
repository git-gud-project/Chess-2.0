package view;

import model.*;
import utils.Event;

import javax.swing.*;
import java.awt.*;

public class PlayerPanel extends JPanel {
    private JLabel _playerName;
    private JLabel _playerTime;

    private Event<String> _playerNameChangedEvent = new Event<String>();

    public PlayerPanel(Team team) {
        /**
         * This panel displays the information of the player.
         * 
         * On the top is the player's name, centered.
         * 
         * On the bottom is the player's time, centered.
         */
        this.setPreferredSize(new Dimension(220, 100));

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

        /**
         * Setup event listeners
         */
        team.getOnNameChangedEvent().addDelegate(name -> {
            _playerName.setText(name);
        });

        team.getOnTimeChangedEvent().addDelegate(time -> {
            _playerTime.setText(time.toString());
        });

        _playerName.setText(team.getName());
        _playerTime.setText(team.getTime().toString());

        /**
         * When double clicking on the player name, it will open a dialog to change
         */
        _playerName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    String newName = JOptionPane.showInputDialog(null, "Enter the new name", _playerName.getText());
                    if (newName != null) {
                        _playerName.setText(newName);
                        _playerNameChangedEvent.invoke(newName);
                    }
                }
            }
        });
    }
}
