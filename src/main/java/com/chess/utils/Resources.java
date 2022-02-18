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
     * HashMap of cached images.
     * 
     * @see #getImage(String)
     */
    private static HashMap<String, Image> cache = new HashMap<String, Image>();

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
        // Check if the image is already in the cache.
        if (cache.containsKey(path)) {
            return cache.get(path);
        }

        Image image = Toolkit.getDefaultToolkit().getImage(Resources.class.getResource(path));

        // Add the image to the cache.
        cache.put(path, image);

        return image;
    }
    
    /**
     * Get the icon of the given resource.
     * 
     * @param path The path of the resource.
     */
    public static ImageIcon getImageIcon(String path) {
        return new ImageIcon(getImage(path));
    }
}
