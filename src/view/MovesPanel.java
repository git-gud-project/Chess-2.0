package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Right now only works for 2 players
public class MovesPanel extends JPanel {
    private final DefaultListModel<String> listModel;
    private int turn, moves;
    JScrollPane scrollpane;

    public MovesPanel(ChessModel model) {
        this.setLayout(new BorderLayout());

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
}
