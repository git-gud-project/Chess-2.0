package com.chess.utils;

import java.awt.*;
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
     * get the image of the given resource.
     * @param path The path of the resource.
     * @return An image created from the given path to the resource.
     */
    public static Image getImage(String path) {
        Image image = Toolkit.getDefaultToolkit().getImage(Resources.class.getResource(path));

        return image;
    }

    /**
     * Get the icon of the given resource.
     * @param path The path of the resource.
     * @return The image icon from the given path to the resource.
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

}
