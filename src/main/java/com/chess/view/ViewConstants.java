package com.chess.view;

import java.awt.*;

public final class ViewConstants {
    /**
     * The default value for the width of the window containing the GUI for the game.
     */
    public final static int DEFAULT_WINDOW_WIDTH = 1190;
    /**
     * The default value for the height of the window containing the GUI for the game.
     */
    public final static int DEFAULT_WINDOW_HEIGHT = 773;
    /**
     * The default mode for the ability to resize the window containing the GUI for the game.
     */
    public final static boolean DEFAULT_RESIZABLE = true;
    /**
     * The default title for the window containing the GUI for the game.
     */
    public final static String DEFAULT_TITLE = "Chess 2.0";

    /**
     * The minimum size a cell on the board can take up.
     */
    public final static Dimension CELL_MIN_SIZE = new Dimension(60, 60);
    /**
     * The ideal size for the cells displayed on the board.
     */
    public final static Dimension CELL_IDEAL_SIZE = new Dimension(80, 80);

    /**
     * The minimum size for the cells containing column indexes.
     */
    public final static Dimension NUM_MIN_SIZE_H = new Dimension(60, 20);
    /**
     * The ideal size for the cells containing column indexes.
     */
    public final static Dimension NUM_IDEAL_SIZE_H = new Dimension(80, 40);

    /**
     * The minimum size for the cells containing row indexes.
     */
    public final static Dimension NUM_MIN_SIZE_V = new Dimension(20, 60);
    /**
     * The ideal size for the cells containing row indexes.
     */
    public final static Dimension NUM_IDEAL_SIZE_V = new Dimension(40, 80);

    /**
     * The minimum size for the cells situated at the corner of the board.
     */
    public final static Dimension NUM_MIN_SIZE_C = new Dimension(20, 20);
    /**
     * The ideal size for the cells situated at the corner of the board.
     */
    public final static Dimension NUM_IDEAL_SIZE_C = new Dimension(40, 40);

    /**
     * The primary color for the outer cells of the board
     */
    public final static Color PRIMARY_COLOR = Color.GRAY;
    /**
     * The alternating color for the outer cells of the board.
     */
    public final static Color SECONDARY_COLOR = Color.LIGHT_GRAY;

    /**
     * The primary color for the inner cells of the board.
     */
    public final static Color PRIMARY_SIDE_COLOR = Color.DARK_GRAY.brighter();
    /**
     * The alternating color for the inner cells of the board.
     */
    public final static Color SECONDARY_SIDE_COLOR = Color.GRAY.brighter();

    /**
     * The background color for the board.
     */
    public final static Color BOARD_BACKGROUND_COLOR = Color.BLACK;

    /**
     * The color used to highlight cells to which a selected piece can move.
     */
    public final static Color HIGHLIGHT_COLOR_MOVE = Color.GREEN;
    /**
     * The color used to highlight cells which a selected piece kan attack.
     */
    public final static Color HIGHLIGHT_COLOR_ATTACK = Color.RED;
    /**
     * The color used to hihglight a cell containing a selected piece.
     */
    public final static Color HIGHLIGHT_COLOR_PIECE = Color.YELLOW;

    /**
     * Modification parameter used to scale certain RGB values for highlighting.
     */
    public final static float HIGHLIGHT_ALPHA = 0.5f;

    /**
     * The aspect ratio value used when resizing the game.
     */
    public final static float ASPECT_RATIO = (float) DEFAULT_WINDOW_WIDTH / DEFAULT_WINDOW_HEIGHT;

}
