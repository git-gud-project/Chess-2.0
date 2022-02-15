package com.chess.view;

import javax.swing.*;

import com.chess.model.ChessModel;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.net.URL;

public class PieceConfigurator extends JPanel {

    private final ChessModel model;
    private final ChessView view;

    private final JFrame frame;

    private final static String[][] names = {{"White pawn", "White rook", "White knight"}, {"White bishop", "White queen", "White king"},
            {"Black pawn", "Black rook", "Black knight"}, {"Black bishop", "Black queen", "Black king"}};
    private final static String[][] fileNames = {{"pw.png", "rw.png", "nw.png"}, {"bw.png", "qw.png", "kw.png"}, {"pb.png", "rb.png", "nb.png"}, {"bb.png", "qb.png", "kb.png"}};

    public PieceConfigurator(ChessView view) {
        super();
        this.model = view.getModel();
        this.view = view;
        this.frame = new JFrame("Customize pieces");
        this.setLayout(new BorderLayout());
        this.frame.add(this);
        setupUI();
        this.frame.setSize(500, 500);
        this.frame.setLocation((int) view.getLocation().getX() + (view.getWidth() - this.frame.getWidth()) / 2,
                (int) view.getLocation().getY() + (view.getHeight() - this.frame.getHeight()) / 2);
        this.frame.setVisible(true);
    }

    private void setupUI() {

        //Creating panel where custom skins for pieces are selected.
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        for (int i = 0; i < names.length; i++) {
            for (int j = 0; j < names[i].length; j++) {
                JPanel panel = selectorPanel(i, j);
                c.gridx = i;
                c.gridy = j;
                buttonsPanel.add(panel, c);
            }
        }

        //Creating the bottom buttons to accept or cancel the changes.
        JPanel acceptCancelPanel = new JPanel();
        acceptCancelPanel.setLayout(new GridBagLayout());
        JButton accept = new JButton("OK");
        JButton cancel = new JButton("Cancel");
        c = new GridBagConstraints();
        accept.setFocusable(false);
        cancel.setFocusable(false);
        c.insets = new Insets(5, 20, 5, 20);
        acceptCancelPanel.add(accept, c);
        acceptCancelPanel.add(cancel, c);

        //Adding action listeners to the buttons.
        accept.addActionListener((e) -> {
            //TODO: Placeholder behavior, should accept and perform the changes later on.
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });
        cancel.addActionListener((e) -> {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });

        //Adding the panels to the main panel displayed in the frame.
        this.add(buttonsPanel, BorderLayout.CENTER);
        this.add(acceptCancelPanel, BorderLayout.SOUTH);
    }

    private JPanel selectorPanel(int i, int j) {
        //Creating panel for piece selector
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        //Creating and adding label at the top of the selector
        JLabel label = new JLabel(names[i][j], SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(label, BorderLayout.NORTH);

        //Creating and adding image to be displayed in the middle of the selector
        JLabel imageLabel = new JLabel("", SwingConstants.CENTER);
        imageLabel.setIcon(getImageIcon("/images/" + fileNames[i][j]));
        panel.add(imageLabel, BorderLayout.CENTER);

        //Creating and adding buttons for rotating through images
        JPanel leftRightPanel = new JPanel();
        leftRightPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 5, 0, 5);
        JButton left = new JButton();
        JButton right = new JButton();
        left.setFocusable(false);
        right.setFocusable(false);
        left.setPreferredSize(new Dimension(20, 20));
        right.setPreferredSize(new Dimension(20, 20));
        left.setIcon(getImageIcon("/images/left.png"));
        right.setIcon(getImageIcon("/images/right.png"));
        leftRightPanel.add(left, c);
        leftRightPanel.add(right, c);
        panel.add(leftRightPanel, BorderLayout.SOUTH);


        return panel;
    }

    //Helper function to load images.
    private ImageIcon getImageIcon(String path) {
        URL url = getClass().getResource(path);
        Image image = Toolkit.getDefaultToolkit().getImage(url);
        return new ImageIcon(image);
    }
}
