package thommynator;

import thommynator.game.Drawer;
import thommynator.game.Population;
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

        EventQueue.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }

    private void initUI() {
        add(new Drawer(new Population(200)));
        setSize(MAP_WIDTH + 10, MAP_HEIGHT + 30); // some buffer to make everything visible
        setBackground(Racetrack.BACKGROUND_COLOR);
        setResizable(false);
        setTitle("ML-Race");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
