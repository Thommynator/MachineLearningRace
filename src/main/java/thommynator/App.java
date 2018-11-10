package thommynator;

import thommynator.Game.Canvas;

import javax.swing.*;
import java.awt.*;

/**
 * Hello world!
 */
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
        add(new Canvas());
        setSize(MAP_WIDTH, MAP_HEIGHT);
        setTitle("ML-Race");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
