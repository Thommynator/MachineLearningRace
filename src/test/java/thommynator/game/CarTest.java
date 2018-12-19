package thommynator.game;

import org.junit.Test;
import thommynator.App;
import thommynator.neuralnetwork.NeuralNet;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class CarTest {

    @Test
    public void constructorWithPositionTest() {
        Point2D position = new Point2D.Double(10.0, 20.0);
        Car car = new Car(position);
        assertThat(car).isNotNull();
        assertThat(car.getPosition()).isEqualTo(position);
        assertThat(car.getNeuralNet()).isNotNull();
    }

    @Test
    public void constructorWithPositionAndNeuralNetTest() {
        Point2D position = new Point2D.Double(10.0, 20.0);
        NeuralNet net = new NeuralNet(3, 2, 1);

        List<Double> testInput = Arrays.asList(0.2, 0.4, 0.6);
        double expectedOutput = net.returnOutputs(testInput).get(0);

        Car car = new Car(position, net);
        assertThat(car).isNotNull();
        assertThat(car.getPosition()).isEqualTo(position);
        assertThat(car.getNeuralNet()).isNotNull();
        assertThat(car.getNeuralNet().returnOutputs(testInput).get(0)).isEqualTo(expectedOutput);
    }

    @Test
    public void whenCarAlreadyNotAliveThenReturnFalse() {
        Point2D position = new Point2D.Double(10.0, 20.0);
        Car car = new Car(position);
        car.setAlive(false);
        assertThat(car.isAlive()).isFalse();
    }

    @Test
    public void whenCarOutOfCanvasThenReturnFalse() {
        Point2D position;
        Car car;

        // left of canvas
        position = new Point2D.Double(-10.0, 20.0);
        car = spy(new Car(position));
        doReturn(true).when(car).isOnTrack(position.getX(), position.getY());
        assertThat(car.isAlive()).isFalse();

        // above canvas
        position = new Point2D.Double(10.0, -20.0);
        car = spy(new Car(position));
        doReturn(true).when(car).isOnTrack(position.getX(), position.getY());
        assertThat(car.isAlive()).isFalse();

        // right of canvas
        position = new Point2D.Double(App.MAP_WIDTH + 1, 20.0);
        car = spy(new Car(position));
        doReturn(true).when(car).isOnTrack(position.getX(), position.getY());
        assertThat(car.isAlive()).isFalse();

        // below canvas
        position = new Point2D.Double(10.0, App.MAP_HEIGHT + 1);
        car = spy(new Car(position));
        doReturn(true).when(car).isOnTrack(position.getX(), position.getY());
        assertThat(car.isAlive()).isFalse();
    }

    @Test
    public void updateStateTest() {
        Car carMock = spy(new Car(new Point2D.Double(10, 10)));
        carMock.updateState();
        verify(carMock, atLeastOnce()).isAlive();
    }
}
