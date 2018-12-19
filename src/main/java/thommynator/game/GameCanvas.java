package thommynator.game;

import thommynator.App;

import javax.swing.*;
import java.awt.*;

public class GameCanvas extends JPanel {

    public GameCanvas() {
        setSize(App.MAP_WIDTH, App.MAP_HEIGHT);
        setOpaque(false);
        setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0, 0, 255, 200));
        g.fillOval(50, 50, 200, 100);
    }
}