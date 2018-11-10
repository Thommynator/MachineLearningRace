package thommynator.NeuralNetwork;

import java.util.ArrayList;

public class NeuralNet {

    private int amountOfInputPerceptrons;
    private int amountOfHiddenPerceptrons;
    private int amountOfOutputPerceptrons;
    private ArrayList<Perceptron> hiddenPerceptrons;
    private ArrayList<Perceptron> outputPerceptrons;

    public NeuralNet(int amountOfInputPerceptrons, int amountOfHiddenPerceptrons, int amountOfOutputPerceptrons) {
        this.amountOfInputPerceptrons = amountOfInputPerceptrons;
        this.amountOfHiddenPerceptrons = amountOfHiddenPerceptrons;
        this.amountOfOutputPerceptrons = amountOfOutputPerceptrons;
        this.hiddenPerceptrons = new ArrayList<>(amountOfHiddenPerceptrons);
        this.outputPerceptrons = new ArrayList<>(amountOfOutputPerceptrons);

        // initialize all hidden perceptrons
        for (int i = 0; i < amountOfHiddenPerceptrons; i++) {
            hiddenPerceptrons.add(new Perceptron(amountOfInputPerceptrons));
        }

        // initialize all output perceptrons
        for (int i = 0; i < amountOfOutputPerceptrons; i++) {
            outputPerceptrons.add(new Perceptron(amountOfHiddenPerceptrons));
        }
    }

    /*
     * Creates a new neural net and copies all weights of other.
     */
    public NeuralNet(NeuralNet other) {
        this.amountOfInputPerceptrons = other.amountOfInputPerceptrons;
        this.amountOfHiddenPerceptrons = other.amountOfHiddenPerceptrons;
        this.amountOfOutputPerceptrons = other.amountOfOutputPerceptrons;
        this.hiddenPerceptrons = new ArrayList<>(amountOfHiddenPerceptrons);
        this.outputPerceptrons = new ArrayList<>(amountOfOutputPerceptrons);

        for (Perceptron p : other.hiddenPerceptrons) {
            this.hiddenPerceptrons.add(new Perceptron(p.getWeights()));
        }

        for (Perceptron p : other.outputPerceptrons) {
            this.outputPerceptrons.add(new Perceptron(p.getWeights()));
        }
    }

    public ArrayList<Double> returnOutputs(ArrayList<Double> inputs) {
        ArrayList<Double> hiddenOutputs = new ArrayList<>(amountOfHiddenPerceptrons);
        for (int i = 0; i < amountOfHiddenPerceptrons; i++) {
            hiddenOutputs.add(hiddenPerceptrons.get(i).getOutput(inputs));
        }

        ArrayList<Double> outputs = new ArrayList<>(amountOfOutputPerceptrons);
        for (int i = 0; i < amountOfOutputPerceptrons; i++) {
            outputs.add(outputPerceptrons.get(i).getOutput(hiddenOutputs));
        }
        return outputs;
    }

    public void mutate(double mutationRate) {
        for (int i = 0; i < amountOfHiddenPerceptrons; i++) {
            Perceptron hp = hiddenPerceptrons.get(i);
            hp.mutateWeights(mutationRate);
            hiddenPerceptrons.set(i, hp);
        }

        for (int i = 0; i < amountOfOutputPerceptrons; i++) {
            Perceptron op = outputPerceptrons.get(i);
            op.mutateWeights(mutationRate);
            outputPerceptrons.set(i, op);
        }
    }
}