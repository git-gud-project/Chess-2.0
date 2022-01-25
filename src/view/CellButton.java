package view;

import javax.swing.*;
import java.awt.*;

public class CellButton extends JButton {

    private Color _hoverBackgroundColor;
    private Color _pressedBackgroundColor;

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
        super.setMargin(new Insets(0, 0, 0, 0));
        super.setBackground(Color.WHITE);
    
        _hoverBackgroundColor = Color.WHITE;
        _pressedBackgroundColor = Color.WHITE;
    }

    public void setColorAll(Color color) {
        super.setBackground(color);
        _hoverBackgroundColor = color;
        _pressedBackgroundColor = color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(_pressedBackgroundColor);
        } else if (getModel().isRollover()) {
            g.setColor(_hoverBackgroundColor);
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
        return _hoverBackgroundColor;
    }

    public void setHoverBackgroundColor(Color hoverBackgroundColor) {
        this._hoverBackgroundColor = hoverBackgroundColor;
    }

    public Color getPressedBackgroundColor() {
        return _pressedBackgroundColor;
    }

    public void setPressedBackgroundColor(Color pressedBackgroundColor) {
        this._pressedBackgroundColor = pressedBackgroundColor;
    }
}
