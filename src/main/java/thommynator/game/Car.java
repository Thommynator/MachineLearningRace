package thommynator.game;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import thommynator.App;
import thommynator.neuralnetwork.NeuralNet;
import thommynator.utils.Utils;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.floor;
import static java.lang.Math.sin;

@Slf4j
@Data
public class Car {

    // state
    private int id;
    private Point2D position;
    private double heading;
    private double speed;
    private double drivenDistance;
    private boolean isAlive;
    private double fitness;
    private NeuralNet neuralNet;

    // sensor inputs
    private int nSensors;
    private ArrayList<Double> distances;

    // appearance
    private int width;
    private int length;
    private boolean highlight;
    private Color color;

    public Car(Point2D position) {
        this.id = App.CAR_ID_FACTORY.incrementAndGet();
        this.position = position;
        this.heading = Math.toRadians(Utils.random(35, 55)); // in radian
        this.speed = Utils.random(0.5, 3.0);
        this.drivenDistance = 0.0;
        this.isAlive = true;
        this.fitness = 0.0;
        int hiddenNodes = 6;
        this.nSensors = 5;
        this.neuralNet = new NeuralNet(nSensors, hiddenNodes, 2);
        this.distances = new ArrayList<>(nSensors);

        this.width = 10;
        this.length = 20;
        this.color = new Color(55, 55, 55);
        this.highlight = false;
    }

    public Car(Point2D position, NeuralNet neuralNet) {
        this(position);
        this.neuralNet = new NeuralNet(neuralNet);
    }

    public void updateState() {
        if (this.isAlive()) {
            this.measureDistances();
            this.adaptControls();
            this.updatePosition();
        }
    }

    /**
     * Measures the distance of all sensors to a wall.
     * The sensors are more or less equally distributed over the front 180° cone of the car.
     * If the amount of sensors is odd, there will be no front sensor (0°). If the amount is
     * even, there will be a front sensor.
     */
    private void measureDistances() {
        distances = new ArrayList<>(nSensors);

        if (nSensors % 2 != 0) {
            distances.add(normalizeDistance(rayDistance(heading)));
        }

        for (int i = 1; i <= (nSensors / 2); i++) {
            double angle = PI / (floor(nSensors / 2.0) * 2);
            distances.add(normalizeDistance(rayDistance(heading + i * angle)));
            distances.add(normalizeDistance(rayDistance(heading - i * angle)));
        }
    }

    private double rayDistance(double angle) {
        double x = position.getX();
        double y = position.getY();
        while (isAlive((int) x, (int) y)) {
            x += cos(angle) * 2;
            y += sin(angle) * 2;
        }
        return position.distance(x, y);
    }

    /**
     * Normalizes the distances to range 0 to 1.
     */
    private double normalizeDistance(double dist) {
        double maxDist = Point2D.distance(0, 0, App.MAP_WIDTH, App.MAP_HEIGHT);
        return Utils.map(dist, 0, maxDist, 0, 1);
    }

    /**
     * Uses the {@link Car#distances} as an input for the {@link Car#neuralNet} and generates to control values.
     * 1. speed change, 2. heading change.
     * These control values will be constrained to some limits.
     */
    private void adaptControls() {
        List<Double> control = neuralNet.returnOutputs(distances);
        double speedLimit = 5.0;
        double headingChangeLimit = 2.0;
        // backwards driving cars are slower
        speed = Utils.constrain(speed + control.get(0), -speedLimit * 0.3, speedLimit);
        heading += Utils.constrain(control.get(1), -headingChangeLimit, headingChangeLimit);
    }

    private void updatePosition() {
        if (!isAlive()) {
            speed = 0.0;
            return;
        }
        double deltaX = speed * cos(heading);
        double deltaY = speed * sin(heading);

        if (!isAlive((int) (position.getX() + deltaX), (int) (position.getY() + deltaY))) {
            this.isAlive = false;
            return;
        }
        position = new Point2D.Double(position.getX() + deltaX, position.getY() + deltaY);
        drivenDistance += abs(deltaX) + abs(deltaY);
    }

    protected double getFitness() {
        // distance to top-left corner
        this.fitness = Math.pow(position.distance(0, 0), 2);
        return fitness;
    }

    /**
     * Checks if a car at its current position is dead or alive.
     * Alive means it is inside of the canvas and on the racetrack.
     * The car status {@link Car#isAlive} and color {@link Car#color} will be adapted accordingly.
     *
     * @return true if alive, otherwise false
     */
    public boolean isAlive() {
        // skip computation, if we already know from the previous loop, that the car is not alive
        if (!isAlive) {
            color = new Color(255, 0, 0);
            return false;
        }

        if (this.isAlive((int) position.getX(), (int) position.getY())) {
            isAlive = true;
            return true;
        } else {
            isAlive = false;
            return false;
        }
    }

    /**
     * Checks if a car would be alive, if it would be at a specific coordinate.
     * The status of the car {@link Car#isAlive} stays unmodified.
     *
     * @param x coordinate
     * @param y coordinate
     * @return true if it would be alive, otherwise false.
     */
    private boolean isAlive(int x, int y) {
        return this.isInsideCanvas(x, y) && this.isOnTrack(x, y);
    }

    private boolean isOnTrack(double x, double y) {
        int positionColorInt = App.RACETRACK.getImage().getRGB((int) x, (int) y);
        int trackColorInt = Racetrack.FOREGROUND_COLOR.getRGB();
        return positionColorInt == trackColorInt;
    }

    private boolean isInsideCanvas(double x, double y) {
        int buffer = 0;
        return x > buffer
                && x < (App.MAP_WIDTH - buffer)
                && y > buffer
                && y < (App.MAP_HEIGHT - buffer);
    }
}
