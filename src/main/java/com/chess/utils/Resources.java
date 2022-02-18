package com.chess.utils;

import java.awt.*;
import java.net.*;
import javax.swing.*;

/**
 * Utility class for loading resources.
 */
public final class Resources {
    /**
     * Private constructor.
     */
    private Resources() { }
    
    /**
     * Get the URL of the given resource.
     * 
     * @param path The path of the resource.
     */
    public static URL getResource(String path) {
        return Resources.class.getResource(path);
    }
    
    /**
     * Get the image of the given resource.
     * 
     * @param path The path of the resource.
     */
    public static Image getImage(String path) {
        return Toolkit.getDefaultToolkit().getImage(Resources.class.getResource(path));
    }
    
    /**
     * Get the icon of the given resource.
     * 
     * @param path The path of the resource.
     */
    public static ImageIcon getImageIcon(String path) {
        return new ImageIcon(Resources.class.getResource(path));
    }
}
