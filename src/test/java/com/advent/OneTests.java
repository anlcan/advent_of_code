package com.advent;

import com.advent.one.Frequency;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created on 2019-01-21.
 */
public class OneTests {

    @Test
    void frequencyTest() {
        Frequency f = new Frequency(0);
        Integer[] t = {1, -2, -3};
        assertEquals(-6, f.apply(Stream.of(t).collect(Collectors.toList())));
    }

    @Test
    void repeatingFrequencyTest() {
        Frequency f = new Frequency(0);
        Integer[] t = {+1, -2, +3, +1};
        assertEquals(2, f.getFirstRepeatingFrequency(Arrays.asList(t)));
    }


    @Test
    void input1() {
        Frequency f = new Frequency(0);
        java.util.List<Integer> integers = Util.readFrequency( "/one/input_1.txt");
        int apply = f.apply(integers);
        System.out.println(apply);
    }

    @Test
    void input2() {
        Frequency f = new Frequency(0);
        java.util.List<Integer> integers = Util.readFrequency( "/one/input_1.txt");
        int apply = f.getFirstRepeatingFrequency(integers);
        System.out.println(apply);
    }

}
