package view;

import javax.swing.*;

public class Menu extends JMenuBar {

    public Menu() {
        super();

        //Creating file menu
        JMenu file = new JMenu("File");
        this.add(file);
        JMenuItem newGame = new JMenuItem("New game");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem save = new JMenuItem("Save");
        file.add(newGame);
        file.add(load);
        file.add(save);

        //Creating edit menu
        JMenu edit = new JMenu("Edit");
        this.add(edit);

        //Creating view menu
        JMenu view = new JMenu("View");
        this.add(view);

        //Creating help menu
        JMenu help = new JMenu("Help");
        this.add(help);

        //Creating server menu
        JMenu server = new JMenu("Server");
        this.add(server);
        JMenuItem startServer = new JMenuItem("Start server");
        server.add(startServer);
    }

}
