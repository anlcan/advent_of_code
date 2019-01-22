package com.advent.five;

import java.util.Arrays;

import static java.lang.Math.abs;

/**
 * Created on 2019-01-22.
 */
public class Polymer {

    public static int DIFF = abs('A' - 'a');
    String initialUnits;
    String finalUnits;

    public Polymer(String initialInput) {
        this.initialUnits = initialInput;
        react();
    }

    public Polymer react() {
        char[] units = initialUnits.toCharArray();
        char[] acc = {units[0]};
        for (int i = 1; i < units.length ; i++) {
            acc = react(acc, units[i]);
        }

        finalUnits = new String(acc);
        return this;
    }

    public char[] react(char[] a, char b) {

        int length = a.length;
        if (length == 0) {
            a = Arrays.copyOf(a,1);
            a[0] = b;
            return a;
        }

        if (DIFF == abs(a[length - 1] - b)) {
            return Arrays.copyOf(a, length -1);
        } else {
            char[] result = Arrays.copyOf(a, length+1);
            result[length] = b;
            return result;
        }
    }

    public int size() {
        return finalUnits.length();
    }
}
