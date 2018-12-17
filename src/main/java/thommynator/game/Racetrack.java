package thommynator.game;

import lombok.Data;
import thommynator.App;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

@Data
public class Racetrack extends Canvas implements MouseMotionListener {

    public static final Color FOREGROUND_COLOR = new Color(100, 100, 100);
    public static final Color BACKGROUND_COLOR = new Color(200, 200, 200);

    public static final int ROAD_WIDTH = 50;
    List<Point2D> anchorPoints;
    BufferedImage image;

    public Racetrack() {
        super.addMouseMotionListener(this);
        super.setBackground(BACKGROUND_COLOR);
        super.setSize(App.MAP_WIDTH, App.MAP_HEIGHT);

        anchorPoints = Arrays.asList(
                new Point2D.Double(0.0, 0.0),
                new Point2D.Double(0.7 * App.MAP_WIDTH, 0.2 * App.MAP_HEIGHT),
                new Point2D.Double(0.5 * App.MAP_WIDTH, 0.4 * App.MAP_HEIGHT),
                new Point2D.Double(App.MAP_WIDTH, App.MAP_HEIGHT));
        image = createTrack();
    }

    public BufferedImage createTrack() {
        BufferedImage img = new BufferedImage(App.MAP_WIDTH, App.MAP_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D graphics = img.createGraphics();
        graphics.setColor(BACKGROUND_COLOR);
        graphics.fillRect(0, 0, img.getWidth(), img.getHeight());
        graphics.setColor(FOREGROUND_COLOR);
        graphics.setStroke(new BasicStroke(ROAD_WIDTH));
        for (int i = 0; i < anchorPoints.size() - 1; i++) {
            graphics.draw(new Line2D.Double(anchorPoints.get(i), anchorPoints.get(i + 1)));
        }
        return img;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Graphics graphics = getGraphics();
        graphics.setColor(FOREGROUND_COLOR);
        int w = ROAD_WIDTH;
        int x = e.getX() - (int) (w / 2.0);
        int y = e.getY() - (int) (w / 2.0);
        graphics.fillOval(x, y, w, w);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // do nothing
    }
}
