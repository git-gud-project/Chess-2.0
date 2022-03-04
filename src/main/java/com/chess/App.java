package com.chess;
import com.chess.control.ChessControl;

/**
 * The application class to run the game.
 * @author Rasmus Standar
 * @version 2022-03-02
 */
public class App {
    /**
     * The main function to execute the game.
     * @param args Arguments passed to the main function.
     * @throws Exception Exceptions may be thrown during the execution of the main function.
     */
    public static void main(String[] args) throws Exception {
        new ChessControl();
    }
}
