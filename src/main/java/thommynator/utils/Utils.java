package thommynator.utils;

public class Utils {

    public static double map(double x, double lowerBefore, double upperBefore, double lowerAfter, double upperAfter) {
        return (x - lowerBefore) * (upperAfter - lowerAfter) / (upperBefore - lowerBefore) + lowerAfter;
    }
}
