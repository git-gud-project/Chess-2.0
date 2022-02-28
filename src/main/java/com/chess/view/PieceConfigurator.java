package com.chess.view;

import javax.swing.*;

import com.chess.model.chess.ChessModel;
import com.chess.model.chess.ChessTeam;
import com.chess.model.chess.ChessTeamIdentifier;
import com.chess.model.chess.ChessTypeIdentifier;

import java.awt.*;
import java.awt.event.WindowEvent;

public class PieceConfigurator extends JPanel {

    private static final int ROWS = 3;
    private static final int COLS = 4;
    private static final int GAMESIZE = 8;

    private final ChessModel model;

    private final JFrame frame;

    public PieceConfigurator(JMenuBar bar, ChessModel model) {
        super();
        this.model = model;
        Component comp = bar.getTopLevelAncestor();
        this.frame = new JFrame("Customize pieces");
        this.frame.setUndecorated(true);
        this.setLayout(new BorderLayout());
        this.frame.add(this);
        setupUI();
        this.frame.setSize(500, 500);
        this.frame.setLocation((int) comp.getLocation().getX() + (comp.getWidth() - this.frame.getWidth()) / 2,
                (int) comp.getLocation().getY() + (comp.getHeight() - this.frame.getHeight()) / 2);
        this.frame.setVisible(true);
    }

    private void setupUI() {

        //Creating a local copy of each team's instance to roll back any performed changes that were not committed.
        ChessTeam teamWhite = model.getTeamWhite().clone();
        ChessTeam teamBlack = model.getTeamBlack().clone();

        //Creating panel where custom skins for pieces are selected.
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                JPanel panel = createPieceSelector(i, j);
                c.gridx = i;
                c.gridy = j;
                buttonsPanel.add(panel, c);
            }
        }

        //Creating the bottom buttons to accept or cancel the changes.
        JPanel acceptCancelPanel = new JPanel();
        acceptCancelPanel.setLayout(new GridBagLayout());
        JButton accept = new JButton("OK");
        JButton cancel = new JButton("Cancel");
        c = new GridBagConstraints();
        accept.setFocusable(false);
        cancel.setFocusable(false);
        c.insets = new Insets(5, 20, 5, 20);
        acceptCancelPanel.add(accept, c);
        acceptCancelPanel.add(cancel, c);

        //Adding action listeners to the buttons.
        accept.addActionListener((e) -> {
            for (int i = 0; i < GAMESIZE; i++) {
                for (int j = 0; j < GAMESIZE; j++) {
                    model.getBoard().getCell(i, j).getOnPieceChangedEvent().trigger(model.getBoard().getCell(i, j).getPiece());
                }
            }
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });

        cancel.addActionListener((e) -> {
            model.getTeamManager().setTeam(ChessTeamIdentifier.WHITE, teamWhite);
            model.getTeamManager().setTeam(ChessTeamIdentifier.BLACK, teamBlack);
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });

        //Adding the panels to the main panel displayed in the frame.
        this.add(buttonsPanel, BorderLayout.CENTER);
        this.add(acceptCancelPanel, BorderLayout.SOUTH);

    }

    private JPanel createPieceSelector(int i, int j) {
        return new PieceSelector(this.model, selectType(i, j), selectTeam(i, j));
    }

    private ChessTypeIdentifier selectType(int i, int j) {
        switch ((i * ROWS + j) % (ROWS * 2)) {
            case 1:
                return ChessTypeIdentifier.ROOK;
            case 2:
                return ChessTypeIdentifier.KNIGHT;
            case 3:
                return ChessTypeIdentifier.BISHOP;
            case 4:
                return ChessTypeIdentifier.QUEEN;
            case 5:
                return ChessTypeIdentifier.KING;
            default:
                return ChessTypeIdentifier.PAWN;
        }
    }

    private ChessTeamIdentifier selectTeam(int i, int j) {
        if (i * ROWS + j < (ROWS * 2)) return ChessTeamIdentifier.WHITE;
        return ChessTeamIdentifier.BLACK;
    }

}