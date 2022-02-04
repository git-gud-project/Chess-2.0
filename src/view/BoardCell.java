package view;

import java.awt.*;

public class BoardCell extends CellButton {
    private int row;
    private int col;
    private boolean isElimination;

    private Color defaultColor = null;
    
    public BoardCell(int row, int col) {
        super();

        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setElimination(boolean isElimination) {
        this.isElimination = isElimination;
    }

    public boolean isElimination() {
        return isElimination;
    }

    public void highlight(Color color) {
        if (defaultColor == null) {
            defaultColor = getBackground();
        }

        // Take a color inbetween the default color and the highlight color, using ChessView.HIGHLIGHT_ALPHA
        Color highlightColor = new Color(
            (int) (color.getRed() * ChessView.HIGHLIGHT_ALPHA + defaultColor.getRed() * (1 - ChessView.HIGHLIGHT_ALPHA)),
            (int) (color.getGreen() * ChessView.HIGHLIGHT_ALPHA + defaultColor.getGreen() * (1 - ChessView.HIGHLIGHT_ALPHA)),
            (int) (color.getBlue() * ChessView.HIGHLIGHT_ALPHA + defaultColor.getBlue() * (1 - ChessView.HIGHLIGHT_ALPHA))
        );
        this.setBackground(highlightColor);
    }

    public void unhighlight() {
        this.setBackground(defaultColor);
    }
}
