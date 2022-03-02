package com.chess.view;

import javax.swing.*;

import com.chess.model.chess.ChessModel;
import com.chess.model.chess.ChessTeam;
import com.chess.model.chess.ChessTeamIdentifier;
import com.chess.model.chess.ChessTypeIdentifier;

import java.awt.*;
import java.awt.event.WindowEvent;

/**
 * A class representing the GUI shown when skins for the pieces are selected.
 * @author Oscar Marrero Engström
 * @version 2022-03-02
 */

public class PieceConfigurator extends JPanel {

    /**
     * The number of rows the PieceConfigurator has.
     */
    private static final int ROWS = 3;
    /**
     * The number of columns the PieceConfigurator has.
     */
    private static final int COLS = 4;
    /**
     * The size of the nxn grid on which the game is played.
     */
    private static final int GAMESIZE = 8;

    /**
     * A reference to the game model containing the information about the state of the game.
     */
    private final ChessModel model;

    /**
     * A reference to the frame in which the GUI for the PieceConfigurator is placed.
     */
    private final JFrame frame;

    /**
     * Constructor for PieceConfigurator.
     * @param model A reference to the model containing the information about the game state.
     * @param locX The X-coordinate location of the parent frame.
     * @param locY The Y-coordinate location of the parent frame.
     * @param width The width of the parent frame.
     * @param height The height of the parent frame.
     */
    public PieceConfigurator(ChessModel model, int locX, int locY, int width, int height) {
        super();
        this.model = model;
        this.frame = new JFrame("Customize pieces");
        this.frame.setUndecorated(true);
        this.setLayout(new BorderLayout());
        this.frame.add(this);
        setupUI();
        this.frame.setSize(500, 500);
        this.frame.setLocation( locX + (width - this.frame.getWidth()) / 2,
                 locY + (height - this.frame.getHeight()) / 2);
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