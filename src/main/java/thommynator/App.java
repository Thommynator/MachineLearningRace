package thommynator;

import thommynator.game.GameCanvas;
import thommynator.game.Racetrack;

import javax.swing.*;
import java.awt.*;
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
        setTitle("ML-Race");

        // add a menu at the top
        JMenuBar menuBar = new JMenuBar();
        JMenu raceMenu = new JMenu("Race");
        raceMenu.add(new JMenuItem("Start"));
        raceMenu.add(new JMenuItem("Stop"));
        menuBar.add(raceMenu);
        JMenu neuralNetMenu = new JMenu("Neural Net");
        neuralNetMenu.add(new JMenuItem("Save best..."));
        neuralNetMenu.add(new JMenuItem("Load best..."));
        menuBar.add(neuralNetMenu);
        setJMenuBar(menuBar);

        // add the content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.add(new GameCanvas());
        contentPanel.add(new Racetrack());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(contentPanel);
        setPreferredSize(new Dimension(App.MAP_WIDTH, App.MAP_HEIGHT));
        setBounds(new Rectangle(App.MAP_WIDTH, App.MAP_HEIGHT));
        setVisible(true);

        // close window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        add(new Drawer(new Population(200)));
//        setSize(MAP_WIDTH + 10, MAP_HEIGHT + 30); // some buffer to make everything visible
//        setBackground(Racetrack.BACKGROUND_COLOR);
//        setResizable(false);
//        setLocationRelativeTo(null);
    }
}
