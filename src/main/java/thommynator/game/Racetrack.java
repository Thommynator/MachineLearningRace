package thommynator.game;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents the racetrack, where the {@link Car}s can drive on.
 * The {@link Racetrack#ROAD_COLOR} represents the road.
 * The {@link Racetrack#OFFROAD_COLOR} represents the off-road area.
 */
public interface Racetrack {

    Color ROAD_COLOR = new Color(100, 100, 100);
    Color OFFROAD_COLOR = new Color(200, 200, 200);
    int ROAD_WIDTH = 100;

    /**
     * Returns the racetrack as a {@link BufferedImage}.
     *
     * @return the racetrack.
     */
    BufferedImage getRacetrack();
}
