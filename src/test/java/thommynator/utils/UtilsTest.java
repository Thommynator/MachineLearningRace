package thommynator.utils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilsTest {

    @Test
    public void mapTest() {
        double after;

        after = Utils.map(5, 0, 10, 0, 100);
        assertThat(after).isEqualTo(50);

        after = Utils.map(5, 0, 5, 10, 20);
        assertThat(after).isEqualTo(20);

        after = Utils.map(0, 0, 10, 10, 20);
        assertThat(after).isEqualTo(10);

        after = Utils.map(-10, -30, 0, 10, 40);
        assertThat(after).isEqualTo(30);
    }

    @Test
    public void constrainTest() {
        double result;
        result = Utils.constrain(-5, 0, 10);
        assertThat(result).isEqualTo(0);

        result = Utils.constrain(0, 0, 10);
        assertThat(result).isEqualTo(0);

        result = Utils.constrain(5, 0, 10);
        assertThat(result).isEqualTo(5);

        result = Utils.constrain(10, 0, 10);
        assertThat(result).isEqualTo(10);

        result = Utils.constrain(20, 0, 10);
        assertThat(result).isEqualTo(10);
    }

    @Test
    public void randomInBoundsTest() {
        double result;
        for (int i = 0; i < 200; i++) {
            result = Utils.random(0, 1);
            assertThat(result).isGreaterThanOrEqualTo(0.0);
            assertThat(result).isLessThan(1.0);
        }
    }

    @Test
    public void areEqualTest() {
        assertThat(Utils.areEqual(-1.0, 1.0)).isFalse();
        assertThat(Utils.areEqual(1.0, -1.0)).isFalse();
        assertThat(Utils.areEqual(-1.0, -1.0)).isTrue();
        assertThat(Utils.areEqual(1.0, 1.0)).isTrue();
        assertThat(Utils.areEqual(1.0, 0.99999999999)).isTrue();
    }
}
