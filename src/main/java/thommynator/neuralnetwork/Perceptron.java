package thommynator.neuralnetwork;

import lombok.Getter;
import org.json.simple.JSONObject;
import thommynator.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents one single node in the {@link NeuralNet}.
 */
public class Perceptron {

    @Getter
    private ArrayList<Double> weights;

    public Perceptron(int amountOfWeights) {
        this.weights = new ArrayList<>(amountOfWeights);
        for (int i = 0; i < amountOfWeights; i++) {
            weights.add(Utils.random(-1, 1));
        }
    }

    public Perceptron(List<Double> inputWeights) {
        weights = new ArrayList<>(inputWeights);
    }

    public double getOutput(List<Double> inputs) {
        double summedWeightedInputs = 0.0;
        for (int i = 0; i < inputs.size(); i++) {
            summedWeightedInputs += weights.get(i) * inputs.get(i);
        }
        return this.activationFunction(summedWeightedInputs);
    }

    /**
     * Changes the weights of this perceptron. A random noise will be added to each weight.
     * The weights are constrained to [-1, +1].
     *
     * @param mutationRate defines the bounds of the maximum +/- noise. The noise itself is random in these bounds.
     */
    public void mutateWeights(double mutationRate) {
        if (Utils.areEqual(mutationRate, 0.0)) {
            return;
        }

        for (int i = 0; i < weights.size(); i++) {
            double w = weights.get(i);
            double noise = Utils.random(-mutationRate, mutationRate);
            weights.set(i, Utils.constrain(w + noise, -1, 1));
        }
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        Map<Integer, Double> ws = new HashMap<>(weights.size());
        for (int i = 0; i < weights.size(); i++) {
            ws.put(i, weights.get(i));
        }
        obj.put("weights", new JSONObject(ws));
        return obj;
    }

    // Sigmoid Function from -1 to +1
    private double activationFunction(double x) {
        return 2.0 / (1 + Math.exp(-x)) - 1;
    }
}