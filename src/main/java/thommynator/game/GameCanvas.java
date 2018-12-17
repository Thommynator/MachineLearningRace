package thommynator.game;

import thommynator.App;

import javax.swing.*;
import java.awt.*;

public class GameCanvas extends JPanel {

    public GameCanvas() {
        super.setSize(App.MAP_WIDTH, App.MAP_HEIGHT);
        super.setVisible(true);
        super.setOpaque(false);
        super.setBackground(new Color(255, 255, 255, 255));
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(0, 0, 255, 128));
        setBackground(new Color(255, 255, 255, 255));
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        g.fillOval(50, 50, 200, 100);
    }
}