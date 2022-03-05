package com.chess.view;

import java.awt.*;

/**
 * BoardCell extends class CellButton by containing row and column values as well as containing information regarding eliminations.
 * @author Wincent Stålbert Holm
 * @version 2022-03-05
 */
public class BoardCell extends CellButton {
    /**
     * The x-coordinate representing the position of the BoardCell.
     */
    private int row;
    /**
     * The y-coordinate representing the position of the BoardCell.
     */
    private int col;

    /**
     * Stores the default color used for
     */
    private Color defaultColor = null;

    /** Constructs a new cell with coordinates (row,col)
     * @param row Used to set the row for the cell
     * @param col Used to set the col for the cell
     * @param defaultColor Used to set the default color for the cell
     */
    public BoardCell(int row, int col, Color defaultColor) {
        super();

        this.row = row;
        this.col = col;
        this.defaultColor = defaultColor;
    }

    /** Used to get the row of the cell
     * @return The index for the row of the cell
     */
    public int getRow() {
        return row;
    }

    /** Used to get the column of the cell
     * @return The index for the column of the cell
     */
    public int getCol() {
        return col;
    }



    /** Highlights the cell in the parameter color. The firs call of this method sets the defaultColor of the Cell to the parameter value
     * @param color Color of the highlight
     */
    public void highlight(Color color) {
        // Take a color inbetween the default color and the highlight color, using ViewConstants.HIGHLIGHT_ALPHA
        Color highlightColor = new Color(
            (int) (color.getRed() * ViewConstants.HIGHLIGHT_ALPHA + defaultColor.getRed() * (1 - ViewConstants.HIGHLIGHT_ALPHA)),
            (int) (color.getGreen() * ViewConstants.HIGHLIGHT_ALPHA + defaultColor.getGreen() * (1 - ViewConstants.HIGHLIGHT_ALPHA)),
            (int) (color.getBlue() * ViewConstants.HIGHLIGHT_ALPHA + defaultColor.getBlue() * (1 - ViewConstants.HIGHLIGHT_ALPHA))
        );
        this.setBackground(highlightColor);
    }

    /**
     * Unhighlight this cell, that is set the background to the default background
     */
    public void unhighlight() {
        this.setBackground(defaultColor);
    }
}
