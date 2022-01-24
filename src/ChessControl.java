import javax.swing.*;
import java.awt.*;

public class ChessControl extends JFrame {


    public ChessControl() {
        super("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(250, 100);
        setSize(new Dimension(600, 600));
        setVisible(true);
    }

    public static void main(String[] args){
        new ChessControl();
    }

}