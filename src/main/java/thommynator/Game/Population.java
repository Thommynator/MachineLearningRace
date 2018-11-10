package thommynator.Game;

import thommynator.NeuralNetwork.NeuralNet;

import java.util.ArrayList;
import java.util.Random;

class Population {
    private int amountOfCars;
    private ArrayList<Car> cars;

    public Population(int amountOfCars) {
        this.amountOfCars = amountOfCars;

        cars = new ArrayList<>();
        for (int i = 0; i < amountOfCars; i++) {
            cars.add(new Car(new PVector(10, 10)));
        }
    }

    // Resampling Wheel
    private void nextGeneration() {
        ArrayList<Car> children = new ArrayList<>();

        // make sure, that the best car is for sure in the new population
        Car bestCar = this.getBestCar();

        Car child = this.generateChild(bestCar);
        child.clr = color(0, 255, 0);
        children.add(child);

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
        return new Car(new PVector(10, 10), new NeuralNet(parent.getNeuralNet()));
    }

    private Car mutateChild(Car child) {
        child.getNeuralNet().mutate(new Random().nextDouble() * 0.2);
        return child;
    }

    private Car getBestCar() {
        double bestScore = 0;
        Car bestCar = null;
        for (Car car : cars) {
            double score = car.getFitness();
            if (score > bestScore) {
                bestScore = score;
                bestCar = car;
            }
        }
        return bestCar;
    }

    private boolean isAlive() {
        for (Car car : cars) {
            if (car.isAlive()) {
                return true;
            }
        }
        return false;
    }

    private void overrideAllWithBest() {
        for (Car car : cars) {
            car.setNeuralNet(new NeuralNet(this.getBestCar().getNeuralNet()));
        }
    }

    private void update() {
        for (Car car : cars) {
            car.updateState();
        }
    }

    private void show() {
        for (int c = cars.size() - 1; c >= 0; c--) {
            cars.get(c).show();
        }

        // highlight best car
        this.getBestCar().show(true);
    }
}
