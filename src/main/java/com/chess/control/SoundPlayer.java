package com.chess.control;

import java.io.*;
import javax.sound.sampled.*;

/**
 * A class that enables functionality for playing sound effects during the game.
 * @author Isak Holmdahl
 * @version 2022-03-02
 */
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
        } catch(FileNotFoundException e) {
            // Proceed without sound
            System.out.println("Sound file not found: " + sound);
        } catch (UnsupportedAudioFileException e) {
            // Proceed without sound
            System.out.println("Unsupported audio file: " + sound);
        } catch (LineUnavailableException e) {
            // Proceed without sound
            System.out.println("Line unavailable: " + sound);
        } catch (IOException e) {
            // Proceed without sound
            System.out.println("Unknown IOException on: " + sound);
            e.printStackTrace();
        } 
    }

}
