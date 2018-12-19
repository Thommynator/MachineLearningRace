package thommynator.neuralnetwork;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NeuralNetTest {

    @Test
    public void createNeuralNetFromConstructor() {
        NeuralNet net = new NeuralNet(3, 2, 1);
        assertThat(net).isNotNull();
        assertThat(net.getAmountOfInputPerceptrons()).isEqualTo(3);
        assertThat(net.getAmountOfHiddenPerceptrons()).isEqualTo(2);
        assertThat(net.getHiddenPerceptrons().size()).isEqualTo(2);
        assertThat(net.getAmountOfOutputPerceptrons()).isEqualTo(1);
        assertThat(net.getOutputPerceptrons().size()).isEqualTo(1);
    }

    @Test
    public void createNeuralNetFromNeuralNet() {
        NeuralNet other = new NeuralNet(3, 2, 1);
        NeuralNet net = new NeuralNet(other);
        assertThat(net).isNotNull();
        assertThat(net.getAmountOfInputPerceptrons()).isEqualTo(3);
        assertThat(net.getAmountOfHiddenPerceptrons()).isEqualTo(2);
        assertThat(net.getHiddenPerceptrons().size()).isEqualTo(2);
        assertThat(net.getAmountOfOutputPerceptrons()).isEqualTo(1);
        assertThat(net.getOutputPerceptrons().size()).isEqualTo(1);
    }

    @Test
    public void createNeuralNetFromJsonFileTest() {
        NeuralNet net = NeuralNet.load("neural-net-example.json");
        assertThat(net).isNotNull();
        assertThat(net.getAmountOfInputPerceptrons()).isEqualTo(5);
        assertThat(net.getAmountOfHiddenPerceptrons()).isEqualTo(6);
        assertThat(net.getHiddenPerceptrons().size()).isEqualTo(6);
        assertThat(net.getAmountOfOutputPerceptrons()).isEqualTo(2);
        assertThat(net.getOutputPerceptrons().size()).isEqualTo(2);
    }
}
