package thommynator.game;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MenuContent {

    RACE_START("Start"),
    RACE_STOP("Stop"),
    RACE_RESTART("Restart"),
    RACE_SHOW_BEST_CAR("Show only best car (on/off)"),
    NEURALNET_SWITCH_MUTATION("Mutation on/off"),
    NEURALNET_SAVE("Save best..."),
    NEURALNET_LOAD("Load..."),
    NEURALNET_OVERRIDE_BEST("Override all...");

    @Getter
    private final String msg;
}
