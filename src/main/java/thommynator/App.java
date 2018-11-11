package thommynator;

import thommynator.Game.Population;
import thommynator.Game.PopulationDrawer;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    public final static int MAP_WIDTH = 800;
    public final static int MAP_HEIGHT = 600;

    public App() {
        initUI();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }

    private void initUI() {
        add(new PopulationDrawer(new Population(50)));
        setSize(MAP_WIDTH, MAP_HEIGHT);
        setResizable(false);
        setTitle("ML-Race");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
