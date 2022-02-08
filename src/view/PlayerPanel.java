package view;

import model.*;
import utils.Event;

import javax.swing.*;
import java.awt.*;

public class PlayerPanel extends JPanel {
    private JLabel playerName;
    private JLabel playerTime;
    private JLabel playerAuthority;
    private Team team;

    private Event<Team> playerNameChangedEvent = new Event<Team>();

    public PlayerPanel(Team team) {
        this.team = team;

        /**
         * This panel displays the information of the player.
         * 
         * On the top is the player's name, centered.
         * 
         * On the bottom is the player's time, centered.
         */
        this.setPreferredSize(new Dimension(220, 100));

        this.setLayout(new BorderLayout());
        this.setBackground(team.getColor());
        
        playerName = new JLabel("Player Name");
        playerName.setHorizontalAlignment(JLabel.CENTER);
        playerName.setFont(new Font("Arial", Font.BOLD, 20));
        playerName.setForeground(ChessView.PRIMARY_SIDE_COLOR);
        this.add(playerName, BorderLayout.NORTH);

        playerTime = new JLabel("00:00");
        playerTime.setHorizontalAlignment(JLabel.CENTER);
        playerTime.setFont(new Font("Arial", Font.BOLD, 20));
        playerTime.setForeground(ChessView.PRIMARY_SIDE_COLOR);
        this.add(playerTime, BorderLayout.SOUTH);

        playerAuthority = new JLabel("");
        playerAuthority.setHorizontalAlignment(JLabel.CENTER);
        playerAuthority.setFont(new Font("Arial", Font.BOLD, 15));
        playerAuthority.setForeground(ChessView.PRIMARY_SIDE_COLOR);
        this.add(playerAuthority, BorderLayout.CENTER);

        /**
         * Setup event listeners
         */
        team.getOnNameChangedEvent().addDelegate(name -> {
            playerName.setText(name);
        });

        team.getOnTimeChangedEvent().addDelegate(time -> {
            playerTime.setText(time.toString());
        });

        team.getOnAuthorityChangedEvent().addDelegate(authority -> {
            playerAuthority.setText(authority ? "" : "(Remote)");
        });

        team.getModel().getOnTeamChangeEvent().addDelegate(newTeam -> {
            if (newTeam == team) {
                // Set a yellow border
                this.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
            } else {
                // Set a color of the team
                this.setBorder(BorderFactory.createLineBorder(team.getColor(), 5));
            }
        });

        /*if (team == team.getModel().getCurrentTeam())*/ {
            playerName.setForeground(team.getOpponentColor());
            playerTime.setForeground(team.getOpponentColor());
        }

        playerName.setText(team.getName());
        playerTime.setText(team.getTime().toString());

        /**
         * When double clicking on the player name, it will open a dialog to change
         */
        playerName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    playerNameChangedEvent.invoke(team);
                }
            }
        });
    }

    public Team getTeam() {
        return team;
    }

    public Event<Team> getOnPlayerNameChangedEvent() {
        return playerNameChangedEvent;
    }
}
