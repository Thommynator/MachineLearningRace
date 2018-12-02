package thommynator.game;

import lombok.Data;
import thommynator.App;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

@Data
public class Racetrack {

    public static final Color FOREGROUND_COLOR = new Color(100, 100, 100);
    public static final Color BACKGROUND_COLOR = new Color(200, 200, 200);

    int roadWidth;
    List<Point2D> anchorPoints;
    BufferedImage image;

    public Racetrack() {
        roadWidth = 150;
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
        graphics.setStroke(new BasicStroke(roadWidth));
        for (int i = 0; i < anchorPoints.size() - 1; i++) {
            graphics.draw(new Line2D.Double(anchorPoints.get(i), anchorPoints.get(i + 1)));
        }
        return img;
    }

}
