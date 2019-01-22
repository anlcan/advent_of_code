package com.advent.five;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Created on 2019-01-22.
 */
public class Polymer {

    public static int DIFF = abs('A' - 'a');
    String initialUnits;
//    String finalUnits;

    public Polymer(String initialInput) {
        this.initialUnits = initialInput;

    }

    public int shortestWithoutProblematic() {
        List<Integer> results = new ArrayList<>();
        for (char p ='a'; p<'z'; p++){
            results.add(reactProblematic(p));
        }

        Collections.sort(results);
        return results.get(0);
    }

    public int reactProblematic(char p) {
        char[] units = initialUnits.toCharArray();
        char[] acc = {};
        for (int i = 0; i < units.length; i++) {
            acc = react(acc, units[i], p);

        }
        //finalUnits = new String(acc);
        return acc.length;
    }

    public char[] react(char[] a, char b, char problematic) {
        if (b == problematic || b + DIFF == (int)problematic) {
//            System.out.println("removing "+b);
            return a;
        } else {
            return react(a, b);
        }
    }
    public int react() {
        char[] units = initialUnits.toCharArray();
        char[] acc = {units[0]};
        for (int i = 1; i < units.length; i++) {
            acc = react(acc, units[i]);
        }
        //finalUnits = new String(acc);
        return acc.length;
    }


    public char[] react(char[] a, char b) {

        int length = a.length;
        if (length == 0) {
            a = Arrays.copyOf(a, 1);
            a[0] = b;
            return a;
        }

        if (DIFF == abs(a[length - 1] - b)) {
            return Arrays.copyOf(a, length - 1);
        } else {
            char[] result = Arrays.copyOf(a, length + 1);
            result[length] = b;
            return result;
        }
    }
}
