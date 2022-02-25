package com.chess.utils;

import java.awt.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

/**
 * Utility class for loading resources.
 */
public final class Resources {
    /**
     * HashMap of cached icons.
     * 
     * @see #getImageIcon(String)
     */
    private static HashMap<String, ImageIcon> cache = new HashMap<>();

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
        Image image = Toolkit.getDefaultToolkit().getImage(Resources.class.getResource(path));

        return image;
    }
    
    /**
     * Get the icon of the given resource.
     * 
     * @param path The path of the resource.
     */
    public static ImageIcon getImageIcon(String path) {
        // Check if the image is already in the cache.
        if (cache.containsKey(path)) {
            return cache.get(path);
        }

        ImageIcon icon = new ImageIcon(getImage(path));

        // Add the image to the cache.
        cache.put(path, icon);

        return icon;
    }

    public static ImageIcon getOwnImageIcon(String absolutePath) {
        //TODO: Behaves a bit mysteriously if a file is deleted or moved in between pop-ups. Could try to fix this some way.
        Image image = Toolkit.getDefaultToolkit().getImage(absolutePath);
        image = image.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
}
