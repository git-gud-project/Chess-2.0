package com.chess.utils;

import java.awt.*;
import java.net.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
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

    public static ImageIcon getOwnImageIcon(String absolutePath) {
        //TODO: Behaves a bit mysteriously if a file is deleted or moved in between pop-ups. Could try to fix this some way.
        Image image = Toolkit.getDefaultToolkit().getImage(absolutePath);
        image = image.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
}
