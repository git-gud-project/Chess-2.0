package com.chess.view;

import com.chess.model.chess.ChessModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * A class used for showing the move history of the match.
 * @author Isak Holmdahl
 * @version 2022-03-02
 */
public class MovesPanel extends JPanel {
    
    /**
     * A list containing the move history of a given match.
     */
    private DefaultListModel<String> listModel;
    
    /**
     * Stores whose turn it is.
     */
    private int turn;
    
    /**
     * Stores the number of moves that have been made.
     */
    private int moves;
    
    /**
     * ScrollPane used to store the move history when the size of the text exceed the dimension of the panel in which it is stored.
     */
    private JScrollPane scrollpane;

    /**
     * A reference to the game model in which the information of the game state is stored.
     */
    private ChessModel model;

    /**
     * Constructor for MovesPanel. Creates a window with a list in it for move notations
     * @param model a reference for the current model
     */
    public MovesPanel(ChessModel model) {
        this.setLayout(new BorderLayout());
        this.model = model;

        listModel = new DefaultListModel<>();
        JList<String> moveJList = new JList<>(listModel);

        moveJList.setBackground(ViewConstants.BOARD_BACKGROUND_COLOR);
        moveJList.setForeground(Color.WHITE);
        moveJList.setFont(new Font("Monospaced", Font.PLAIN, 32));
        scrollpane = new JScrollPane(moveJList);
        turn = 0;
        moves = 0;

        add(scrollpane);

        /*
         * Setup events
         */

        model.getOnMoveEvent().addDelegate(move -> {
            List<String> moveList = model.getMoveList();
            moves++;
            if (moves % 2 == 1) {
                turn++;
                StringBuilder spacing = new StringBuilder("");
                for(int i=0; i<4-String.valueOf(turn).length(); i++) {
                    spacing.append(" ");
                }
                listModel.addElement(String.valueOf(turn) + "." + spacing + moveList.get(moves-1));
            } else {
                String oldLine = listModel.lastElement();
                listModel.removeElementAt(turn-1);
                String toBeAdded = moveList.get(moves-1).toString();
                StringBuilder spacing = new StringBuilder("");
                for(int i=0; i<14-oldLine.length(); i++) {
                    spacing.append(" ");
                }
                listModel.addElement(oldLine + spacing + toBeAdded);
            }
            JScrollBar vertical = scrollpane.getVerticalScrollBar();
            vertical.setValue( vertical.getMaximum() );
        });

        model.getOnModelLoadedEvent().addDelegate((serialModel) ->
            resetMovesPanel()
        );
    }

    /**
     * Clears all notation from this panel.
     */
    public void resetMovesPanel() {
        //Removes the list of moves from the previous game when creating a new one.
        remove(scrollpane);

        //Creates a new list of move which is empty to begin with.
        listModel = new DefaultListModel<>();
        JList<String> _moveList = new JList<>(listModel);
        _moveList.setBackground(ViewConstants.BOARD_BACKGROUND_COLOR);
        _moveList.setForeground(Color.WHITE);
        _moveList.setFont(new Font("Monospaced", Font.PLAIN, 32));
        scrollpane = new JScrollPane(_moveList);
        turn = 0;
        moves = 0;

        //Adds the new list of moves to the GUI.
        add(scrollpane);
    }

    /**
     * Reloads the move history when a saved game is loaded.
     */
     public void loadMovesPanel() {
        //Removes the list of moves from the previous game loading a saved game.
        remove(scrollpane);

        //Creates the loaded list of moves which displays the moves until the last saved state.
        listModel = new DefaultListModel<>();
        List<String> moveList = model.getMoveList();
        JList<String> jMoveList = new JList<>(listModel);
        jMoveList.setBackground(ViewConstants.BOARD_BACKGROUND_COLOR);
        jMoveList.setForeground(Color.WHITE);
        jMoveList.setFont(new Font("Monospaced", Font.PLAIN, 32));
        scrollpane = new JScrollPane(jMoveList);
        turn = 0;
        moves = 0;

        for (String moveNotation : moveList) {
            moves++;
            if (moves % 2 == 1) {
                turn++;
                StringBuilder spacing = new StringBuilder("");
                for(int i=0; i<4-String.valueOf(turn).length(); i++) {
                    spacing.append(" ");
                }
                listModel.addElement(String.valueOf(turn) + "." + spacing + moveList.get(moves-1));
            } else {
                String oldLine = listModel.lastElement();
                listModel.removeElementAt(turn - 1);
                String toBeAdded = moveList.get(moves - 1).toString();
                StringBuilder spacing = new StringBuilder("");
                for (int i = 0; i < 14 - oldLine.length(); i++) {
                    spacing.append(" ");
                }
                listModel.addElement(oldLine + spacing + toBeAdded);
            }
        }
            JScrollBar vertical = scrollpane.getVerticalScrollBar();
            vertical.setValue( vertical.getMaximum() );

            add(scrollpane);
        }
}
