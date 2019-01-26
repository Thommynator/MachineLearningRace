package thommynator;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import thommynator.game.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicInteger;

import static thommynator.game.MenuContent.*;

@Slf4j
public class App extends JFrame implements ActionListener {


    public static final int MAP_WIDTH = 800;
    public static final int MAP_HEIGHT = 600;
    public static final int INITIAL_X = 30;
    public static final int INITIAL_Y = 30;
    public static final AtomicInteger CAR_ID_FACTORY = new AtomicInteger(0);
    @Getter
    private static Racetrack racetrack = new SimpleRacetrack();
    @Getter
    private Population population;
    private int amountOfCars = 50;

    public App() {
        population = new Population(amountOfCars);
        initUI();
    }

    public static void main(String[] args) {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG");
        App app = new App();
    }


    private void initUI() {
        setTitle("ML-Race");

        // add a menu at the top
        setJMenuBar(createMenuBar());

        // add the content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.add(new GameCanvas(population));
        contentPanel.add(new RacetrackCanvas(racetrack));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(contentPanel);
        setPreferredSize(new Dimension(App.MAP_WIDTH, App.MAP_HEIGHT));
        setBounds(new Rectangle(App.MAP_WIDTH, App.MAP_HEIGHT));
        setVisible(true);

        // close window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenuItem menuItem;

        JMenu raceMenu = new JMenu("Race");
        menuItem = new JMenuItem(RACE_START.getMsg());
        menuItem.addActionListener(this);
//        raceMenu.add(menuItem);

        menuItem = new JMenuItem(RACE_STOP.getMsg());
        menuItem.addActionListener(this);
        raceMenu.add(menuItem);
//        menuBar.add(raceMenu);

        menuItem = new JMenuItem(RACE_RESTART.getMsg());
        menuItem.addActionListener(this);
        raceMenu.add(menuItem);
        menuBar.add(raceMenu);

        menuItem = new JMenuItem(RACE_NEWTRACK.getMsg());
        menuItem.addActionListener(this);
        raceMenu.add(menuItem);
        menuBar.add(raceMenu);

        JMenu neuralNetMenu = new JMenu("Neural Net");
        menuItem = new JMenuItem(NEURALNET_SAVE.getMsg());
        menuItem.addActionListener(this);
        neuralNetMenu.add(menuItem);

        menuItem = new JMenuItem(NEURALNET_LOAD.getMsg());
        menuItem.addActionListener(this);
        neuralNetMenu.add(menuItem);

        menuItem = new JMenuItem(NEURALNET_OVERRIDE_BEST.getMsg());
        menuItem.addActionListener(this);
        neuralNetMenu.add(menuItem);

        menuBar.add(neuralNetMenu);
        return menuBar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String actionCommand = e.getActionCommand();
        if (actionCommand.equals(RACE_START.getMsg())) {
            // TODO add functionality for continuing after stopping
            log.info("Started");
        }

        if (actionCommand.equals(RACE_STOP.getMsg())) {
            // TODO add functionality to stop/pause the simulation
            log.info("Stopped");
        }

        if (actionCommand.equals(RACE_RESTART.getMsg())) {
            population = new Population(amountOfCars);
            racetrack = new SimpleRacetrack();
            initUI();
            log.info("Restarted");
        }

        if (actionCommand.equals(RACE_NEWTRACK.getMsg())) {
            // TODO add functionality to load initial/default racetrack
            log.info("Restarted");
        }

        if (actionCommand.equals(NEURALNET_SAVE.getMsg())) {
            population.getBestCar().getNeuralNet().save();
            log.info("Saved best Neural Net.");
        }

        if (actionCommand.equals(NEURALNET_LOAD.getMsg())) {
            population.overrideAllWithJson();
            log.info("Loaded best Neural Net.");
        }

        if (actionCommand.equals(NEURALNET_OVERRIDE_BEST.getMsg())) {
            population.overrideAllWithBest();
            log.info("Use the best neural net for all.");
        }
    }
}
