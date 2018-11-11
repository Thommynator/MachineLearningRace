package thommynator.Game;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import thommynator.App;
import thommynator.NeuralNetwork.NeuralNet;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

@Slf4j
public class Population {
    private int amountOfCars;

    @Getter
    private ArrayList<Car> cars;

    public Population(int amountOfCars) {
        log.debug("Create a new population with " + amountOfCars + " cars.");
        this.amountOfCars = amountOfCars;

        cars = new ArrayList<>();
        for (int i = 0; i < amountOfCars; i++) {
            cars.add(new Car(new Point2D.Double(App.INITIAL_X, App.INITIAL_Y)));
        }
    }

    /**
     * Generate a new population of cars from the current one.
     * Cars with a high {@link Car#fitness} have a better chance to survive.
     */
    public void nextGeneration() {
        ArrayList<Car> children = new ArrayList<>();

        // make sure, that the best car is for sure in the new population
        Car bestCar = this.getBestCar();

        Car child = this.generateChild(bestCar);
        child.setColor(new Color(0, 255, 0));
        children.add(child);

        // Resampling Wheel
        double maxScore = bestCar.getFitness();
        double beta = 0.0;
        int index = new Random().nextInt(amountOfCars);
        for (int i = 1; i < amountOfCars; i++) {
            beta += new Random().nextDouble() * 2 * maxScore;
            while (beta > cars.get(index).getFitness()) {
                beta -= cars.get(index).getFitness();
                index = (index + 1) % amountOfCars;
            }
            children.add(mutateChild(generateChild(cars.get(index))));
        }
        this.cars = children;
    }

    private Car generateChild(Car parent) {
        return new Car(new Point2D.Double(App.INITIAL_X, App.INITIAL_Y), new NeuralNet(parent.getNeuralNet()));
    }

    private Car mutateChild(Car child) {
        double mutationRate = 1E-2;
        child.getNeuralNet().mutate(new Random().nextDouble() * mutationRate);
        return child;
    }

    private Car getBestCar() throws NullPointerException {
        double bestScore = 0;
        Car bestCar = null;
        for (Car car : cars) {
            double score = car.getFitness();
            if (score > bestScore) {
                bestScore = score;
                bestCar = car;
            }
        }

        if (bestCar == null) {
            log.error("Not possible to find the best car. Best car is now null!");
            throw new NullPointerException("Not possible to find the best car.");
        }

        log.debug("The best car #" + bestCar.getId() + " has a fitness of " + bestCar.getFitness(), bestCar);
        return bestCar;
    }

    public boolean isAlive() {
        for (Car car : cars) {
            if (car.isAlive()) {
                return true;
            }
        }
        log.debug("All cars are dead.");
        return false;
    }

    private void overrideAllWithBest() {
        NeuralNet bestNeuralNet = this.getBestCar().getNeuralNet();
        cars.forEach(car -> car.setNeuralNet(bestNeuralNet));
    }

    public void update() {
        cars.forEach(Car::updateState);
    }
}
