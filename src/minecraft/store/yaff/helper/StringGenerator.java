package store.yaff.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public record StringGenerator(boolean useLower, boolean useUpper, boolean useDigits, boolean useSymbols) {
    public static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    public static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String DIGITS = "0123456789";
    public static final String SYMBOLS = "!@#$%&*()_+-=[]|,./?><";

    public String generate(int length) {
        StringBuilder generatedString = new StringBuilder(length);
        Random random = new Random(System.nanoTime());
        List<String> charCategories = new ArrayList<>(4);
        if (useLower) {
            charCategories.add(LOWER);
        }
        if (useUpper) {
            charCategories.add(UPPER);
        }
        if (useDigits) {
            charCategories.add(DIGITS);
        }
        if (useSymbols) {
            charCategories.add(SYMBOLS);
        }
        for (int i = 0; i < length; i++) {
            String charCategory = charCategories.get(random.nextInt(charCategories.size()));
            int position = random.nextInt(charCategory.length());
            generatedString.append(charCategory.charAt(position));
        }
        return new String(generatedString);
    }

}
