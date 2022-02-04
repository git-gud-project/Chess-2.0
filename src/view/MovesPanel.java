package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Right now only works for 2 players
public class MovesPanel extends JPanel {
    private final DefaultListModel<String> listModel;
    private int turn, moves, spaceSize;
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
        spaceSize = 0;

        add(scrollpane);

        /*
         * Setup events
         */


        model.getOnMoveEvent().addDelegate(move -> {
            ArrayList<MoveNotation> moveList = model.getMoveList();
            if(moves != moveList.size()) {
               moves = moveList.size();
                if (moves % 2 == 1) {
                    turn++;
                    spaceSize = 8 - moveList.get(moves-1).toString().length();  // The distance to be used between two notations
                    System.out.println(spaceSize);
                    listModel.addElement(String.valueOf(turn) + "     " + moveList.get(moves-1));
                } else {
                    String tmp = listModel.lastElement();
                    listModel.removeElementAt(turn-1);
                    String space = "";
                    for(int i=0; i<spaceSize; i++) {
                        space = space.concat(" ");
                    }
                    listModel.addElement(tmp + space + moveList.get(moves-1));
                }
            }
            JScrollBar vertical = scrollpane.getVerticalScrollBar();
            vertical.setValue( vertical.getMaximum() );
        });
    }
}
