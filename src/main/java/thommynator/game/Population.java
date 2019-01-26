package thommynator.game;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import thommynator.App;
import thommynator.neuralnetwork.NeuralNet;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

@Slf4j
@Getter
public class Population {
    private int amountOfCars;

    private ArrayList<Car> cars;

    public Population(int amountOfCars) {
        log.debug("Create a new population with {} cars.", amountOfCars);
        this.amountOfCars = amountOfCars;

        cars = new ArrayList<>();
        for (int i = 0; i < amountOfCars; i++) {
            cars.add(new Car(new Point2D.Double(App.INITIAL_X, App.INITIAL_Y)));
        }
    }

    /**
     * Generate a new population of cars from the current one.
     * Cars with a high fitness have a better chance to survive.
     */
    public void nextGeneration() {
        log.debug("Creating new generation...");
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

    /**
     * Finds the best car of this population and returns it.
     * The best car is the one with the highest fitness.
     *
     * @return the {@link Car} with the highest fitness.
     */
    public Car getBestCar() {
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

        log.debug("The best car #{} has a fitness of {}.", bestCar.getId(), bestCar.getFitness(), bestCar);
        return bestCar;
    }

    /**
     * Checks if the populations is alive or dead.
     * A population is dead, if all if its cars are dead. As long as one single car is alive, the whole populations
     * is alive.
     *
     * @return true if alive, otherwise false.
     */
    public boolean isAlive() {
        for (Car car : cars) {
            if (car.isAlive()) {
                return true;
            }
        }
        log.debug("All cars are dead.");
        return false;
    }

    private Car generateChild(Car parent) {
        return new Car(new Point2D.Double(App.INITIAL_X, App.INITIAL_Y), new NeuralNet(parent.getNeuralNet()));
    }

    private Car mutateChild(Car child) {
        double mutationRate = 0.05;
        child.getNeuralNet().mutate(new Random().nextDouble() * mutationRate);
        return child;
    }

    /**
     * Calls for all cars in the population the update method {@link Car#updateState()}.
     */
    public void update() {
        cars.parallelStream().forEach(Car::updateState);
    }

    /**
     * Loads the {@link NeuralNet} of the best car and uses it for the whole population.
     * Every {@link Car} will have the same {@link NeuralNet} after this.
     */
    public void overrideAllWithBest() {
        NeuralNet bestNeuralNet = this.getBestCar().getNeuralNet();
        this.overrideAllNeuralNets(bestNeuralNet);
    }

    /**
     * Loads a {@link NeuralNet} from a json file and uses it for the whole population.
     * Every {@link Car} will have the same {@link NeuralNet} after this.
     */
    public void overrideAllWithJson() {
        this.overrideAllWithJson("neural-net.json");
    }

    public void overrideAllWithJson(String fileName) {
        NeuralNet neuralNet = NeuralNet.load(fileName);
        this.overrideAllNeuralNets(neuralNet);
    }

    public void overrideAllNeuralNets(NeuralNet neuralNet) {
        cars.parallelStream().forEach(car -> car.setNeuralNet(new NeuralNet(neuralNet)));
    }

}
