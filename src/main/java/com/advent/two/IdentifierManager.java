package com.advent.two;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2019-01-21.
 */
public class IdentifierManager {


    private List<String> ids;

    public IdentifierManager(List<String> ids) {
        this.ids = new ArrayList<>(ids);
    }

    public int checksum() {

        long two = ids.stream()
                .filter(this::multipleOfThree)
                .count();

        long three = ids.stream()
                .filter(this::multipleOfTwo)
                .count();

        return (int) (two * three);
    }

    private boolean multipleOf(final String s, final int n) {
        Map<String, Integer> meh = new HashMap<>();
        s.chars().forEach(c -> {
            String ch = String.valueOf(c);
            meh.merge(ch, 1, (a, b) -> b + a);
        });

        return meh.values().stream().anyMatch(c -> c.equals(n));

    }

    public boolean multipleOfTwo(final String s) {
        return multipleOf(s, 2);
    }


    public boolean multipleOfThree(final String s) {
        return multipleOf(s, 3);
    }


    public String commonLetters() {
        for (String first : ids) {
            for (String second : ids) {
                if (diff(first, second, 1)) {
                    return common(first, second);
                }
            }
        }

        throw new RuntimeException("No match found");
    }

    public String common(String first, String second) {
        StringBuffer buffer = new StringBuffer();
        char[] firstChars = first.toCharArray();
        char[] secondChars = second.toCharArray();
        for (int j = 0; j < firstChars.length; j++) {
            if (firstChars[j] == secondChars[j]) {
                buffer.append(firstChars[j]);
            }
        }

        return buffer.toString();

    }

    public boolean diff(String first, String second, int i) {
        char[] chars = first.toCharArray();
        if (chars.length != second.toCharArray().length) {
            return false;
        } else {

            return i == (first.length() - common(first, second).length());
        }
    }
}
