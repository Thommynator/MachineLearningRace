package thommynator.game;

import org.junit.Test;

import java.awt.geom.Point2D;

import static org.assertj.core.api.Assertions.assertThat;

public class PopulationTest {

    @Test
    public void constructorTest() {
        int cars = 5;
        Population population = new Population(cars);
        assertThat(population.getCars().size()).isEqualTo(cars);
        assertThat(population.getAmountOfCars()).isEqualTo(cars);
    }

    @Test
    public void getBestCarTest() {
        Population population = new Population(0);
        // give  car 3 the best fitness
        population.getCars().add(new Car(new Point2D.Double(10, 10)));
        population.getCars().add(new Car(new Point2D.Double(20, 20)));
        population.getCars().add(new Car(new Point2D.Double(30, 30))); // << this should be the best car
        population.getCars().add(new Car(new Point2D.Double(20, 20)));
        assertThat(population.getBestCar()).isEqualToComparingFieldByField(population.getCars().get(2));
    }

    @Test
    public void whenAllCarsAreDeadThenIsAliveReturnsFalse() {
        Population population = new Population(5);
        population.getCars().forEach(car -> car.setAlive(false));
        assertThat(population.isAlive()).isFalse();
    }

    @Test
    public void whenOneOrMoreCarsAreAliveThenIsAliveReturnsTrue() {
        Population population = new Population(5);
        population.getCars().forEach(car -> car.setAlive(false));
        population.getCars().get(2).setAlive(true);
        assertThat(population.isAlive()).isTrue();
    }
}
