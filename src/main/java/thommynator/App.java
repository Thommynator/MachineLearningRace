package thommynator;

import thommynator.Game.Drawer;
import thommynator.Game.Population;
import thommynator.Game.Racetrack;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class App extends JFrame {

    public final static int MAP_WIDTH = 800;
    public final static int MAP_HEIGHT = 600;
    public static Racetrack racetrack;
    public final static int INITIAL_X = 30;
    public final static int INITIAL_Y = 30;
    public final static AtomicInteger CAR_ID_FACTORY = new AtomicInteger(0);


    public App() {
        initUI();
        racetrack = new Racetrack();
        racetrack.createTrack();
    }

    public static void main(String[] args) {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG");

        EventQueue.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }

    private void initUI() {
        add(new Drawer(new Population(300)));
        setSize(MAP_WIDTH, MAP_HEIGHT);
        setBackground(new Color(200, 200, 200));
        setResizable(false);
        setTitle("ML-Race");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
