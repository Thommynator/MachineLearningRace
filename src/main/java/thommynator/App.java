package thommynator;

import thommynator.game.GameCanvas;
import thommynator.game.Racetrack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicInteger;

public class App extends JFrame {

    public static final int MAP_WIDTH = 800;
    public static final int MAP_HEIGHT = 600;
    public static final Racetrack RACETRACK = new Racetrack();
    public static final int INITIAL_X = 30;
    public static final int INITIAL_Y = 30;
    public static final AtomicInteger CAR_ID_FACTORY = new AtomicInteger(0);


    public App() {
        initUI();
    }

    public static void main(String[] args) {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG");
        App app = new App();
    }


    private void initUI() {
        super.setTitle("ML-Race");
        MenuBar menuBar = new MenuBar();
        Menu raceMenu = new java.awt.Menu("Race");
        raceMenu.add(new MenuItem("Start"));
        raceMenu.add(new MenuItem("Stop"));
        menuBar.add(raceMenu);

        Menu neuralNetMenu = new java.awt.Menu("Neural Net");
        neuralNetMenu.add(new MenuItem("Save best..."));
        neuralNetMenu.add(new MenuItem("Load best..."));

        menuBar.add(neuralNetMenu);
        super.setMenuBar(menuBar);

        // the smaller the depth-number, the more it is at the top, i.e. big numbers are getting covered by small numbers
        JLayeredPane pane = new JLayeredPane();
        pane.add(new Racetrack(), 0);
        pane.add(new GameCanvas(), 1);
        pane.setOpaque(false);
        pane.setVisible(true);
        pane.setSize(new Dimension(App.MAP_WIDTH, App.MAP_HEIGHT));
        super.add(pane);

        super.setLayout(null);
        super.setPreferredSize(new Dimension(App.MAP_WIDTH, App.MAP_HEIGHT));
        super.setBounds(new Rectangle(App.MAP_WIDTH, App.MAP_HEIGHT));
        super.setVisible(true);
        super.addWindowListener(new WindowAdapter() {
                                    public void windowClosing(WindowEvent we) {
                                        dispose();
                                    }
                                }
        );


//        add(new Drawer(new Population(200)));
//        setSize(MAP_WIDTH + 10, MAP_HEIGHT + 30); // some buffer to make everything visible
//        setBackground(Racetrack.BACKGROUND_COLOR);
//        setResizable(false);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
    }
}
