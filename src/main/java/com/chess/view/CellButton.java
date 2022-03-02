package com.chess.view;

import javax.swing.*;
import java.awt.*;

/**
 * A class to represent the graphical aspects of a button placed on the chess board.
 * @author Wincent Stålbert Holm
 * @version 2022-03-02
 */
public class CellButton extends JButton {

    /**
     * The color used to highlight a cell when being hovered.
     */
    private Color hoverBackgroundColor;
    /**
     * The color used ti highlight a cell when being clicked.
     */
    private Color pressedBackgroundColor;

    /**
     * Constructor to create an instance of a JButton showing no text in it.
     */
    public CellButton() {
        this(null);
    }

    /**
     * Constructor to create an instance of a JButton with the provided String as text.
     * @param text A String containing the text to be shown on the button.
     */
    public CellButton(String text) {
        super(text);
        super.setContentAreaFilled(false);
        super.setBorderPainted(false);
        super.setFocusPainted(false);
        super.setRolloverEnabled(false);
        super.setFocusable(false);
        super.setMargin(new Insets(0, 0, 0, 0));
        super.setBackground(Color.WHITE);
    
        hoverBackgroundColor = Color.WHITE;
        pressedBackgroundColor = Color.WHITE;
    }

    /**
     * Selects the color the button is supposed to show when interacting with it.
     * @param color The new color the button is supposed to show when interacting with it.
     */
    public void setColorAll(Color color) {
        super.setBackground(color);
        hoverBackgroundColor = color;
        pressedBackgroundColor = color;
    }


    /**
     * Calls the UI delegate's paint method. Selects the corresponding color to use depending on the state of the button.
     * @param g The Graphics object to protect.
     */
    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(pressedBackgroundColor);
        } else if (getModel().isRollover()) {
            g.setColor(hoverBackgroundColor);
        } else {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    /**
     * Sets the color for the background of a cell when being hovered.
     * @param hoverBackgroundColor New color for the background of a cell when being hovered.
     */
    public void setHoverBackgroundColor(Color hoverBackgroundColor) {
        this.hoverBackgroundColor = hoverBackgroundColor;
    }

    /**
     * Sets the color for the background of a cell when being clicked.
     * @param pressedBackgroundColor New color for the background of a cell when being clicked.
     */
    public void setPressedBackgroundColor(Color pressedBackgroundColor) {
        this.pressedBackgroundColor = pressedBackgroundColor;
    }
}
