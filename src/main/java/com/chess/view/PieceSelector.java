package com.chess.view;

import com.chess.model.chess.ChessModel;
import com.chess.model.chess.ChessTeam;
import com.chess.model.chess.ChessTeamIdentifier;
import com.chess.model.chess.ChessTypeIdentifier;
import com.chess.utils.Resources;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PieceSelector extends JPanel {

    private final ChessModel model;
    private final ChessTypeIdentifier pieceType;
    private final ChessTeamIdentifier teamColor;

    private static final String[] skinsNames = {"bird.png", "blueninja.png", "car.png", "eagle.png", "eyebat.png"};

    public PieceSelector(ChessModel model, ChessTypeIdentifier pieceType, ChessTeamIdentifier teamColor){
        //Initial setup for the constructor
        super();
        this.model = model;
        this.pieceType = pieceType;
        this.teamColor = teamColor;
        setLayout(new BorderLayout());

        //Creating and adding label at the top of the selector.
        JLabel label = new JLabel(selectName(), SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        add(label, BorderLayout.NORTH);

        //Creating and adding image to be displayed in the middle of the selector
        JLabel imageLabel = selectSkinImage();
        add(imageLabel, BorderLayout.CENTER);

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
        left.setIcon(Resources.getImageIcon("/images/left.png"));
        right.setIcon(Resources.getImageIcon("/images/right.png"));
        plus.setIcon(Resources.getImageIcon("/images/plus.png"));
        leftRightPanel.add(left, c);
        leftRightPanel.add(right, c);
        leftRightPanel.add(plus, c);
        add(leftRightPanel, BorderLayout.SOUTH);

        //Adding action listeners to all the buttons
        left.addActionListener((e) -> {
            model.getTeamManager().getTeam(teamColor).decSkinIndex(pieceType);
            updatePiece(imageLabel);
        });

        right.addActionListener((e) -> {
            model.getTeamManager().getTeam(teamColor).incSkinIndex(pieceType);
            updatePiece(imageLabel);
        });

        plus.addActionListener((e) -> {
            manageOwnSkin(imageLabel);
        });
    }

    // Util for constructor

    private String selectName() {
        String s;
        if(teamColor.equals(ChessTeamIdentifier.WHITE)) s = "White ";
        else s = "Black ";
        s = s.concat(selectPieceName());
        return s;
    }

    private String selectPieceName() {
        switch(pieceType){
            case ROOK: return "rook";
            case KNIGHT: return "knight";
            case BISHOP: return "bishop";
            case QUEEN: return "queen";
            case KING: return "king";
            default: return "pawn";
        }
    }

    private JLabel selectSkinImage(){
        JLabel imageLabel = new JLabel("", SwingConstants.CENTER);
        ChessTeam team = model.getTeamManager().getTeam(teamColor);
        if (!team.getOwnSkin(pieceType)) {
            if (team.getSkinIndex(pieceType) == 0) {
                imageLabel.setIcon(Resources.getImageIcon("/images/" + team.getSkin(pieceType)));
            } else {
                imageLabel.setIcon(Resources.getImageIcon("/skins/" + team.getSkin(pieceType)));
            }
        } else {
            //TODO: Behaves a bit mysteriously if a file is deleted or moved in between pop-ups. Could try to fix this some way.
            Image image = Toolkit.getDefaultToolkit().getImage(team.getSkin(pieceType));
            image = image.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(image));
        }
        return imageLabel;
    }

    // Util for action listeners

    private void updatePiece(JLabel label) {
        if (((model.getTeamManager().getTeam(teamColor).getSkinIndex(pieceType) % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) == 0) {
            label.setIcon(Resources.getImageIcon("/images/" + pieceType.toString() + teamColor.toString() + ".png"));
            model.getTeamManager().getTeam(teamColor).setSkinIndex(pieceType, 0);
            model.getTeamManager().getTeam(teamColor).setOwnSkin(pieceType, false);
            model.getTeamManager().getTeam(teamColor).setSkin(pieceType, pieceType.toString() + teamColor.toString() + ".png");
        } else {
            label.setIcon(Resources.getImageIcon("/skins/" + skinsNames[((model.getTeamManager().getTeam(teamColor).getSkinIndex(pieceType) % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) - 1]));
            model.getTeamManager().getTeam(teamColor).setSkinIndex(pieceType, ((model.getTeamManager().getTeam(teamColor).getSkinIndex(pieceType) % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1));
            model.getTeamManager().getTeam(teamColor).setOwnSkin(pieceType, false);
            model.getTeamManager().getTeam(teamColor).setSkin(pieceType, skinsNames[((model.getTeamManager().getTeam(teamColor).getSkinIndex(pieceType) % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) - 1]);
        }
    }

    private void manageOwnSkin(JLabel label) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose an image file");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int returnValue = fileChooser.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File chosenFile = fileChooser.getSelectedFile();
            String absolutePath = chosenFile.getAbsolutePath();

            //Loading image to be displayed if the image is not in png format
            ImageIcon imageIcon = Resources.getImageIcon("/images/filenotfound.png");
            Image oldImage = imageIcon.getImage();
            Image newImage = oldImage.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(newImage);

            //Checking if the selected file is in png format and then loading said image to the corresponding label.
            if (absolutePath.endsWith("png")) {
                Image image1 = Toolkit.getDefaultToolkit().getImage(absolutePath);
                Image image2 = image1.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                imageIcon = new ImageIcon(image2);
                saveNewFile(absolutePath);
            } else {
                label.setIcon(imageIcon);
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(new JFrame(), "The selected file is not in png format!");
            }

            label.setIcon(imageIcon);

        }
    }

    private void saveNewFile(String path){
        model.getTeamManager().getTeam(teamColor).setOwnSkin(pieceType, true);
        model.getTeamManager().getTeam(teamColor).setSkin(pieceType, path);
    }

}