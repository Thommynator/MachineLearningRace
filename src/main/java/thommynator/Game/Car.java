package thommynator.Game;

import lombok.Getter;
import lombok.Setter;
import thommynator.NeuralNetwork.NeuralNet;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.*;

public class Car {
    private color clr;
    private Pos2D pos;
    private double heading;
    private double vel;

    private int nSensors;
    private ArrayList<Double> distances;

    private double drivenDistance;
    private boolean isAlive;

    private double fitness;

    @Getter
    @Setter
    private NeuralNet neuralNet;

    public Car(PVector pos) {
        this.clr = color(240, 240, 255);
        this.pos = pos;
        this.heading = PI / 2;
        this.vel = max(1, new Random().nextDouble() * 5);
        this.nSensors = 9;
        this.distances = new ArrayList<>(nSensors);
        this.drivenDistance = 0.0;
        this.isAlive = true;

        int hiddenNodes = 6;
        this.neuralNet = new NeuralNet(nSensors, hiddenNodes, 2);
    }

    public Car(PVector pos, NeuralNet neuralNet) {
        this(pos);
        this.neuralNet = neuralNet;
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
        this.distances = new ArrayList<>(this.nSensors);

        if (this.nSensors % 2 != 0) {
            this.distances.add(normalizeDistance(rayDistance(this.heading)));
        }

        for (int i = 1; i <= (nSensors / 2); i++) {
            double angle = PI / (floor(nSensors / 2) * 2);
            this.distances.add(normalizeDistance(rayDistance(this.heading + i * angle)));
            this.distances.add(normalizeDistance(rayDistance(this.heading - i * angle)));
        }
    }

    private double rayDistance(double angle) {
        double x = this.pos.x;
        double y = this.pos.y;
        while (isInsideCanvas(x, y)) {
            if (!isOnTrack(x, y)) {
                break;
            }
            x += cos(angle) * 2;
            y += sin(angle) * 2;
        }

        return dist(this.pos.x, this.pos.y, x, y);
    }

    /**
     * Normalizes the distances to range 0 to 1.
     */
    private double normalizeDistance(double dist) {
        double maxDist = dist(0, 0, width, height);
        return map(dist, 0, maxDist, 0, 1);
    }

    // adapt the amount of input nodes of the NeuralNet accordingly: distances.size() + 1 (vel)
    private void adaptControls() {
        ArrayList<Double> inputs = distances;
        ArrayList<Double> control = this.neuralNet.returnOutputs(inputs);
        this.vel += control.get(0);
        this.heading += control.get(1);
    }

    private void updatePosition() {
        if (!isAlive()) {
            this.clr = color(255, 0, 0);
            this.vel = 0.0;
            return;
        }
        double deltaX = this.vel * cos(this.heading);
        double deltaY = this.vel * sin(this.heading);

        this.pos.x += deltaX;
        this.pos.y += deltaY;

        drivenDistance += abs(deltaX) + abs(deltaY);
    }

    protected double getFitness() {
        this.fitness = dist(this.pos.x, this.pos.y, 0, 0);
        return this.fitness;
    }

    public void show() {
        this.show(false);
    }

    public void show(boolean highlight) {
        rectMode(CENTER);
        if (highlight) {
            fill(color(255, 255, 0));
        } else if (!this.isAlive) {
            fill(this.clr, 50);
        } else {
            fill(this.clr);
        }
        stroke(0);
        pushMatrix();
        translate(this.pos.x, this.pos.y);
        rotate(this.heading);
        rect(0, 0, 20, 10);
        ellipse(5, 0, 3, 3);
        popMatrix();
    }

    private int convertCoordinateToIndex(double x, double y) {
        return floor(x) + floor(y) * width;
    }

    public boolean isAlive() {
        // skip computation, if we already know from prev loop, that car is not alive
        if (!this.isAlive) {
            return false;
        }

        double x = pos.x;
        double y = pos.y;

        if (this.isInsideCanvas(x, y) && this.isOnTrack(x, y)) {
            return true;
        } else {
            this.isAlive = false;
            return false;
        }
    }

    private boolean isOnTrack(double x, double y) {
        return red(backgroundPixels[convertCoordinateToIndex(x, y)]) == red(streetColor)
                && green(backgroundPixels[convertCoordinateToIndex(x, y)]) == green(streetColor)
                && blue(backgroundPixels[convertCoordinateToIndex(x, y)]) == blue(streetColor);
    }

    private boolean isInsideCanvas(double x, double y) {
        return x > 0 && x < width && y > 0 && y < height;
    }
}
