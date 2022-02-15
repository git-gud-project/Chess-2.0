package com.chess.view;

import javax.swing.*;

import com.chess.utils.Event;

import java.awt.event.KeyEvent;
import java.io.*;

import com.chess.model.*;

public class Menu extends JMenuBar {

    private ChessView view;

    private String choosenSoundMap;

    //
    // Buttons
    //

    private JMenuItem newGame;
    private JMenuItem save;
    private JMenuItem load;
    private JMenuItem startServer;
    private JMenuItem connectToServer;
    private JMenuItem customizePieces;
    private JMenuItem disconnect;
    private JMenuItem classic;
    private JMenuItem notClassic;

    //
    // Events
    //

    private Event<String> onStartServerEvent = new Event<>();
    private Event<String> onConnectToServerEvent = new Event<>();
    private Event<JMenuItem> onDisconnectEvent = new Event<>();
    private Event<SerialModel> onLoadGameEvent = new Event<>();

    public Menu(ChessView view) {
        super();
        this.view = view;

        //Creating file menu
        JMenu file = new JMenu("File");
        this.add(file);
        this.newGame = new JMenuItem("New game");
        this.newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        this.load = new JMenuItem("Load");
        this.load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        this.save = new JMenuItem("Save");
        this.save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        file.add(newGame);
        file.add(new JSeparator());
        file.add(load);
        file.add(save);

        save.addActionListener((e) -> {
            boolean paused = view.getModel().getPaused();
            if(!paused) view.getModel().setPaused(true);
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            int returnValue = fileChooser.showSaveDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION){
                File chosenFile = fileChooser.getSelectedFile();
                try {
                    FileOutputStream fOut = new FileOutputStream(chosenFile);
                    ObjectOutputStream stream = new ObjectOutputStream(fOut);
                    stream.writeObject(new SerialModel(view.getModel()));
                    stream.flush();
                    stream.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if(!paused) view.getModel().setPaused(false);
        });

        load.addActionListener((e) -> {
            boolean paused = view.getModel().getPaused();
            if(!paused) view.getModel().setPaused(true);
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Load");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            int returnValue = fileChooser.showOpenDialog(null);

            if(returnValue == JFileChooser.APPROVE_OPTION){
                try {
                    File chosenFile = fileChooser.getSelectedFile();
                    FileInputStream fIn = new FileInputStream(chosenFile);
                    ObjectInputStream stream = new ObjectInputStream(fIn);
                    SerialModel newModel = (SerialModel) stream.readObject();
                    onLoadGameEvent.invoke(newModel);
                    stream.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                if(!paused) view.getModel().setPaused(false);
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
        this.customizePieces = new JMenuItem("Customize Pieces");
        viewMenu.add(customizePieces);

        customizePieces.addActionListener((e) -> {
            new PieceConfigurator(view);
        });

        //Creating sound menu
        JMenu soundMenu = new JMenu("Sound");
        this.add(soundMenu);
        this.classic = new JMenuItem("Classic");
        this.notClassic = new JMenuItem("Not Classic");
        soundMenu.add(this.classic);
        soundMenu.add(this.notClassic);

        this.choosenSoundMap = "classic";

        this.classic.addActionListener((e) -> {
            this.choosenSoundMap = "classic";
        });
        this.notClassic.addActionListener((e) -> {
            this.choosenSoundMap = "notClassic";
        });


        //Creating help menu
        JMenu help = new JMenu("Help");
        //TODO: This menu could mostly serve to display miscellaneous information to the user upon request.
        this.add(help);

        //
        // Create network menu
        //

        JMenu server = new JMenu("Network");
        this.add(server);
        startServer = new JMenuItem("Start server");
        startServer.addActionListener(e -> {
            JFrame f = new JFrame();
            String port = JOptionPane.showInputDialog(f, "Please select a port to start the server communication:");
            if (port != null) {
                onStartServerEvent.invoke(port);
            }
        });

        connectToServer = new JMenuItem("Connect to server");
        connectToServer.addActionListener(e -> {
            JFrame f = new JFrame();
            
            String port = JOptionPane.showInputDialog(f, "Please enter the server:port to connect to:");
            if (port != null) {
                onConnectToServerEvent.invoke(port);
            }
        });

        disconnect = new JMenuItem("Disconnect");
        disconnect.addActionListener(e -> {
            onDisconnectEvent.invoke(disconnect);
        });
        disconnect.setEnabled(false);

        server.add(startServer);
        server.add(connectToServer);
        server.add(disconnect);
    }

    public JMenuItem getNewGame() { return newGame; }

    public JMenuItem getSave() { return save; }

    public JMenuItem getLoad() { return load; }

    public JMenuItem getStartServer() { return startServer; }

    public JMenuItem getConnectToServer() { return connectToServer; }

    public JMenuItem getDisconnect() { return disconnect; }
    
    public Event<String> getOnStartServerEvent() { return onStartServerEvent; }

    public Event<String> getOnConnectToServerEvent() { return onConnectToServerEvent; }

    public Event<JMenuItem> getOnDisconnectEvent() { return onDisconnectEvent; }
    
    public Event<SerialModel> getOnLoadGameEvent() { return onLoadGameEvent; }

    public String getSoundMap() {
        return this.choosenSoundMap;
    }
}
