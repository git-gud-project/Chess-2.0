package com.chess.control;

import java.io.*;
import java.nio.file.Path;
import javax.sound.sampled.*;

public class SoundPlayer {

    public static void playSound(String map, String action) {
        try {
            String filename = System.getProperty("user.dir") + "/res/sounds/" + map+ "/" + action + ".wav";
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
