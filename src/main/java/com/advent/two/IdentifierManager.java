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

        int two = ids.stream()
                .mapToInt(c -> multipleOfThree(c) ? 1 : 0)
                .sum();

        int three = ids.stream()
                .mapToInt(c -> multipleOfTwo(c) ? 1 : 0)
                .sum();

        return two * three;
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


}
