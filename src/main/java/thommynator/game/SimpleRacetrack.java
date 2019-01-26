package thommynator.game;

import thommynator.App;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

public class SimpleRacetrack implements Racetrack {

    private List<Point2D> anchorPoints;
    private BufferedImage image;

    public SimpleRacetrack() {
        this.anchorPoints = Arrays.asList(
                new Point2D.Double(0.0, 0.0),
                new Point2D.Double(0.7 * App.MAP_WIDTH, 0.2 * App.MAP_HEIGHT),
                new Point2D.Double(0.5 * App.MAP_WIDTH, 0.4 * App.MAP_HEIGHT),
                new Point2D.Double(App.MAP_WIDTH, App.MAP_HEIGHT));
        image = createTrack();
    }

    public SimpleRacetrack(List<Point2D> anchorPoints) {
        this.anchorPoints = anchorPoints;
    }

    @Override
    public BufferedImage getRacetrack() {
        return this.image;
    }

    private BufferedImage createTrack() {
        BufferedImage img = new BufferedImage(App.MAP_WIDTH, App.MAP_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D graphics = img.createGraphics();
        graphics.setColor(OFFROAD_COLOR);
        graphics.fillRect(0, 0, img.getWidth(), img.getHeight());
        graphics.setColor(ROAD_COLOR);
        graphics.setStroke(new BasicStroke(ROAD_WIDTH));
        for (int i = 0; i < anchorPoints.size() - 1; i++) {
            graphics.draw(new Line2D.Double(anchorPoints.get(i), anchorPoints.get(i + 1)));
        }
        return img;
    }
}
