package thommynator.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class PopulationDrawer extends JPanel implements Runnable {

    private final int DELAY = 10;
    private final long MAX_EPOCH_TIME = 15000; // hard reset after this milliseconds
    private Thread animator;
    private Population population;

    public PopulationDrawer(Population population) {
        this.population = population;
    }

    @Override
    public void run() {
        long epochStartTime = System.currentTimeMillis();
        long beforeTime = System.currentTimeMillis();
        long timeDiff;
        long sleep;

        while (true) {

            cycle();
            repaint();

            long now = System.currentTimeMillis();
            if (now - epochStartTime > MAX_EPOCH_TIME) {
                epochStartTime = now;
                population.nextGeneration();
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
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        population.getCars().forEach(car -> drawCar(graphics, car));
    }

    @Override
    public void addNotify() {
        super.addNotify();
        animator = new Thread(this);
        animator.start();
    }

    private void cycle() {
        population.update();
        if (!population.isAlive()) {
            population.nextGeneration();
        }
    }

    private void drawCar(Graphics g, Car car) {
        Graphics2D graphics = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHints(rh);

        int carWidth = car.getWidth();
        int carLength = car.getLength();

        // FIXME the visualization of the cars seems buggy. The center is not at the expected position (e.g. during collision)
        Rectangle rect = new Rectangle(0, 0, carLength, carWidth);
        Ellipse2D scanner = new Ellipse2D.Double((int) (0.75 * car.getLength()), 3, 4, 4);
        graphics.setStroke(new BasicStroke(2));
        graphics.setColor(car.getColor());

        AffineTransform translateToOrigin = AffineTransform.getTranslateInstance(-carLength / 2.0, -carWidth / 2.0);
        AffineTransform rotateHeading = AffineTransform.getRotateInstance(car.getHeading());
        AffineTransform transformToCar = AffineTransform.getTranslateInstance(car.getPosition().getX(), car.getPosition().getY());

        AffineTransform at = new AffineTransform();
//        at.concatenate(translateToOrigin);
//        at.concatenate(rotateHeading);
//        at.preConcatenate(transformToCar);
//        graphics.draw(at.createTransformedShape(rect));
//        graphics.draw(at.createTransformedShape(scanner));

        graphics.setColor(Color.BLUE);
        graphics.draw(new Rectangle2D.Double((int) car.getPosition().getX(), (int) car.getPosition().getY(), 1, 1));
        // id
//        graphics.setColor(Color.BLACK);
//        graphics.setFont(new Font("Arial", Font.PLAIN, 10));
//        graphics.drawString(String.valueOf(car.getId()), (int) car.getPosition().getX(), (int) car.getPosition().getY());
    }
}