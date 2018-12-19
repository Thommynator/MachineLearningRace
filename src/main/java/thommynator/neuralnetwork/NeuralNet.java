package thommynator.neuralnetwork;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Getter
@EqualsAndHashCode
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

    /**
     * Load a {@link NeuralNet} from a JSON file and return the object as Java object.
     *
     * @param fileName name of the json file.
     * @return a new instance of {@link NeuralNet}.
     */
    public static NeuralNet load(String fileName) {
        String path = Objects.requireNonNull(NeuralNet.class.getClassLoader().getResource(fileName)).getPath();
        try (JsonReader jsonReader = new JsonReader(new FileReader(path))) {
            Gson gson = new GsonBuilder().create();
            return gson.fromJson(jsonReader, NeuralNet.class);
        } catch (FileNotFoundException e) {
            log.error("File not found. Can't load neural net from: {}", path);
        } catch (IOException e) {
            log.error("Error occurred when loading a neural network from a file.");
        }
        return null;
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
     * Saves this {@link NeuralNet} as JSON string into file.
     */
    public void save() {
        URL url = this.getClass().getClassLoader().getResource("neural-net.json");
        if (url != null) {
            try (FileWriter file = new FileWriter(url.getPath())) {
                Gson gson = new GsonBuilder().create();
                String json = gson.toJson(this);
                file.write(json);
                log.info("Successfully saved neural net JSON object to file {}.", url.getPath());
                log.debug("object: \n {}", json);
            } catch (IOException e) {
                log.error("Failed to save neural net JSON object.", e);
            }
        } else {
            log.error("Failed to save neural net JSON object. URL is null.");
        }
    }
}