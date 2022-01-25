import model.ChessModel;
import view.ChessView;
import model.ChessModel;

public class ChessControl {
    public ChessControl() {
        ChessModel model = new ChessModel();
        ChessView view = new ChessView(model);
    }
}
