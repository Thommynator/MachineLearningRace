package thommynator.Game;

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

    public final static Color FOREGROUND_COLOR = new Color(100, 100, 100);
    public final static Color BACKGROUND_COLOR = new Color(200, 200, 200);

    int roadWidth;
    List<Point2D> anchorPoints;
    BufferedImage image;

    public Racetrack() {
        image = new BufferedImage(App.MAP_WIDTH, App.MAP_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        roadWidth = 150;
        anchorPoints = Arrays.asList(
                new Point2D.Double(0.0, 0.0),
                new Point2D.Double(App.MAP_WIDTH, App.MAP_HEIGHT));
    }

    public void createTrack() {
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(BACKGROUND_COLOR);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        graphics.setColor(FOREGROUND_COLOR);
        graphics.setStroke(new BasicStroke(roadWidth));
        graphics.draw(new Line2D.Double(anchorPoints.get(0), anchorPoints.get(1)));
    }
}
