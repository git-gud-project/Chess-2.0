package view;

import javax.swing.*;

import utils.Delegate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JMenuBar {

    private Delegate<Integer> _startServerDelegate;
    private Delegate<Integer> _connectToServerDelegate;

    public void setStartServerDelegate(Delegate<Integer> startServerDelegate) {
        _startServerDelegate = startServerDelegate;
    }

    public void setConnectToServerDelegate(Delegate<Integer> connectToServerDelegate) {
        _connectToServerDelegate = connectToServerDelegate;
    }

    public Menu() {
        super();

        //Creating file menu
        JMenu file = new JMenu("File");
        this.add(file);
        JMenuItem newGame = new JMenuItem("New game");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem save = new JMenuItem("Save");
        file.add(newGame);
        file.add(new JSeparator());
        file.add(load);
        file.add(save);

        //Creating edit menu
        JMenu edit = new JMenu("Edit");
        //TODO: This menu could support functionality for pausing the game. If no other functionality for it is used it could also be removed.
        this.add(edit);

        //Creating view menu
        //TODO: Visual customization could be added to this part of the menu as part of the technical requirements for the project.
        JMenu view = new JMenu("View");
        this.add(view);

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
            //TODO: The input value of the pop-up dialog is discarded. To use it, add a third parameter to the function call below. The input value will be stored in this variable.
            String port = JOptionPane.showInputDialog(f, "Please select a port to start the server communication:");
            if (port != null) {
                _startServerDelegate.invoke(Integer.parseInt(port));
            }
        });

        JMenuItem connectToServer = new JMenuItem("Connect to server");
        connectToServer.addActionListener(e -> {
            JFrame f = new JFrame();
            
            String port = JOptionPane.showInputDialog(f, "Please enter the port to connect to:");
            if (port != null) {
                _connectToServerDelegate.invoke(Integer.parseInt(port));
            }
        });

        server.add(startServer);
        server.add(connectToServer);
    }

}
