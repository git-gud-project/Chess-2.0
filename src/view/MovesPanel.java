package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Right now only works for 2 players
public class MovesPanel extends JPanel {
    private DefaultListModel<String> _listModel;
    private int _turn, _moves, _spaceSize;
    ChessModel _model;
    JScrollPane _scrollpane;

    public MovesPanel(ChessModel model) {
        this.setLayout(new BorderLayout());
        this._model = model;

        _listModel = new DefaultListModel<>();
        JList<String> _moveList = new JList<>(_listModel);

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

        // Ser att i ChessModels registerMove funktion så körs nu invoke(mN), kan man nu ändra denna funktion så att den inte längre måste kolla med moveList om den ändrats? - Isak

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

    public void resetMovesPanel() {
        //Removes the list of moves from the previous game when creating a new one.
        remove(_scrollpane);

        //Creates a new list of move which is empty to begin with.
        _listModel = new DefaultListModel<>();
        JList<String> _moveList = new JList<>(_listModel);
        _moveList.setBackground(ChessView.BOARD_BACKGROUND_COLOR);
        _moveList.setForeground(Color.WHITE);
        _moveList.setFont(new Font("Arial", Font.PLAIN, 32));
        _scrollpane = new JScrollPane(_moveList);
        _turn = 0;
        _moves = 0;

        //Adds the new list of moves to the GUI.
        add(_scrollpane);
    }

    public void loadMovesPanel() {
        //Removes the list of moves from the previous game loading a saved game.
        remove(_scrollpane);

        //Creates the loaded list of moves which displays the moves until the last saved state.
        _listModel = new DefaultListModel<>();
        ArrayList<MoveNotation> moveList = _model.getMoveList();
        JList<String> _moveList = new JList<>(_listModel);
        _moveList.setBackground(ChessView.BOARD_BACKGROUND_COLOR);
        _moveList.setForeground(Color.WHITE);
        _moveList.setFont(new Font("Arial", Font.PLAIN, 32));
        _scrollpane = new JScrollPane(_moveList);
        _turn = 0;
        _moves = 0;

        for (MoveNotation moveNotation : moveList) {
            _moves++;
            if (_moves % 2 == 1) {
                _turn++;
                _spaceSize = 8 - moveNotation.toString().length();  // The distance to be used between two notations
                System.out.println(_spaceSize);
                _listModel.addElement(String.valueOf(_turn) + "     " + moveNotation);
            } else {
                String tmp = _listModel.lastElement();
                _listModel.removeElementAt(_turn - 1);
                String space = "";
                for (int j = 0; j < _spaceSize; j++) {
                    space = space.concat(" ");
                }
                _listModel.addElement(tmp + space + moveNotation);
            }
        }

        JScrollBar vertical = _scrollpane.getVerticalScrollBar();
        vertical.setValue( vertical.getMaximum() );


        add(_scrollpane);
    }
}
