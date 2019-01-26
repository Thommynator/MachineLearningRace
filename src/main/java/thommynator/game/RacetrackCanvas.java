package thommynator.game;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

@Data
@EqualsAndHashCode(callSuper = true)
public class RacetrackCanvas extends JPanel implements MouseMotionListener {

    private Racetrack racetrack;

    public RacetrackCanvas(Racetrack racetrack) {
        this.racetrack = racetrack;
        addMouseMotionListener(this);
        setBackground(racetrack.OFFROAD_COLOR);
        setSize(racetrack.getRacetrack().getWidth(), racetrack.getRacetrack().getHeight());
        setOpaque(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(racetrack.getRacetrack(), 0, 0, null);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        Graphics graphics = racetrack.getRacetrack().createGraphics();
        if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
            graphics.setColor(racetrack.ROAD_COLOR);
        } else if (SwingUtilities.isRightMouseButton(mouseEvent)) {
            graphics.setColor(racetrack.OFFROAD_COLOR);
        }

        int w = racetrack.ROAD_WIDTH;
        int x = mouseEvent.getX() - (int) (w / 2.0);
        int y = mouseEvent.getY() - (int) (w / 2.0);
        graphics.fillOval(x, y, w, w);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // do nothing
    }
}
