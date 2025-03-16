package com.marcos.sensorapp.utils;

public class RangeProcessor {
    public static String process(double value, Range[] ranges, String defaultValue) {
        for (Range range : ranges) {
            if (value >= range.min && value < range.max) {
                return range.message;
            }
        }
        return defaultValue;
    }

    static class Range {
        double min;
        double max;
        String message;

        Range(double min, double max, String message) {
            this.min = min;
            this.max = max;
            this.message = message;
        }
    }
}