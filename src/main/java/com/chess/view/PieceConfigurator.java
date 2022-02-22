package com.chess.view;

import javax.swing.*;

import com.chess.model.chess.ChessModel;
import com.chess.model.chess.ChessTeam;
import com.chess.model.chess.PieceType;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;
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
        this.frame.setUndecorated(true);
        this.frame.setAlwaysOnTop(true);
        this.setLayout(new BorderLayout());
        this.frame.add(this);
        setupUI();
        this.frame.setSize(500, 500);
        this.frame.setLocation((int) view.getLocation().getX() + (view.getWidth() - this.frame.getWidth()) / 2,
                (int) view.getLocation().getY() + (view.getHeight() - this.frame.getHeight()) / 2);
        this.frame.setVisible(true);
    }

    private void setupUI() {

        //Creating a local copy of each team's instance to roll back any performed changes that were not committed.
        ChessTeam teamWhite = model.getTeamWhite().cloneTeam();
        ChessTeam teamBlack = model.getTeamBlack().cloneTeam();

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
            for(int i = 0; i < model.getBoard().getGameSize(); i++){
                for(int j = 0; j < model.getBoard().getGameSize(); j++){
                    model.getBoard().getCell(i, j).getOnPieceChangedEvent().trigger(model.getBoard().getCell(i,j).getPiece());
                }
            }
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });
        cancel.addActionListener((e) -> {
            model.setTeamWhite(teamWhite);
            model.setTeamBlack(teamBlack);
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
        if (!getOwnSkin(i * 3 + j)) {
            if (getSkinIndex(i * 3 + j) == 0) {
                imageLabel.setIcon(getImageIcon("/images/" + getFileName(i * 3 + j)));
            } else {
                imageLabel.setIcon(getImageIcon("/skins/" + getFileName(i * 3 + j)));
            }
        } else {
            //TODO: Behaves a bit mysteriously if a file is deleted or moved in between pop-ups. Could try to fix this some way.
            Image image = Toolkit.getDefaultToolkit().getImage(getFileName(i*3 + j));
            image = image.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(image));
        }
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
            handleRightClick(i * 3 + j, imageLabel);
        });

        left.addActionListener((e) -> {
            handleLeftClick(i * 3 + j, imageLabel);
        });

        plus.addActionListener((e) -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choose an image file");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            int returnValue = fileChooser.showSaveDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION){
                File chosenFile = fileChooser.getSelectedFile();
                String absolutePath = chosenFile.getAbsolutePath();

                //Loading image to be displayed if the image is not in png format
                ImageIcon imageIcon = getImageIcon("/images/filenotfound.png");
                Image oldImage = imageIcon.getImage();
                Image newImage = oldImage.getScaledInstance(60,60, Image.SCALE_SMOOTH);
                imageIcon = new ImageIcon(newImage);

                //Checking if the selected file is in png format and then loading said image to the corresponding label.
                if(absolutePath.substring(absolutePath.length() - 3).equals("png")){
                    Image image1 = Toolkit.getDefaultToolkit().getImage(absolutePath);
                    Image image2 = image1.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                    imageIcon = new ImageIcon(image2);
                    saveNewFile(i*3 + j, absolutePath);
                } else {
                    imageLabel.setIcon(imageIcon);
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(new JFrame(), "The selected file is not in png format!");
                }

                imageLabel.setIcon(imageIcon);

            }
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
            default:
                break;
        }
    }

    private void handleLeftClick(int n, JLabel label) {
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
            default:
                break;
        }
    }

    private void updateWhitePawn(JLabel label) {
        if (((model.getTeamWhite().getPawnSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) == 0) {
            label.setIcon(getImageIcon("/images/pw.png"));
            model.getTeamWhite().setSkinIndex(0, 0);
            model.getTeamWhite().setOwnSkin(0, false);
            model.getTeamWhite().setSkin(PieceType.PAWN, fileNames[0][0]);
        } else {
            label.setIcon(getImageIcon("/skins/" + skinsNames[((model.getTeamWhite().getPawnSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) - 1]));
            model.getTeamWhite().setSkinIndex(0, ((model.getTeamWhite().getPawnSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1));
            model.getTeamWhite().setOwnSkin(0, false);
            model.getTeamWhite().setSkin(PieceType.PAWN, skinsNames[((model.getTeamWhite().getPawnSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) - 1]);
        }
    }

    private void updateWhiteRook(JLabel label) {
        if (((model.getTeamWhite().getRookSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) == 0) {
            label.setIcon(getImageIcon("/images/rw.png"));
            model.getTeamWhite().setSkinIndex(1, 0);
            model.getTeamWhite().setOwnSkin(1, false);
            model.getTeamWhite().setSkin(PieceType.ROOK, fileNames[0][1]);
        } else {
            label.setIcon(getImageIcon("/skins/" + skinsNames[((model.getTeamWhite().getRookSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) - 1]));
            model.getTeamWhite().setSkinIndex(1, ((model.getTeamWhite().getRookSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1));
            model.getTeamWhite().setOwnSkin(1, false);
            model.getTeamWhite().setSkin(PieceType.ROOK, skinsNames[((model.getTeamWhite().getRookSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) - 1]);
        }
    }

    private void updateWhiteKnight(JLabel label) {
        if (((model.getTeamWhite().getKnightSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) == 0) {
            label.setIcon(getImageIcon("/images/nw.png"));
            model.getTeamWhite().setSkinIndex(2, 0);
            model.getTeamWhite().setOwnSkin(2, false);
            model.getTeamWhite().setSkin(PieceType.KNIGHT, fileNames[0][2]);
        } else {
            label.setIcon(getImageIcon("/skins/" + skinsNames[((model.getTeamWhite().getKnightSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) - 1]));
            model.getTeamWhite().setSkinIndex(2, ((model.getTeamWhite().getKnightSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1));
            model.getTeamWhite().setOwnSkin(2, false);
            model.getTeamWhite().setSkin(PieceType.KNIGHT, skinsNames[((model.getTeamWhite().getKnightSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) - 1]);
        }
    }

    private void updateWhiteBishop(JLabel label) {
        if (((model.getTeamWhite().getBishopSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) == 0) {
            label.setIcon(getImageIcon("/images/bw.png"));
            model.getTeamWhite().setSkinIndex(3, 0);
            model.getTeamWhite().setOwnSkin(3, false);
            model.getTeamWhite().setSkin(PieceType.BISHOP, fileNames[1][0]);
        } else {
            label.setIcon(getImageIcon("/skins/" + skinsNames[((model.getTeamWhite().getBishopSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) - 1]));
            model.getTeamWhite().setSkinIndex(3, ((model.getTeamWhite().getBishopSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1));
            model.getTeamWhite().setOwnSkin(3, false);
            model.getTeamWhite().setSkin(PieceType.BISHOP, skinsNames[((model.getTeamWhite().getBishopSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) - 1]);
        }
    }

    private void updateWhiteQueen(JLabel label) {
        if (((model.getTeamWhite().getQueenSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) == 0) {
            label.setIcon(getImageIcon("/images/qw.png"));
            model.getTeamWhite().setSkinIndex(4, 0);
            model.getTeamWhite().setOwnSkin(4, false);
            model.getTeamWhite().setSkin(PieceType.QUEEN, fileNames[1][1]);
        } else {
            label.setIcon(getImageIcon("/skins/" + skinsNames[((model.getTeamWhite().getQueenSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) - 1]));
            model.getTeamWhite().setSkinIndex(4, ((model.getTeamWhite().getQueenSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1));
            model.getTeamWhite().setOwnSkin(4, false);
            model.getTeamWhite().setSkin(PieceType.QUEEN, skinsNames[((model.getTeamWhite().getQueenSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) - 1]);
        }
    }

    private void updateWhiteKing(JLabel label) {
        if (((model.getTeamWhite().getKingSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) == 0) {
            label.setIcon(getImageIcon("/images/kw.png"));
            model.getTeamWhite().setSkinIndex(5, 0);
            model.getTeamWhite().setOwnSkin(5, false);
            model.getTeamWhite().setSkin(PieceType.KING, fileNames[1][2]);
        } else {
            label.setIcon(getImageIcon("/skins/" + skinsNames[((model.getTeamWhite().getKingSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) - 1]));
            model.getTeamWhite().setSkinIndex(5, ((model.getTeamWhite().getKingSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1));
            model.getTeamWhite().setOwnSkin(5, false);
            model.getTeamWhite().setSkin(PieceType.KING, skinsNames[((model.getTeamWhite().getKingSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) - 1]);
        }
    }

    private void updateBlackPawn(JLabel label) {
        if (((model.getTeamBlack().getPawnSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) == 0) {
            label.setIcon(getImageIcon("/images/pb.png"));
            model.getTeamBlack().setSkinIndex(0, 0);
            model.getTeamBlack().setOwnSkin(0, false);
            model.getTeamBlack().setSkin(PieceType.PAWN, fileNames[2][0]);
        } else {
            label.setIcon(getImageIcon("/skins/" + skinsNames[((model.getTeamBlack().getPawnSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) - 1]));
            model.getTeamBlack().setSkinIndex(0, ((model.getTeamBlack().getPawnSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1));
            model.getTeamBlack().setOwnSkin(0, false);
            model.getTeamBlack().setSkin(PieceType.PAWN, skinsNames[((model.getTeamBlack().getPawnSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) - 1]);
        }
    }

    private void updateBlackRook(JLabel label) {
        if (((model.getTeamBlack().getRookSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) == 0) {
            label.setIcon(getImageIcon("/images/rb.png"));
            model.getTeamBlack().setSkinIndex(1, 0);
            model.getTeamBlack().setOwnSkin(1, false);
            model.getTeamBlack().setSkin(PieceType.ROOK, fileNames[2][1]);
        } else {
            label.setIcon(getImageIcon("/skins/" + skinsNames[((model.getTeamBlack().getRookSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) - 1]));
            model.getTeamBlack().setSkinIndex(1, ((model.getTeamBlack().getRookSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1));
            model.getTeamBlack().setOwnSkin(1, false);
            model.getTeamBlack().setSkin(PieceType.ROOK, skinsNames[((model.getTeamBlack().getRookSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) - 1]);
        }
    }

    private void updateBlackKnight(JLabel label) {
        if (((model.getTeamBlack().getKnightSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) == 0) {
            label.setIcon(getImageIcon("/images/nb.png"));
            model.getTeamBlack().setSkinIndex(2, 0);
            model.getTeamBlack().setOwnSkin(2, false);
            model.getTeamBlack().setSkin(PieceType.KNIGHT, fileNames[2][2]);
        } else {
            label.setIcon(getImageIcon("/skins/" + skinsNames[((model.getTeamBlack().getKnightSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) - 1]));
            model.getTeamBlack().setSkinIndex(2, ((model.getTeamBlack().getKnightSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1));
            model.getTeamBlack().setOwnSkin(2, false);
            model.getTeamBlack().setSkin(PieceType.KNIGHT, skinsNames[((model.getTeamBlack().getKnightSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) - 1]);
        }
    }

    private void updateBlackBishop(JLabel label) {
        if (((model.getTeamBlack().getBishopSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) == 0) {
            label.setIcon(getImageIcon("/images/bb.png"));
            model.getTeamBlack().setSkinIndex(3, 0);
            model.getTeamBlack().setOwnSkin(3, false);
            model.getTeamBlack().setSkin(PieceType.BISHOP, fileNames[3][0]);
        } else {
            label.setIcon(getImageIcon("/skins/" + skinsNames[((model.getTeamBlack().getBishopSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) - 1]));
            model.getTeamBlack().setSkinIndex(3, ((model.getTeamBlack().getBishopSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1));
            model.getTeamBlack().setOwnSkin(3, false);
            model.getTeamBlack().setSkin(PieceType.BISHOP, skinsNames[((model.getTeamBlack().getBishopSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) - 1]);
        }
    }

    private void updateBlackQueen(JLabel label) {
        if (((model.getTeamBlack().getQueenSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) == 0) {
            label.setIcon(getImageIcon("/images/qb.png"));
            model.getTeamBlack().setSkinIndex(4, 0);
            model.getTeamBlack().setOwnSkin(4, false);
            model.getTeamBlack().setSkin(PieceType.QUEEN, fileNames[3][1]);
        } else {
            label.setIcon(getImageIcon("/skins/" + skinsNames[((model.getTeamBlack().getQueenSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) - 1]));
            model.getTeamBlack().setSkinIndex(4, ((model.getTeamBlack().getQueenSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1));
            model.getTeamBlack().setOwnSkin(4, false);
            model.getTeamBlack().setSkin(PieceType.QUEEN, skinsNames[((model.getTeamBlack().getQueenSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) - 1]);
        }
    }

    private void updateBlackKing(JLabel label) {
        if (((model.getTeamBlack().getKingSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) == 0) {
            label.setIcon(getImageIcon("/images/kb.png"));
            model.getTeamBlack().setSkinIndex(5, 0);
            model.getTeamBlack().setOwnSkin(5, false);
            model.getTeamBlack().setSkin(PieceType.KING, fileNames[3][2]);
        } else {
            label.setIcon(getImageIcon("/skins/" + skinsNames[((model.getTeamBlack().getKingSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) - 1]));
            model.getTeamBlack().setSkinIndex(5, ((model.getTeamBlack().getKingSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1));
            model.getTeamBlack().setOwnSkin(5, false);
            model.getTeamBlack().setSkin(PieceType.KING, skinsNames[((model.getTeamBlack().getKingSkin() % (skinsNames.length + 1)) + (skinsNames.length + 1)) % (skinsNames.length + 1) - 1]);
        }
    }

    private String getFileName(int n) {
        switch (n) {
            case 0:
                return model.getTeamWhite().getSkin(PieceType.PAWN);
            case 1:
                return model.getTeamWhite().getSkin(PieceType.ROOK);
            case 2:
                return model.getTeamWhite().getSkin(PieceType.KNIGHT);
            case 3:
                return model.getTeamWhite().getSkin(PieceType.BISHOP);
            case 4:
                return model.getTeamWhite().getSkin(PieceType.QUEEN);
            case 5:
                return model.getTeamWhite().getSkin(PieceType.KING);
            case 6:
                return model.getTeamBlack().getSkin(PieceType.PAWN);
            case 7:
                return model.getTeamBlack().getSkin(PieceType.ROOK);
            case 8:
                return model.getTeamBlack().getSkin(PieceType.KNIGHT);
            case 9:
                return model.getTeamBlack().getSkin(PieceType.BISHOP);
            case 10:
                return model.getTeamBlack().getSkin(PieceType.QUEEN);
            case 11:
                return model.getTeamBlack().getSkin(PieceType.KING);
            default:
                return null;
        }
    }

    private int getSkinIndex(int n) {
        if (n < 6) {
            return model.getTeamWhite().getSkinIndex(n);
        } else {
            return model.getTeamBlack().getSkinIndex(n - 6);
        }
    }

    private boolean getOwnSkin(int n) {
        if (n < 6) {
            return model.getTeamWhite().getOwnSkin(n);
        } else {
            return model.getTeamBlack().getOwnSkin(n - 6);
        }
    }

    private void saveNewFile(int n, String path){
        switch(n){
            case 0: model.getTeamWhite().setOwnSkin(0, true); model.getTeamWhite().setSkin(PieceType.PAWN, path); break;
            case 1: model.getTeamWhite().setOwnSkin(1, true); model.getTeamWhite().setSkin(PieceType.ROOK, path); break;
            case 2: model.getTeamWhite().setOwnSkin(2, true); model.getTeamWhite().setSkin(PieceType.KNIGHT, path); break;
            case 3: model.getTeamWhite().setOwnSkin(3, true); model.getTeamWhite().setSkin(PieceType.BISHOP, path); break;
            case 4: model.getTeamWhite().setOwnSkin(4, true); model.getTeamWhite().setSkin(PieceType.QUEEN, path); break;
            case 5: model.getTeamWhite().setOwnSkin(5, true); model.getTeamWhite().setSkin(PieceType.KING, path); break;
            case 6: model.getTeamBlack().setOwnSkin(0, true); model.getTeamBlack().setSkin(PieceType.PAWN, path); break;
            case 7: model.getTeamBlack().setOwnSkin(1, true); model.getTeamBlack().setSkin(PieceType.ROOK, path); break;
            case 8: model.getTeamBlack().setOwnSkin(2, true); model.getTeamBlack().setSkin(PieceType.KNIGHT, path); break;
            case 9: model.getTeamBlack().setOwnSkin(3, true); model.getTeamBlack().setSkin(PieceType.BISHOP, path); break;
            case 10: model.getTeamBlack().setOwnSkin(4, true); model.getTeamBlack().setSkin(PieceType.QUEEN, path); break;
            case 11: model.getTeamBlack().setOwnSkin(5, true); model.getTeamBlack().setSkin(PieceType.KING, path); break;
            default: break;
        }
    }
}

