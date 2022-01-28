import model.ChessModel;
import view.ChessView;
import model.ChessModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessControl {
    private final ChessModel _model;
    private final ChessView _view;

    public ChessControl() {
        _model = new ChessModel();
        _view = new ChessView(_model);
        Timer t = new Timer(100, new TimerListener());
        t.start();
    }

    public class TimerListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            _model.getCurrentTeam().getTime().tick();
            _view.updateModel();
        }
    }
}
