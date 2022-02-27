package com.chess.view;

import com.chess.model.*;
import com.chess.model.chess.ChessModel;
import com.chess.model.chess.ChessTeam;
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

    private Delegate<BoardCell> clickDelegate;

    /** Set up a new panel with a chess board made up of buttons.
     * @param model The model to use to create the board.
     * @param size The size of the chess board to be created. For a normal game of chess the size is 8.
     */
    public BoardGridPanel(ChessModel model, int size) {
        super(new GridLayout(size, size));
        this.cells = new BoardCell[size][size];
        this.size = size;

        Board board = model.getBoard();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                
                BoardCell button = new BoardCell(size - 1 - row, col);

                button.setMinimumSize(ChessView.CELL_MIN_SIZE);
                button.setPreferredSize(ChessView.CELL_IDEAL_SIZE);

                this.add(button);

                button.addActionListener((e) -> {
                    handleClick(button);
                });

                Color color = row % 2 == col % 2 ? ChessView.PRIMARY_COLOR : ChessView.SECONDARY_COLOR;

                // Set the background color of the button, black or white
                button.setBackground(color);

                button.setHoverBackgroundColor(color);
                button.setPressedBackgroundColor(color);

                cells[size - 1 - row][col] = button;

                /**
                 * Setup events
                 */

                Cell cell = board.getCell(size - 1 - row, col);

                cell.getOnPieceChangedEvent().addDelegate(piece -> {
                    if (piece == null) {
                        button.setIcon(null);
                    } else {
                        ChessTeam team = model.getTeamManager().getTeam(piece.getTeamIdentifier());
                        String s = model.getTeamManager().getTeam(piece.getTeamIdentifier()).getSkin(piece.getTypeIdentifier());
                        ImageIcon icon;
                        if (!team.getOwnSkin(piece.getTypeIdentifier())) {
                            if (team.getSkinIndex(piece.getTypeIdentifier()) == 0) {
                                icon = Resources.getImageIcon("/images/" + team.getSkin(piece.getTypeIdentifier()));
                            } else {
                                icon = Resources.getImageIcon("/skins/" + team.getSkin(piece.getTypeIdentifier()));
                            }
                        } else {
                            //TODO: Behaves a bit mysteriously if a file is deleted or moved in between pop-ups. Could try to fix this some way.
                            Image image = Toolkit.getDefaultToolkit().getImage(team.getSkin(piece.getTypeIdentifier()));
                            image = image.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                            icon = new ImageIcon(image);
                        }
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

    //todo fix with dynamic size
    public void unHighlightAll (){
        for(int row=0;row<size;row++){
            for(int col=0;col<size;col++){
                cells[row][col].unhighlight();
                Color color = row % 2 == col % 2 ? ChessView.PRIMARY_COLOR : ChessView.SECONDARY_COLOR;
                // Set the background color of the button, black or white
                cells[row][col].setBackground(color);
            }
        }
    }
}
