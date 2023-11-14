package com.kewargs.cs309.core.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Helpers {
    public static String toQuantifierPattern(String s) {
        return s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
    }

    public static String boolToYesNo(Boolean b) {
        return b == null ? "Unknown" : (b ? "Yes" : "No");
    }

    public static String formatMinutes(int minutesFromMidnight) {
        return LocalTime.MIDNIGHT
            .plusMinutes(minutesFromMidnight)
            .format(DateTimeFormatter.ofPattern("h:mm a"));
    }
}
