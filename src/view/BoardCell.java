package view;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import javax.swing.border.*;

import model.*;
 
public class BoardCell extends CellButton {
    private int _row;
    private int _col;

    private Color _defaultColor;
    
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

    public void highlight() {
        _defaultColor = this.getBackground();
        this.setBackground(Color.YELLOW);
    }

    public void unhighlight() {
        this.setBackground(_defaultColor);
    }
}
