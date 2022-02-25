package com.chess.model;

/**
 * Represents a skin manager system.
 */

public interface SkinInfo {

    /**
     * Function for obtaining the path to an image file which represents a skin in a game.
     * @return Path to an image file stored in an instance of a class that implements this interface.
     */
    public String getSkinPath();


    /**
     * Function for setting a new path to an image file which represents a skin in a game.
     * @param newPath New path to an image file which will be updated in the instance of a class that implements this interface.
     */
    public void setSkinPath(String newPath);
}
