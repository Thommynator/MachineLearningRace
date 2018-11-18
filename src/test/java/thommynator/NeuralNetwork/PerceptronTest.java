package thommynator.NeuralNetwork;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PerceptronTest {

    @Test
    public void initializingNewPerceptronTest() {
        int amountOfWeights = 5;
        Perceptron perceptron = new Perceptron(amountOfWeights);
        assertThat(perceptron.getWeights().size()).isEqualTo(amountOfWeights);
        perceptron.getWeights().forEach(weight -> assertThat(weight).isBetween(-1.0, 1.0));
    }

    @Test
    public void initializingPerceptronWithExistingWeightsTest() {
        final List<Double> existingWeights = Arrays.asList(0.9, -0.5, 0.0, 0.5, 0.9);
        Perceptron perceptron = new Perceptron(existingWeights);
        final ArrayList<Double> weights = perceptron.getWeights();
        for (int i = 0; i < existingWeights.size(); i++) {
            assertThat(weights.get(i)).isEqualTo(existingWeights.get(i));
            assertThat(weights.get(i)).isBetween(-1.0, 1.0);
        }
    }

    @Test
    public void mutateWeightsWithZeroMutationRateTest() {
        // weights shouldn't change if mutation rate is zero
        final List<Double> existingWeights = Arrays.asList(0.9, -0.5, 0.0, 0.5, 0.9);
        Perceptron perceptron = new Perceptron(existingWeights);
        perceptron.mutateWeights(0.0);
        final ArrayList<Double> weights = perceptron.getWeights();
        for (int i = 0; i < existingWeights.size(); i++) {
            assertThat(weights.get(i)).isEqualTo(existingWeights.get(i));
            assertThat(weights.get(i)).isBetween(-1.0, 1.0);
        }
    }

    @Test
    public void mutateWeightsTest() {

    }
}
