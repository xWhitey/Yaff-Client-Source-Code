package store.yaff.helper;

import java.util.Arrays;

public class Random {
    public static int getRandomInt(int min, int max) {
        return (int) ((java.lang.Math.random() * (max - min)) + min);
    }

    public static int getRandomIntExclude(int min, int max, Integer... excludeIntegers) {
        while (true) {
            int randomInteger = (int) ((java.lang.Math.random() * (max - min)) + min);
            if (!Arrays.stream(excludeIntegers).toList().contains(randomInteger)) {
                return randomInteger;
            }
        }
    }

    public static float getRandomFloat(float min, float max) {
        return (float) ((java.lang.Math.random() * (max - min)) + min);
    }

    public static float getRandomFloatExclude(float min, float max, Float... excludeFloats) {
        while (true) {
            float randomFloat = (float) ((java.lang.Math.random() * (max - min)) + min);
            if (!Arrays.stream(excludeFloats).toList().contains(randomFloat)) {
                return randomFloat;
            }
        }
    }

}
