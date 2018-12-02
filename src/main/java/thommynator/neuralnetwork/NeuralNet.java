package thommynator.neuralnetwork;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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

    public List<Double> returnOutputs(List<Double> inputs) {
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

    /**
     * Converts the {@link NeuralNet} into a {@link JSONObject}.
     *
     * @return a {@link JSONObject}.
     */
    private JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("amountOfInputPerceptrons", amountOfInputPerceptrons);
        obj.put("amountOfHiddenPerceptrons", amountOfHiddenPerceptrons);
        obj.put("amountOfOutputPerceptrons", amountOfOutputPerceptrons);

        JSONArray hidden = new JSONArray();
        hiddenPerceptrons.forEach(p -> hidden.add(p.toJSON()));
        obj.put("hiddenPerceptrons", hidden);

        JSONArray output = new JSONArray();
        outputPerceptrons.forEach(p -> output.add(p.toJSON()));
        obj.put("outputPerceptrons", output);
        return obj;
    }

    /**
     * Saves this {@link NeuralNet} as JSON string into file.
     */
    public void save() {
        URL url = this.getClass().getClassLoader().getResource("neural-net.json");
        if (url != null) {
            try (FileWriter file = new FileWriter(url.getPath())) {
                JSONObject jsonObject = this.toJSON();
                file.write(jsonObject.toJSONString());
                log.info("Successfully saved neural net JSON object to file {}.", url.getPath());
                log.debug("object: \n {}", jsonObject);
            } catch (IOException e) {
                log.error("Failed to save neural net JSON object.", e);
            }
        } else {
            log.error("Failed to save neural net JSON object. URL is null.");
        }
    }
}