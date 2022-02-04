package view;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import javax.swing.border.*;

import model.*;
 
public class BoardCell extends CellButton {
    private int _row;
    private int _col;
    private boolean _isElimination;

    private Color _defaultColor = null;
    
    public BoardCell(int row, int col) {
        super();

        _row = row;
        _col = col;
    }

    public int getRow() {
        return _row;
    }

    public int getCol() {
        return _col;
    }

    public void setElimination(boolean isElimination) {
        _isElimination = isElimination;
    }

    public boolean isElimination() {
        return _isElimination;
    }

    public void highlight(Color color) {
        if (_defaultColor == null) {
            _defaultColor = getBackground();
        }
        // Take a color inbetween the default color and the highlight color, using ChessView.HIGHLIGHT_ALPHA
        Color highlightColor = new Color(
            (int) (color.getRed() * ChessView.HIGHLIGHT_ALPHA + _defaultColor.getRed() * (1 - ChessView.HIGHLIGHT_ALPHA)),
            (int) (color.getGreen() * ChessView.HIGHLIGHT_ALPHA + _defaultColor.getGreen() * (1 - ChessView.HIGHLIGHT_ALPHA)),
            (int) (color.getBlue() * ChessView.HIGHLIGHT_ALPHA + _defaultColor.getBlue() * (1 - ChessView.HIGHLIGHT_ALPHA))
        );
        this.setBackground(highlightColor);
    }

    public void unhighlight() {
        this.setBackground(_defaultColor);
    }
}
