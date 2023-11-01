package com.kewargs.cs309.core.utils;

public class Helpers {
    public static String toQuantifierPattern(String s) {
        return s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
    }
}
