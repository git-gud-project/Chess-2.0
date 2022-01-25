package view;

import javax.swing.*;
import java.awt.*;

public class CellButton extends JButton {

    private Color hoverBackgroundColor;
    private Color pressedBackgroundColor;

    public CellButton() {
        this(null);
    }

    public CellButton(String text) {
        super(text);
        super.setContentAreaFilled(false);
        
        super.setBorderPainted(false);
        super.setFocusPainted(false);
        super.setRolloverEnabled(false);
        super.setFocusable(false);
    }

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

    @Override
    public void setContentAreaFilled(boolean b) {
    }

    public Color getHoverBackgroundColor() {
        return hoverBackgroundColor;
    }

    public void setHoverBackgroundColor(Color hoverBackgroundColor) {
        this.hoverBackgroundColor = hoverBackgroundColor;
    }

    public Color getPressedBackgroundColor() {
        return pressedBackgroundColor;
    }

    public void setPressedBackgroundColor(Color pressedBackgroundColor) {
        this.pressedBackgroundColor = pressedBackgroundColor;
    }
}
