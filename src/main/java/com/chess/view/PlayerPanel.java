package com.chess.view;

import com.chess.model.*;
import com.chess.model.chess.ChessModel;
import com.chess.model.chess.ChessTeam;
import com.chess.utils.Event;

import javax.swing.*;
import java.awt.*;

public class PlayerPanel extends JPanel {
    private final JLabel playerName;
    private final JLabel playerTime;
    private final JLabel playerAuthority;
    private final ChessTeam team;
    private final ChessModel model;

    private Event<ChessTeam> playerNameChangedEvent = new Event<ChessTeam>();

    public PlayerPanel(ChessModel model, ChessTeam team) {
        this.team = team;
        this.model = model;

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

        model.getOnTeamChangeEvent().addDelegate(newTeam -> {
            if (newTeam == team) {
                // Set a yellow border
                this.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
            } else {
                // Set a color of the team
                this.setBorder(BorderFactory.createLineBorder(team.getColor(), 5));
            }
        });

        ChessTeam opponent = model.getOtherTeam(team);

        {
            playerName.setForeground(opponent.getColor());
            playerTime.setForeground(opponent.getColor());
        }

        playerName.setText(team.getName());
        playerTime.setText(team.getTime().toString());

        /**
         * When double clicking on the player name, it will open a dialog to change
         */
        playerName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    playerNameChangedEvent.trigger(team);
                }
            }
        });
    }

    public ChessTeam getTeam() {
        return team;
    }

    public Event<ChessTeam> getOnPlayerNameChangedEvent() {
        return playerNameChangedEvent;
    }
}
