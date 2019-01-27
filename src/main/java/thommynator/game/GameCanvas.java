package thommynator.game;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import thommynator.App;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

@Slf4j
public class GameCanvas extends JPanel implements Runnable {

    private static final int DELAY = 20;               // time in ms between each frame
    private static final long MAX_EPOCH_TIME = 15000;  // hard reset after this amount of milliseconds
    @Setter
    @Getter
    private boolean showBestCarOnly;
    private transient Population population;
    private boolean showCarIds;

    public GameCanvas(Population population) {
        setSize(App.MAP_WIDTH, App.MAP_HEIGHT);
        setOpaque(false);
        setVisible(true);
        this.population = population;
        this.showCarIds = false;
        this.showBestCarOnly = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!showBestCarOnly) {
            population.getCars().forEach(car -> drawCar(g, car, showCarIds));
        } else {
            try {
                Car bestCar = population.getBestCar();
                drawCar(g, bestCar, showCarIds);
            } catch (IllegalAccessException e) {
                log.info("Can't draw the best car on canvas. {}", e.getMessage(), e);
            }
        }
    }

    private void drawCar(Graphics g, Car car, boolean showId) {
        Graphics2D graphics = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHints(rh);

        int carWidth = car.getWidth();
        int carLength = car.getLength();

        Rectangle rect = new Rectangle(0, 0, carLength, carWidth);
        Ellipse2D scanner = new Ellipse2D.Double((int) (0.75 * car.getLength()), 3, 4, 4);
        graphics.setStroke(new BasicStroke(2));
        graphics.setColor(car.getColor());

        AffineTransform transformToOrigin = AffineTransform.getTranslateInstance(-carLength / 2.0, -carWidth / 2.0);
        AffineTransform transformToHeading = AffineTransform.getRotateInstance(car.getHeading());
        AffineTransform transformToDestination = AffineTransform.getTranslateInstance(
                car.getPosition().getX(), car.getPosition().getY());

        // build final transform matrix, order matters
        AffineTransform at = new AffineTransform();
        at.concatenate(transformToDestination);
        at.concatenate(transformToHeading);
        at.concatenate(transformToOrigin);

        graphics.draw(at.createTransformedShape(rect));
        graphics.draw(at.createTransformedShape(scanner));

        graphics.setColor(Color.BLUE);
        graphics.draw(new Rectangle2D.Double((int) car.getPosition().getX(), (int) car.getPosition().getY(), 1, 1));

        if (showId) {
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Arial", Font.PLAIN, 12));
            graphics.drawString(String.valueOf(car.getId()), (int) car.getPosition().getX(), (int) car.getPosition().getY());
        }
    }

    @Override
    public void run() {
        long epochStartTime = System.currentTimeMillis();
        long beforeTime = System.currentTimeMillis();
        long timeDiff;
        long sleep;

        while (true) {

            population.update();
            repaint();

            long now = System.currentTimeMillis();
            if (now - epochStartTime > MAX_EPOCH_TIME) {
                population.nextGeneration();
                epochStartTime = now;
            }

            if (!population.isAlive()) {
                population.nextGeneration();
                epochStartTime = now;
            }

            timeDiff = now - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                String msg = String.format("Thread interrupted: %s", e.getMessage());
                JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
            }
            beforeTime = System.currentTimeMillis();
        }
    }

    @Override
    public void addNotify() {
        super.addNotify();
        Thread animator = new Thread(this);
        animator.start();
    }
}