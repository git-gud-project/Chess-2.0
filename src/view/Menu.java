package view;

import javax.swing.*;

import utils.Delegate;

import java.nio.file.*;
import java.io.*;

public class Menu extends JMenuBar {

    private ChessView view;
    private Delegate<Integer> startServerDelegate;
    private Delegate<Integer> connectToServerDelegate;

    private JMenuItem newGame;

    public void setStartServerDelegate(Delegate<Integer> startServerDelegate) {
        this.startServerDelegate = startServerDelegate;
    }

    public void setConnectToServerDelegate(Delegate<Integer> connectToServerDelegate) {
        this.connectToServerDelegate = connectToServerDelegate;
    }

    public Menu(ChessView view) {
        super();
        this.view = view;

        //Creating file menu
        JMenu file = new JMenu("File");
        this.add(file);
        this.newGame = new JMenuItem("New game");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem save = new JMenuItem("Save");
        file.add(newGame);
        file.add(new JSeparator());
        file.add(load);
        file.add(save);

        save.addActionListener((e) -> {
            String content = view.getModel().toFEN();

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save FEN");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setSelectedFile(new File("fen.txt"));
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            int returnValue = fileChooser.showSaveDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File chosenFile = fileChooser.getSelectedFile();
                try {
                    PrintWriter writer = new PrintWriter(chosenFile);
                    writer.print(content);
                    writer.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        load.addActionListener((e) -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Load FEN");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setSelectedFile(new File("fen.txt"));
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File chosenFile = fileChooser.getSelectedFile();
                try {
                    String content = Files.readString(chosenFile.toPath());
                    view.getModel().loadFEN(content);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        //Creating edit menu
        JMenu edit = new JMenu("Edit");
        //TODO: This menu could support functionality for pausing the game. If no other functionality for it is used it could also be removed.
        this.add(edit);

        //Creating view menu
        //TODO: Visual customization could be added to this part of the menu as part of the technical requirements for the project.
        JMenu viewMenu = new JMenu("View");
        this.add(viewMenu);

        //Creating help menu
        JMenu help = new JMenu("Help");
        //TODO: This menu could mostly serve to display miscellaneous information to the user upon request.
        this.add(help);

        //Creating server menu
        JMenu server = new JMenu("Network");
        this.add(server);
        JMenuItem startServer = new JMenuItem("Start server");
        startServer.addActionListener(e -> {
            JFrame f = new JFrame();
            String port = JOptionPane.showInputDialog(f, "Please select a port to start the server communication:");
            if (port != null) {
                startServerDelegate.invoke(Integer.parseInt(port));
            }
        });

        JMenuItem connectToServer = new JMenuItem("Connect to server");
        connectToServer.addActionListener(e -> {
            JFrame f = new JFrame();
            
            String port = JOptionPane.showInputDialog(f, "Please enter the port to connect to:");
            if (port != null) {
                connectToServerDelegate.invoke(Integer.parseInt(port));
            }
        });

        server.add(startServer);
        server.add(connectToServer);
    }

    public JMenuItem getNewGame() { return newGame; }

}
