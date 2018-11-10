package java.thommynator.utils;

import main.java.thommynator.utils.Utils;
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
    }
}
