package com.chess.view;

import javax.swing.*;

import com.chess.utils.Event;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

import com.chess.model.GameTime;
import com.chess.model.chess.ChessModel;
import com.chess.model.chess.SerialModel;

/**
 * A class representing the menu bar shown at the top of the window containing the GUI for the game.
 */
public class Menu extends JMenuBar {

    private String choosenSoundMap;

    //
    // Buttons
    //

    private final JMenuItem newGame;
    private final JMenuItem save;
    private final JMenuItem load;
    private final JMenuItem startServer;
    private final JMenuItem connectToServer;
    private final JMenuItem customizePieces;
    private final JMenuItem disconnect;
    private final JMenuItem classic;
    private final JMenuItem notClassic;

    //
    // Events
    //

    private Event<String> onStartServerEvent = new Event<>();
    private Event<String> onConnectToServerEvent = new Event<>();
    private Event<JMenuItem> onDisconnectEvent = new Event<>();
    private Event<SerialModel> onLoadGameEvent = new Event<>();

    /**
     * Constructor for Menu.
     * @param model A reference to the model containing information regarding the state of the game.
     */
    public Menu(ChessModel model) {
        super();

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

        newGame.addActionListener((e) -> {
            JFrame f = new JFrame();
            int answer = JOptionPane.showConfirmDialog(f, "Are you sure you want to start a new game?\nAny unsaved changes to the current state will be lost.", "", JOptionPane.YES_NO_OPTION);
            if(answer == JOptionPane.YES_OPTION) {
                try {
                    int input = Integer.parseInt(JOptionPane.showInputDialog("Minutes:", "5"));
                    GameTime newTime = new GameTime(input, 0, 0);
                    model.resetState(newTime);
                }
                catch(NullPointerException exc) {
                    exc.printStackTrace();
                }
            }
        });

        save.addActionListener((e) -> {
            boolean paused = model.getPaused();
            if(!paused) model.setPaused(true);
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
                    stream.writeObject(model.getSerialModel());
                    stream.flush();
                    stream.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if(!paused) model.setPaused(false);
        });

        load.addActionListener((e) -> {
            boolean paused = model.getPaused();
            if(!paused) model.setPaused(true);
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
                    onLoadGameEvent.trigger(newModel);
                    //view.getInfoPanel().getMovesPanel().loadMovesPanel();
                    stream.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                if(!paused) model.setPaused(false);
            }
        });
        

        //Creating view menu
        JMenu viewMenu = new JMenu("View");
        this.add(viewMenu);
        this.customizePieces = new JMenuItem("Customize Pieces");
        viewMenu.add(customizePieces);

        customizePieces.addActionListener((e) ->
            new PieceConfigurator(this, model)
        );

        //Creating sound menu
        JMenu soundMenu = new JMenu("Sound");
        this.add(soundMenu);
        this.classic = new JMenuItem("Classic");
        this.notClassic = new JMenuItem("Not Classic");
        soundMenu.add(this.classic);
        soundMenu.add(this.notClassic);

        this.choosenSoundMap = "classic";

        this.classic.addActionListener((e) ->
            this.choosenSoundMap = "classic"
        );
        this.notClassic.addActionListener((e) ->
            this.choosenSoundMap = "notClassic"
        );


        //Creating help menu
        JMenu help = new JMenu("Help");
        JMenuItem wikiLink = new JMenuItem("How to play chess");
        wikiLink.addActionListener((a) -> {
            try {
                URI site = new URI("https://www.dummies.com/article/home-auto-hobbies/games/board-games/chess/chess-for-dummies-cheat-sheet-208533");
                Desktop.getDesktop().browse(site);
            }
            catch(URISyntaxException | IOException e ) {
                e.printStackTrace();
            }
        });

        help.add(wikiLink);
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
                onStartServerEvent.trigger(port);
            }
        });

        connectToServer = new JMenuItem("Connect to server");
        connectToServer.addActionListener(e -> {
            JFrame f = new JFrame();
            
            String port = JOptionPane.showInputDialog(f, "Please enter the server:port to connect to:");
            if (port != null) {
                onConnectToServerEvent.trigger(port);
            }
        });

        disconnect = new JMenuItem("Disconnect");
        disconnect.addActionListener(e ->
            onDisconnectEvent.trigger(disconnect)
        );
        disconnect.setEnabled(false);

        server.add(startServer);
        server.add(connectToServer);
        server.add(disconnect);
    }

    /**
     * Gets the reference to the menu item used to start a new game.
     * @return A reference to the menu item used to start a new game.
     */
    public JMenuItem getNewGame() { return newGame; }

    /**
     * Gets the reference for the menu item used to start a server for hosting the game.
     * @return A reference for the menu item used to start a server for hosting the game.
     */
    public JMenuItem getStartServer() { return startServer; }

    /**
     * Gets the reference to the menu item used to connect to a hosted game.
     * @return A reference to the menu item used to connect to a hosted game.
     */
    public JMenuItem getConnectToServer() { return connectToServer; }

    /**
     * Gets the reference to the menu item used for disconnecting from a game and to stop hosting the game.
     * @return A reference to the menu item used for disconnecting from a game and to stop hosting the game.
     */
    public JMenuItem getDisconnect() { return disconnect; }

    /**
     * Gets the reference to the collection of events to be triggered when a game starts to be hosted.
     * @return A reference to the collection of events to be triggered when a game starts to be hosted.
     */
    public Event<String> getOnStartServerEvent() { return onStartServerEvent; }

    /**
     * Gets the reference to the collection of events to be triggered when a connection is made a hosted game.
     * @return A reference to the collection of events to be triggered when a connection is made a hosted game.
     */
    public Event<String> getOnConnectToServerEvent() { return onConnectToServerEvent; }

    /**
     * Gets the reference to the collection of events to be triggered when a hosted game is disconnected.
     * @return A reference to the collection of events to be triggered when a hosted game is disconnected.
     */
    public Event<JMenuItem> getOnDisconnectEvent() { return onDisconnectEvent; }

    /**
     * Gets the reference to the collection of events to be triggered when a new game is loaded by the host.
     * @return A reference to the collection of events to be triggered when a new game is loaded by the host.
     */
    public Event<SerialModel> getOnLoadGameEvent() { return onLoadGameEvent; }

    /**
     * Gets the reference to the String representing what sound map is currently selected.
     * @return A reference to the String representing what sound map is currently selected.
     */
    public String getSoundMap() {
        return this.choosenSoundMap;
    }
}
