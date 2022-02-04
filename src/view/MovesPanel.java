package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Right now only works for 2 players
public class MovesPanel extends JPanel {
    private final DefaultListModel<String> _listModel;
    private int _turn, _moves, _spaceSize;
    JScrollPane _scrollpane;

    public MovesPanel(ChessModel model) {
        this.setLayout(new BorderLayout());

        _listModel = new DefaultListModel<>();
        JList<String >_moveList = new JList<>(_listModel);

        _moveList.setBackground(ChessView.BOARD_BACKGROUND_COLOR);
        _moveList.setForeground(Color.WHITE);
        _moveList.setFont(new Font("Arial", Font.PLAIN, 32));
        _scrollpane = new JScrollPane(_moveList);
        _turn = 0;
        _moves = 0;
        _spaceSize = 0;

        add(_scrollpane);

        /*
         * Setup events
         */


        model.getOnMoveEvent().addDelegate(move -> {
            ArrayList<MoveNotation> moveList = model.getMoveList();
            if(_moves != moveList.size()) {
               _moves = moveList.size();
                if (_moves % 2 == 1) {
                    _turn++;
                    _spaceSize = 8 - moveList.get(_moves-1).toString().length();  // The distance to be used between two notations
                    System.out.println(_spaceSize);
                    _listModel.addElement(String.valueOf(_turn) + "     " + moveList.get(_moves-1));
                } else {
                    String tmp = _listModel.lastElement();
                    _listModel.removeElementAt(_turn-1);
                    String space = "";
                    for(int i=0; i<_spaceSize; i++) {
                        space = space.concat(" ");
                    }
                    _listModel.addElement(tmp + space + moveList.get(_moves-1));
                }
            }
            JScrollBar vertical = _scrollpane.getVerticalScrollBar();
            vertical.setValue( vertical.getMaximum() );
        });
    }
}
