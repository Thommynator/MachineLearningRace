package thommynator.game;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MenuContent {

    RACE_START("Start"),
    RACE_STOP("Stop"),
    RACE_RESTART("Restart"),
    RACE_NEWTRACK("New Racetrack"),
    NEURALNET_SAVE("Save best..."),
    NEURALNET_LOAD("Load..."),
    NEURALNET_OVERRIDE_BEST("Override all...");

    @Getter
    private final String msg;
}
