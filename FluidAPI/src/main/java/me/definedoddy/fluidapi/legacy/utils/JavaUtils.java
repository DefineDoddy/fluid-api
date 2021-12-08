package me.definedoddy.fluidapi.legacy.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class JavaUtils {
    public static int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static double round(double value) {
        return round(value, 0);
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static int clamp(int value, int min, int max) {
        return Math.max(Math.min(value, max), min);
    }

    public static float clamp(float value, float min, float max) {
        return Math.max(Math.min(value, max), min);
    }

    public static long clamp(long value, long min, long max) {
        return Math.max(Math.min(value, max), min);
    }

    public static double clamp(double value, double min, double max) {
        return Math.max(Math.min(value, max), min);
    }

    public static boolean containsIgnoreCase(String[] array, String string) {
        return containsIgnoreCase(List.of(array), string);
    }

    public static boolean containsIgnoreCase(List<String> list, String string) {
        for (String current : list) {
            if (current.equalsIgnoreCase(string)) {
                return true;
            }
        }
        return false;
    }

    public static String randomLineFromFile(File file) {
        String result = null;
        Random random = new Random();
        int n = 0;
        try {
            for (Scanner scanner = new Scanner(file); scanner.hasNext();) {
                ++n;
                String line = scanner.nextLine();
                if (random.nextInt(n) == 0) {
                    result = line;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
