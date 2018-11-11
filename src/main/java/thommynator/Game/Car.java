package thommynator.Game;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import thommynator.App;
import thommynator.NeuralNetwork.NeuralNet;
import thommynator.utils.Utils;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.floor;
import static java.lang.Math.sin;

@Slf4j
@Data
public class Car {

    // state
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
        this.position = position;
        this.heading = Math.toRadians(Utils.random(0, 90)); // in radian
        this.speed = Utils.random(0.5, 3.0);
        this.drivenDistance = 0.0;
        this.isAlive = true;
        this.fitness = 0.0;
        int hiddenNodes = 6;
        this.nSensors = 9;
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
        if (isAlive()) {
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
        while (isInsideCanvas(x, y)) {
            if (!isOnTrack(x, y)) {
                break;
            }
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

    private void adaptControls() {
        ArrayList<Double> control = neuralNet.returnOutputs(distances);
        speed += control.get(0);
        heading += control.get(1);
    }

    private void updatePosition() {
        if (!isAlive()) {
            color = new Color(255, 0, 0);
            speed = 0.0;
            return;
        }
        double deltaX = speed * cos(heading);
        double deltaY = speed * sin(heading);

        position = new Point2D.Double(position.getX() + deltaX, position.getY() + deltaY);
        drivenDistance += abs(deltaX) + abs(deltaY);
        log.debug("Updated position. x: " + position.getX() + " y: " + position.getY());
    }

    protected double getFitness() {
        // distance to top-left corner
        this.fitness = position.distance(0, 0);
        return fitness;
    }

    private int convertCoordinateToIndex(double x, double y) {
        return (int) (floor(x) + floor(y) * App.MAP_WIDTH);
    }

    public boolean isAlive() {
        // skip computation, if we already know from prev loop, that car is not alive
        if (!isAlive) {
            return false;
        }

        double x = position.getX();
        double y = position.getY();

        if (this.isInsideCanvas(x, y) && this.isOnTrack(x, y)) {
            return true;
        } else {
            isAlive = false;
            return false;
        }
    }

    private boolean isOnTrack(double x, double y) {
        // TODO implementation needed
        return true;
//        return red(backgroundPixels[convertCoordinateToIndex(x, y)]) == red(streetColor)
//                && green(backgroundPixels[convertCoordinateToIndex(x, y)]) == green(streetColor)
//                && blue(backgroundPixels[convertCoordinateToIndex(x, y)]) == blue(streetColor);
    }

    private boolean isInsideCanvas(double x, double y) {
        return x > 0 && x < App.MAP_WIDTH - 50 && y > 0 && y < App.MAP_HEIGHT - 50;
    }
}
