package com.chess.control;

import java.io.*;
import java.nio.file.Path;
import javax.sound.sampled.*;

public class SoundPlayer {

    /**
     * Play a sound clip once
     * Will print to console if no file could be found
     * @param map The map in which the sound file lays
     * @param sound The sound to be played
     */

    public static void playSound(String map, String sound) {
        try {
            String filename = System.getProperty("user.dir") + "/res/sounds/" + map+ "/" + sound + ".wav";
            File file = new File(filename);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        }
        catch(FileNotFoundException e) {
            System.out.println("File not found");
            System.out.println(System.getProperty("user.dir"));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

}
