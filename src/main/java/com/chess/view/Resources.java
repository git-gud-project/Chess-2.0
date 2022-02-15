package com.chess.view;

import java.awt.*;
import java.net.*;
import javax.swing.*;

public final class Resources {
    private Resources() { }
    
    public static URL getResource(String path) {
        return Resources.class.getResource(path);
    }
    
    public static Image getImage(String path) {
        return Toolkit.getDefaultToolkit().getImage(Resources.class.getResource(path));
    }
    
    public static ImageIcon getImageIcon(String path) {
        return new ImageIcon(Resources.class.getResource(path));
    }
}
