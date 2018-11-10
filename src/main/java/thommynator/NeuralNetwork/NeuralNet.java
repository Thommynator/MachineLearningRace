package thommynator.NeuralNetwork;

import java.util.ArrayList;

public class NeuralNet {

    private int inputNodes;
    private int hiddenNodes;
    private int outputNodes;
    private ArrayList<Perceptron> hiddenPerceptrons;
    private ArrayList<Perceptron> outputPerceptrons;

    public NeuralNet(int inputNodes, int hiddenNodes, int outputNodes) {

        this.inputNodes = inputNodes;
        this.hiddenNodes = hiddenNodes;
        this.outputNodes = outputNodes;

        this.hiddenPerceptrons = new ArrayList<>(hiddenNodes);
        this.outputPerceptrons = new ArrayList<>(outputNodes);

        // initialize all hidden perceptrons
        for (int i = 0; i < hiddenNodes; i++) {
            hiddenPerceptrons.add(new Perceptron(inputNodes));
        }

        // initialize all output perceptrons
        for (int i = 0; i < outputNodes; i++) {
            outputPerceptrons.add(new Perceptron(hiddenNodes));
        }
    }

    /*
     * Creates a new neural net and copies all weights of other.
     */
    public NeuralNet(NeuralNet other) {
        this.inputNodes = other.inputNodes;
        this.hiddenNodes = other.hiddenNodes;
        this.outputNodes = other.outputNodes;

        this.hiddenPerceptrons = new ArrayList<>(hiddenNodes);
        this.outputPerceptrons = new ArrayList<>(outputNodes);

        for (Perceptron p : other.hiddenPerceptrons) {
            this.hiddenPerceptrons.add(new Perceptron(p.getWeights()));
        }

        for (Perceptron p : other.outputPerceptrons) {
            this.outputPerceptrons.add(new Perceptron(p.getWeights()));
        }
    }

    public ArrayList<Double> returnOutputs(ArrayList<Double> inputs) {
        ArrayList<Double> hiddenOutputs = new ArrayList<>(hiddenNodes);
        for (int i = 0; i < hiddenNodes; i++) {
            hiddenOutputs.add(hiddenPerceptrons.get(i).getOutput(inputs));
        }

        ArrayList<Double> outputs = new ArrayList<>(outputNodes);
        for (int i = 0; i < outputNodes; i++) {
            outputs.add(outputPerceptrons.get(i).getOutput(hiddenOutputs));
        }
        return outputs;
    }

    public void mutate(double mutationRate) {
        for (int i = 0; i < hiddenNodes; i++) {
            Perceptron hp = hiddenPerceptrons.get(i);
            hp.mutateWeights(mutationRate);
            hiddenPerceptrons.set(i, hp);
        }

        for (int i = 0; i < outputNodes; i++) {
            Perceptron op = outputPerceptrons.get(i);
            op.mutateWeights(mutationRate);
            outputPerceptrons.set(i, op);
        }
    }
}