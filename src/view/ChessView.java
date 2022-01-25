package view;
import javax.swing.*;
import java.awt.*;

public class ChessView extends JFrame {
    
    public ChessView() {
        super("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(600, 600));

        this.setLayout(new BorderLayout());

        var pane = new JPanel();
        
        CellButton button;

        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        final int SIZE = 8;

        for (int i = 0; i < SIZE; ++i) {
            button = new CellButton(String.valueOf((char) (i + 'A')));
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = i + 1;
            c.gridy = 0;
            pane.add(button, c);
            button.setMinimumSize(new Dimension(60, 20));
            button.setPreferredSize(new Dimension(80, 40));
        }

        for (int i = 0; i < SIZE; ++i) {
            button = new CellButton(Integer.toString(SIZE - i));
            c.fill = GridBagConstraints.VERTICAL;
            c.gridx = 0;
            c.gridy = i + 1;
            pane.add(button, c);
            button.setMinimumSize(new Dimension(20, 60));
            button.setPreferredSize(new Dimension(40, 80));
        }
        
        for (int i = 0; i < SIZE; ++i) {
            button = new CellButton(String.valueOf((char) (i + 'A')));
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = i + 1;
            c.gridy = SIZE + 1;
            pane.add(button, c);
            button.setMinimumSize(new Dimension(60, 20));
            button.setPreferredSize(new Dimension(80, 40));
        }

        for (int i = 0; i < SIZE; ++i) {
            button = new CellButton(Integer.toString(SIZE - i));
            c.fill = GridBagConstraints.VERTICAL;
            c.gridx = SIZE + 1;
            c.gridy = i + 1;
            pane.add(button, c);
            button.setMinimumSize(new Dimension(20, 60));
            button.setPreferredSize(new Dimension(40, 80));
        }

        JPanel grid = new BoardPanel(SIZE);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.0;
        c.gridwidth = 8;
        c.gridheight = 8;
        c.gridx = 1;
        c.gridy = 1;
        pane.add(grid, c);

        this.add(pane, BorderLayout.CENTER);

        pack();

        setVisible(true);
    }
}
