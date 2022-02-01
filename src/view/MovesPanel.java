package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Right now only works for 2 players
public class MovesPanel extends JPanel {
    private JList<String> _moveList;
    private DefaultListModel<String> _listModel;
    private int _movesNr, _counter;
    private MoveNotation _lastMove;

    public MovesPanel() {
        this.setLayout(new BorderLayout());

        _listModel = new DefaultListModel<>();
        _moveList = new JList<>(_listModel);

        _moveList.setBackground(ChessView.BOARD_BACKGROUND_COLOR);
        _moveList.setForeground(Color.WHITE);
        _moveList.setFont(new Font("Monospaced", Font.PLAIN, 32));
        JScrollPane scrollpane = new JScrollPane(_moveList);
        _movesNr = 0;
        _counter = 0;

        add(scrollpane);
    }

    public void updateModel(ChessModel model) {
        ArrayList<MoveNotation> moveList = model.getMoveList();
        if(_counter != moveList.size()) {
           _counter = moveList.size();
            if (_counter % 2 == 1) {
                _movesNr++;
                _listModel.addElement(String.valueOf(_movesNr) + "     " + moveList.get(_counter-1));
            } else {
                String tmp = _listModel.lastElement();
                _listModel.removeElementAt(_movesNr-1);
                _listModel.addElement(tmp + "     " + moveList.get(_counter-1));
            }
        }
    }
}
