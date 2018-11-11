package thommynator.utils;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static double map(double x, double lowerBefore, double upperBefore, double lowerAfter, double upperAfter) {
        return (x - lowerBefore) * (upperAfter - lowerAfter) / (upperBefore - lowerBefore) + lowerAfter;
    }

    public static double random(double lowerBound, double higherBound) {
        return ThreadLocalRandom.current().nextDouble(lowerBound, higherBound);
    }
}
