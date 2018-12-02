package thommynator.Game;

import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RacetrackTest {

    @Test
    public void test() {

        Racetrack racetrack = new Racetrack();
        BufferedImage before = racetrack.image;

        racetrack.createTrack();
        BufferedImage after = racetrack.image;

        int blue = new Color(racetrack.image.getRGB(0, 0)).getBlue();
        int green = new Color(racetrack.image.getRGB(0, 0)).getGreen();
        int red = new Color(racetrack.image.getRGB(0, 0)).getRed();

        int b = new Color(racetrack.image.getRGB(400, 0)).getRed();

    }
}
