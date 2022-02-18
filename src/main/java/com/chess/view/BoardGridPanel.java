package com.chess.view;

import com.chess.model.*;
import com.chess.utils.Delegate;
import com.chess.utils.Resources;

import javax.swing.*;
import java.awt.*;

/** Create a new view panel of a Chess board based on data from a model. This class also set up listeners for all the cells.
 *
 */
public class BoardGridPanel extends JPanel {
    private BoardCell[][] cells;
    private int size;
    private ChessView view;

    private Delegate<BoardCell> clickDelegate;

    /** Set up a new panel with a chess board made up of buttons.
     * @param view The JFrame that the board grid will be added to.
     * @param size The size of the chess board to be created. For a normal game of chess the size is 8.
     */
    public BoardGridPanel(ChessView view, int size) {
        super(new GridLayout(size, size));
        this.cells = new BoardCell[size][size];
        this.size = size;
        this.view = view;

        Board board = view.getModel().getBoard();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                
                BoardCell button = new BoardCell(row, col);

                button.setMinimumSize(ChessView.CELL_MIN_SIZE);
                button.setPreferredSize(ChessView.CELL_IDEAL_SIZE);

                this.add(button);

                button.addActionListener((e) -> {
                    handleClick(button);
                });

                Color color = row % 2 == col % 2 ? ChessView.PRIMARY_COLOR : ChessView.SECONDARY_COLOR;

                // Set the background color of the button, balck or white
                button.setBackground(color);

                button.setHoverBackgroundColor(color);
                button.setPressedBackgroundColor(color);

                cells[row][col] = button;

                /**
                 * Setup events
                 */

                Cell cell = board.getCell(row, col);

                cell.getOnPieceChangedEvent().addDelegate(piece -> {
                    if (piece == null) {
                        button.setIcon(null);
                    } else {
                        ImageIcon icon = Resources.getImageIcon(piece.getIconPath());
                        
                        button.setIcon(icon);
                    }
                });

                Piece piece = cell.getPiece();

                if (piece != null) {
                    ImageIcon icon = Resources.getImageIcon(piece.getIconPath());
                    
                    button.setIcon(icon);
                }
            }
        }
    }

    public void setClickDelegate(Delegate<BoardCell> delegate) {
        clickDelegate = delegate;
    }

    /** Given a row and a column it returns the cell in that coordinate
     * @param row The row of the sought after cell
     * @param col The column of the sought after cell
     * @return The Cell at coordinate (row,col)
     */
    public BoardCell getCell(int row, int col) {
        return cells[row][col];
    }


    /** TODO
     * @param boardCell
     */
    private void handleClick(BoardCell boardCell) {
        if (clickDelegate != null) {
            clickDelegate.trigger(boardCell);
        }
    }
}
