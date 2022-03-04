package com.chess.view;

import javax.swing.*;

import com.chess.model.chess.ChessModel;

import java.awt.*;
import java.awt.event.*;

/**
 * Represents a board panel.
 * Contains a grid of cells.
 * @author Rasmus Standar
 * @version 2022-03-02
 */
public class BoardPanel extends JPanel {
    /**
     * A reference to an instance of BoardGridPanel, which contains the grid of cells of the chess board.
     */
    private BoardGridPanel boardPanel;

    /** Constructor for BoardPanel.
     * @param model The model to use to create the board.
     * @param size The size of the chess board to be created.
     */
    public BoardPanel(ChessModel model, int size) {
        CellButton button;

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridheight = 1;
        c.gridwidth = 1;

        this.setBackground(ViewConstants.BOARD_BACKGROUND_COLOR);

        // Top
        for (int i = 0; i < size; ++i) {
            button = new CellButton(String.valueOf((char) (i + 'A')));
            c.fill = GridBagConstraints.BOTH;
            c.gridx = i + 1;
            c.gridy = 0;
            c.weightx = 1;
            c.weighty = 0;
            button.setColorAll((i % 2 == 0 ? ViewConstants.PRIMARY_SIDE_COLOR : ViewConstants.SECONDARY_SIDE_COLOR));
            this.add(button, c);
            button.setMinimumSize(ViewConstants.NUM_MIN_SIZE_H);
            button.setPreferredSize(ViewConstants.NUM_IDEAL_SIZE_H);
        }

        // Left
        for (int i = 0; i < size; ++i) {
            button = new CellButton(Integer.toString(size - i));
            c.fill = GridBagConstraints.BOTH;
            c.gridx = 0;
            c.gridy = i + 1;
            c.weightx = 0;
            c.weighty = 1;
            button.setColorAll((i % 2 == 0 ? ViewConstants.PRIMARY_SIDE_COLOR : ViewConstants.SECONDARY_SIDE_COLOR));
            this.add(button, c);
            button.setMinimumSize(ViewConstants.NUM_MIN_SIZE_V);
            button.setPreferredSize(ViewConstants.NUM_IDEAL_SIZE_V);
        }
        
        // Bottom
        for (int i = 0; i < size; ++i) {
            button = new CellButton(String.valueOf((char) (i + 'A')));
            c.fill = GridBagConstraints.BOTH;
            c.gridx = i + 1;
            c.gridy = size + 1;
            c.weightx = 1;
            c.weighty = 0;
            button.setColorAll((i % 2 == 1 ? ViewConstants.PRIMARY_SIDE_COLOR : ViewConstants.SECONDARY_SIDE_COLOR));
            this.add(button, c);
            button.setMinimumSize(ViewConstants.NUM_MIN_SIZE_H);
            button.setPreferredSize(ViewConstants.NUM_IDEAL_SIZE_H);
        }

        // Right
        for (int i = 0; i < size; ++i) {
            button = new CellButton(Integer.toString(size - i));
            c.fill = GridBagConstraints.BOTH;
            c.gridx = size + 1;
            c.gridy = i + 1;
            c.weightx = 0;
            c.weighty = 1;
            button.setColorAll((i % 2 == 1 ? ViewConstants.PRIMARY_SIDE_COLOR : ViewConstants.SECONDARY_SIDE_COLOR));
            this.add(button, c);
            button.setMinimumSize(ViewConstants.NUM_MIN_SIZE_V);
            button.setPreferredSize(ViewConstants.NUM_IDEAL_SIZE_V);
        }

        // Add to the corners
        {
            button = new CellButton("");
            c.fill = GridBagConstraints.BOTH;
            c.gridx = 0;
            c.gridy = 0;
            c.weightx = 1;
            c.weighty = 1;
            button.setColorAll(ViewConstants.SECONDARY_SIDE_COLOR);
            this.add(button, c);
            button.setMinimumSize(ViewConstants.NUM_MIN_SIZE_C);
            button.setPreferredSize(ViewConstants.NUM_IDEAL_SIZE_C);
        }
        {
            button = new CellButton("");
            c.fill = GridBagConstraints.BOTH;
            c.gridx = size + 1;
            c.gridy = 0;
            c.weightx = 1;
            c.weighty = 1;
            button.setColorAll(ViewConstants.PRIMARY_SIDE_COLOR);
            this.add(button, c);
            button.setMinimumSize(ViewConstants.NUM_MIN_SIZE_C);
            button.setPreferredSize(ViewConstants.NUM_IDEAL_SIZE_C);
        }
        {
            button = new CellButton("");
            c.fill = GridBagConstraints.BOTH;
            c.gridx = 0;
            c.gridy = size + 1;
            c.weightx = 1;
            c.weighty = 1;
            button.setColorAll(ViewConstants.PRIMARY_SIDE_COLOR);
            this.add(button, c);
            button.setMinimumSize(ViewConstants.NUM_MIN_SIZE_C);
            button.setPreferredSize(ViewConstants.NUM_IDEAL_SIZE_C);
        }
        {
            button = new CellButton("");
            c.fill = GridBagConstraints.BOTH;
            c.gridx = size + 1;
            c.gridy = size + 1;
            c.weightx = 1;
            c.weighty = 1;
            button.setColorAll(ViewConstants.SECONDARY_SIDE_COLOR);
            this.add(button, c);
            button.setMinimumSize(ViewConstants.NUM_MIN_SIZE_C);
            button.setPreferredSize(ViewConstants.NUM_IDEAL_SIZE_C);
        }

        boardPanel = new BoardGridPanel(model, size);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 8;
        c.weighty = 8;
        c.gridwidth = size;
        c.gridheight = size;
        c.gridx = 1;
        c.gridy = 1;
        this.add(boardPanel, c);
        
        BoardPanel container = this;
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = container.getWidth();
                int h = container.getHeight();
                int size =  Math.min(w, h);
                boardPanel.setPreferredSize(new Dimension(size, size));
                container.revalidate();
            }
        });
    }

    /** Used to return a reference of the instance of a class representing the grid of cells.
     * @return An instance of BoardGridPanel containing the grid of cells representing the chess board.
     */
    public BoardGridPanel getBoardGridPanel() {
        return boardPanel;
    }
}
