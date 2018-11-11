package thommynator.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class PopulationDrawer extends JPanel implements Runnable {

    private final int DELAY = 10;
    private Thread animator;
    private Population population;

    public PopulationDrawer(Population population) {
        this.population = population;
    }

    @Override
    public void run() {
        long beforeTime = System.currentTimeMillis();
        long timeDiff;
        long sleep;

        while (true) {
            cycle();
            repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
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
        population.getCars().forEach(Car::updateState);
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

        Rectangle rect = new Rectangle(0, 0, carLength, carWidth);
        Ellipse2D scanner = new Ellipse2D.Double(15, 3, 4, 4);
        graphics.setStroke(new BasicStroke(2));
        graphics.setColor(car.getColor());

        double x = car.getPosition().getX() - carWidth / 2.0;
        double y = car.getPosition().getY() - carLength / 2.0;
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        at.rotate(car.getHeading());
        graphics.draw(at.createTransformedShape(rect));
        graphics.draw(at.createTransformedShape(scanner));
    }
}
