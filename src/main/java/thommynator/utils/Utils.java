package thommynator.utils;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    private Utils() {
        // private constructor to hide the implicit public one
    }

    public static double map(double x, double lowerBefore, double upperBefore, double lowerAfter, double upperAfter) {
        return (x - lowerBefore) * (upperAfter - lowerAfter) / (upperBefore - lowerBefore) + lowerAfter;
    }

    public static double random(double lowerBound, double higherBound) {
        return ThreadLocalRandom.current().nextDouble(lowerBound, higherBound);
    }

    public static double constrain(double value, double lowerBound, double upperBound) {
        if (value < lowerBound) {
            return lowerBound;
        }
        if (value > upperBound) {
            return upperBound;
        }
        return value;
    }

    public static boolean areEqual(double a, double b) {
        return Math.abs(a - b) < 1E-10;
    }
}
