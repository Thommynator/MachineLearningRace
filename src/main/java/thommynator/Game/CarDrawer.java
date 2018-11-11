package thommynator.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class CarDrawer extends JPanel implements Runnable {

    private final int INITIAL_X = 50;
    private final int INITIAL_Y = 50;
    private final int DELAY = 5;
    private Thread animator;
    private Car car;

    public CarDrawer(Car car) {
        this.car = car;
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
        drawCar(graphics);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        animator = new Thread(this);
        animator.start();
    }

    private void cycle() {
        car.updatePosition();
        if (!car.isAlive()) {
            car = new Car(new Point2D.Double(INITIAL_X, INITIAL_Y));
        }
    }

    private void drawCar(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHints(rh);

        int carWidth = car.getWidth();
        int carLength = car.getLength();

        Rectangle rect = new Rectangle(0, 0, carLength, carWidth);
        graphics.setStroke(new BasicStroke(2));
        graphics.setColor(Color.gray);

        double x = car.getPosition().getX() - carWidth / 2.0;
        double y = car.getPosition().getY() - carLength / 2.0;
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        at.rotate(car.getHeading());
        graphics.draw(at.createTransformedShape(rect));
    }
}
