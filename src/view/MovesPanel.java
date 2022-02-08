package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Right now only works for 2 players
public class MovesPanel extends JPanel {
    private DefaultListModel<String> listModel;
    private int turn, moves;
    private JScrollPane scrollpane;
    private ChessModel model;

    public MovesPanel(ChessModel model) {
        this.setLayout(new BorderLayout());
        this.model = model;

        listModel = new DefaultListModel<>();
        JList<String> moveJList = new JList<>(listModel);

        moveJList.setBackground(ChessView.BOARD_BACKGROUND_COLOR);
        moveJList.setForeground(Color.WHITE);
        moveJList.setFont(new Font("Arial", Font.PLAIN, 32));
        scrollpane = new JScrollPane(moveJList);
        turn = 0;
        moves = 0;

        add(scrollpane);

        /*
         * Setup events
         */

        // Ser att i ChessModels registerMove funktion så körs nu invoke(mN), kan man nu ändra denna funktion så att den inte längre måste kolla med moveList om den ändrats? - Isak

        model.getOnMoveEvent().addDelegate(move -> {
            ArrayList<Move> moveList = model.getMoveList();
            moves++;
                if (moves % 2 == 1) {
                    turn++;
                    listModel.addElement(String.valueOf(turn) + "     " + moveList.get(moves-1));
                } else {
                    String oldLine = listModel.lastElement();
                    listModel.removeElementAt(turn-1);
                    String toBeAdded = String.format("%1$25s", moveList.get(moves-1).toString());
                    listModel.addElement(oldLine + toBeAdded);
                }
            JScrollBar vertical = scrollpane.getVerticalScrollBar();
            vertical.setValue( vertical.getMaximum() );
        });
    }

    public void resetMovesPanel() {
        //Removes the list of moves from the previous game when creating a new one.
        remove(scrollpane);

        //Creates a new list of move which is empty to begin with.
        listModel = new DefaultListModel<>();
        JList<String> moveJList = new JList<>(listModel);
        moveJList.setBackground(ChessView.BOARD_BACKGROUND_COLOR);
        moveJList.setForeground(Color.WHITE);
        moveJList.setFont(new Font("Arial", Font.PLAIN, 32));
        scrollpane = new JScrollPane(moveJList);
        turn = 0;
        moves = 0;

        //Adds the new list of moves to the GUI.
        add(scrollpane);
    }

    public void loadMovesPanel() {
        //Removes the list of moves from the previous game loading a saved game.
        remove(scrollpane);

        //Creates the loaded list of moves which displays the moves until the last saved state.
        listModel = new DefaultListModel<>();
        ArrayList<Move> moveList = model.getMoveList();
        JList<String> moveJList = new JList<>(listModel);
        moveJList.setBackground(ChessView.BOARD_BACKGROUND_COLOR);
        moveJList.setForeground(Color.WHITE);
        moveJList.setFont(new Font("Arial", Font.PLAIN, 32));
        scrollpane = new JScrollPane(moveJList);
        turn = 0;
        moves = 0;

        for (Move moveNotation : moveList) {
            moves++;
            if (moves % 2 == 1) {
                turn++;
                /*spaceSize = 8 - moveNotation.toString().length();  // The distance to be used between two notations
                System.out.println(spaceSize);*/
                listModel.addElement(String.valueOf(turn) + "     " + moveNotation);
            } else {
                String tmp = listModel.lastElement();
                listModel.removeElementAt(turn - 1);
                String space = "";
                /*for (int j = 0; j < spaceSize; j++) {
                    space = space.concat(" ");
                }*/
                listModel.addElement(tmp + space + moveNotation);
            }
        }

        JScrollBar vertical = scrollpane.getVerticalScrollBar();
        vertical.setValue( vertical.getMaximum() );


        add(scrollpane);
    }
}
