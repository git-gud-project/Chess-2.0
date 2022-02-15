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

    private static final String[][] names = {{"White pawn", "White rook", "White knight"}, {"White bishop", "White queen", "White king"},
            {"Black pawn", "Black rook", "Black knight"}, {"Black bishop", "Black queen", "Black king"}};
    private static final String[][] fileNames = {{"pw.png", "rw.png", "nw.png"}, {"bw.png", "qw.png", "kw.png"}, {"pb.png", "rb.png", "nb.png"}, {"bb.png", "qb.png", "kb.png"}};
    private static final String[] skinsNames = {"bird.png", "blueninja.png", "car.png", "eagle.png", "eyebat.png"};

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
        c.insets = new Insets(0, 2, 0, 2);
        JButton left = new JButton();
        JButton right = new JButton();
        JButton plus = new JButton();
        left.setFocusable(false);
        right.setFocusable(false);
        plus.setFocusable(false);
        left.setPreferredSize(new Dimension(20, 20));
        right.setPreferredSize(new Dimension(20, 20));
        plus.setPreferredSize(new Dimension(20, 20));
        left.setIcon(getImageIcon("/images/left.png"));
        right.setIcon(getImageIcon("/images/right.png"));
        plus.setIcon(getImageIcon("/images/plus.png"));
        leftRightPanel.add(left, c);
        leftRightPanel.add(right, c);
        leftRightPanel.add(plus, c);
        panel.add(leftRightPanel, BorderLayout.SOUTH);

        right.addActionListener((e) -> {
            handleRightClick(i*3 + j, imageLabel);
        });

        left.addActionListener((e) -> {
            handleLeftClick(i*3 + j, imageLabel);
        });

        return panel;
    }

    //Helper function to load images.
    private ImageIcon getImageIcon(String path) {
        URL url = getClass().getResource(path);
        Image image = Toolkit.getDefaultToolkit().getImage(url);
        return new ImageIcon(image);
    }

    private void handleRightClick(int n, JLabel label) {
        switch (n) {
            case 0:
                model.getTeamWhite().incPawnSkin();
                updateWhitePawn(label);
                break;
            case 1:
                model.getTeamWhite().incRookSkin();
                updateWhiteRook(label);
                break;
            case 2:
                model.getTeamWhite().incKnightSkin();
                updateWhiteKnight(label);
                break;
            case 3:
                model.getTeamWhite().incBishopSkin();
                updateWhiteBishop(label);
                break;
            case 4:
                model.getTeamWhite().incQueenSkin();
                updateWhiteQueen(label);
                break;
            case 5:
                model.getTeamWhite().incKingSkin();
                updateWhiteKing(label);
                break;
            case 6:
                model.getTeamBlack().incPawnSkin();
                updateBlackPawn(label);
                break;
            case 7:
                model.getTeamBlack().incRookSkin();
                updateBlackRook(label);
                break;
            case 8:
                model.getTeamBlack().incKnightSkin();
                updateBlackKnight(label);
                break;
            case 9:
                model.getTeamBlack().incBishopSkin();
                updateBlackBishop(label);
                break;
            case 10:
                model.getTeamBlack().incQueenSkin();
                updateBlackQueen(label);
                break;
            case 11:
                model.getTeamBlack().incKingSkin();
                updateBlackKing(label);
                break;
            default: break;
        }
    }

    private void handleLeftClick(int n, JLabel label){
        switch (n) {
            case 0:
                model.getTeamWhite().decPawnSkin();
                updateWhitePawn(label);
                break;
            case 1:
                model.getTeamWhite().decRookSkin();
                updateWhiteRook(label);
                break;
            case 2:
                model.getTeamWhite().decKnightSkin();
                updateWhiteKnight(label);
                break;
            case 3:
                model.getTeamWhite().decBishopSkin();
                updateWhiteBishop(label);
                break;
            case 4:
                model.getTeamWhite().decQueenSkin();
                updateWhiteQueen(label);
                break;
            case 5:
                model.getTeamWhite().decKingSkin();
                updateWhiteKing(label);
                break;
            case 6:
                model.getTeamBlack().decPawnSkin();
                updateBlackPawn(label);
                break;
            case 7:
                model.getTeamBlack().decRookSkin();
                updateBlackRook(label);
                break;
            case 8:
                model.getTeamBlack().decKnightSkin();
                updateBlackKnight(label);
                break;
            case 9:
                model.getTeamBlack().decBishopSkin();
                updateBlackBishop(label);
                break;
            case 10:
                model.getTeamBlack().decQueenSkin();
                updateBlackQueen(label);
                break;
            case 11:
                model.getTeamBlack().decKingSkin();
                updateBlackKing(label);
                break;
            default: break;
        }
    }

    private void updateWhitePawn(JLabel label){
        if (((model.getTeamWhite().getPawnSkin() % (skinsNames.length+1)) + (skinsNames.length+1)) % (skinsNames.length+1) == 0) label.setIcon(getImageIcon("/images/pw.png"));
        else label.setIcon(getImageIcon("/skins/" + skinsNames[((model.getTeamWhite().getPawnSkin() % (skinsNames.length+1)) + (skinsNames.length+1)) % (skinsNames.length+1) - 1]));
    }
    private void updateWhiteRook(JLabel label){
        if (((model.getTeamWhite().getRookSkin() % (skinsNames.length+1)) + (skinsNames.length+1)) % (skinsNames.length+1) == 0) label.setIcon(getImageIcon("/images/rw.png"));
        else label.setIcon(getImageIcon("/skins/" + skinsNames[((model.getTeamWhite().getRookSkin() % (skinsNames.length+1)) + (skinsNames.length+1)) % (skinsNames.length+1) - 1]));
    }
    private void updateWhiteKnight(JLabel label){
        if (((model.getTeamWhite().getKnightSkin() % (skinsNames.length+1)) + (skinsNames.length+1)) % (skinsNames.length+1) == 0) label.setIcon(getImageIcon("/images/nw.png"));
        else label.setIcon(getImageIcon("/skins/" + skinsNames[((model.getTeamWhite().getKnightSkin() % (skinsNames.length+1)) + (skinsNames.length+1)) % (skinsNames.length+1) - 1]));
    }
    private void updateWhiteBishop(JLabel label){
        if (((model.getTeamWhite().getBishopSkin() % (skinsNames.length+1)) + (skinsNames.length+1)) % (skinsNames.length+1) == 0) label.setIcon(getImageIcon("/images/bw.png"));
        else label.setIcon(getImageIcon("/skins/" + skinsNames[((model.getTeamWhite().getBishopSkin() % (skinsNames.length+1)) + (skinsNames.length+1)) % (skinsNames.length+1) - 1]));
    }
    private void updateWhiteQueen(JLabel label){
        if (((model.getTeamWhite().getQueenSkin() % (skinsNames.length+1)) + (skinsNames.length+1)) % (skinsNames.length+1) == 0) label.setIcon(getImageIcon("/images/qw.png"));
        else label.setIcon(getImageIcon("/skins/" + skinsNames[((model.getTeamWhite().getQueenSkin() % (skinsNames.length+1)) + (skinsNames.length+1)) % (skinsNames.length+1) - 1]));
    }
    private void updateWhiteKing(JLabel label){
        if (((model.getTeamWhite().getKingSkin() % (skinsNames.length+1)) + (skinsNames.length+1)) % (skinsNames.length+1) == 0) label.setIcon(getImageIcon("/images/kw.png"));
        else label.setIcon(getImageIcon("/skins/" + skinsNames[((model.getTeamWhite().getKingSkin() % (skinsNames.length+1)) + (skinsNames.length+1)) % (skinsNames.length+1) - 1]));
    }

    private void updateBlackPawn(JLabel label){
        if (((model.getTeamBlack().getPawnSkin() % (skinsNames.length+1)) + (skinsNames.length+1)) % (skinsNames.length+1) == 0) label.setIcon(getImageIcon("/images/pb.png"));
        else label.setIcon(getImageIcon("/skins/" + skinsNames[((model.getTeamBlack().getPawnSkin() % (skinsNames.length+1)) + (skinsNames.length+1)) % (skinsNames.length+1) - 1]));
    }
    private void updateBlackRook(JLabel label){
        if (((model.getTeamBlack().getRookSkin() % (skinsNames.length+1)) + (skinsNames.length+1)) % (skinsNames.length+1) == 0) label.setIcon(getImageIcon("/images/rb.png"));
        else label.setIcon(getImageIcon("/skins/" + skinsNames[((model.getTeamBlack().getRookSkin() % (skinsNames.length+1)) + (skinsNames.length+1)) % (skinsNames.length+1) - 1]));
    }
    private void updateBlackKnight(JLabel label){
        if (((model.getTeamBlack().getKnightSkin() % (skinsNames.length+1)) + (skinsNames.length+1)) % (skinsNames.length+1) == 0) label.setIcon(getImageIcon("/images/nb.png"));
        else label.setIcon(getImageIcon("/skins/" + skinsNames[((model.getTeamBlack().getKnightSkin() % (skinsNames.length+1)) + (skinsNames.length+1)) % (skinsNames.length+1) - 1]));
    }
    private void updateBlackBishop(JLabel label){
        if (((model.getTeamBlack().getBishopSkin() % (skinsNames.length+1)) + (skinsNames.length+1)) % (skinsNames.length+1) == 0) label.setIcon(getImageIcon("/images/bb.png"));
        else label.setIcon(getImageIcon("/skins/" + skinsNames[((model.getTeamBlack().getBishopSkin() % (skinsNames.length+1)) + (skinsNames.length+1)) % (skinsNames.length+1) - 1]));
    }
    private void updateBlackQueen(JLabel label){
        if (((model.getTeamBlack().getQueenSkin() % (skinsNames.length+1)) + (skinsNames.length+1)) % (skinsNames.length+1) == 0) label.setIcon(getImageIcon("/images/qb.png"));
        else label.setIcon(getImageIcon("/skins/" + skinsNames[((model.getTeamBlack().getQueenSkin() % (skinsNames.length+1)) + (skinsNames.length+1)) % (skinsNames.length+1) - 1]));
    }
    private void updateBlackKing(JLabel label){
        if (((model.getTeamBlack().getKingSkin() % (skinsNames.length+1)) + (skinsNames.length+1)) % (skinsNames.length+1) == 0) label.setIcon(getImageIcon("/images/kb.png"));
        else label.setIcon(getImageIcon("/skins/" + skinsNames[((model.getTeamBlack().getKingSkin() % (skinsNames.length+1)) + (skinsNames.length+1)) % (skinsNames.length+1) - 1]));
    }
}

