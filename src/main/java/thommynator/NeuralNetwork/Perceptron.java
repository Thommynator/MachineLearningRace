package thommynator.NeuralNetwork;

import lombok.Getter;
import thommynator.utils.Utils;

import java.util.ArrayList;

public class Perceptron {

    @Getter
    private ArrayList<Double> weights;

    public Perceptron(int amountOfWeights) {
        this.weights = new ArrayList<>(amountOfWeights);
        for (int i = 0; i < amountOfWeights; i++) {
            weights.add(Utils.random(-1, 1));
        }
    }

    public Perceptron(ArrayList<Double> inputWeights) {
        weights = new ArrayList<>(inputWeights);
    }

    public double getOutput(ArrayList<Double> inputs) {
        double summedWeightedInputs = 0.0;
        for (int i = 0; i < inputs.size(); i++) {
            summedWeightedInputs += weights.get(i) * inputs.get(i);
        }
        return this.activationFunction(summedWeightedInputs);
    }

    public void mutateWeights(double mutationRate) {
        for (int i = 0; i < weights.size(); i++) {
            double w = weights.get(i);
            double noise = Utils.random(-mutationRate, mutationRate);
            weights.set(i, Utils.constrain(w + noise, -1, 1));
        }
    }

    // Sigmoid Function from -1 to +1
    private double activationFunction(double x) {
        return 2.0 / (1 + Math.exp(-x)) - 1;
    }
}
