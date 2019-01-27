package thommynator.game;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static thommynator.game.MenuContent.*;

public class MenuContentTest {

    @Test
    public void whenLoadingMenuContentThenMessageNotEmpty() {
        assertThat(RACE_START.getMsg()).isNotEmpty();
        assertThat(RACE_STOP.getMsg()).isNotEmpty();
        assertThat(RACE_RESTART.getMsg()).isNotEmpty();
        assertThat(RACE_SHOW_BEST_CAR.getMsg()).isNotEmpty();
        assertThat(NEURALNET_SWITCH_MUTATION.getMsg()).isNotEmpty();
        assertThat(NEURALNET_SAVE.getMsg()).isNotEmpty();
        assertThat(NEURALNET_LOAD.getMsg()).isNotEmpty();
        assertThat(NEURALNET_OVERRIDE_BEST.getMsg()).isNotEmpty();
    }
}
