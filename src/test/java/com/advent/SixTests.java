package com.advent;

import com.advent.six.ChoronalCoordinate;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created on 2019-01-22.
 */
public class SixTests {

    @Test
    void testInput() {
        String[] rawInput = {"1, 1",
                "1, 6",
                "8, 3",
                "3, 4",
                "5, 5",
                "8, 9"};
        List<String> input = Arrays.asList(rawInput);
        assertEquals(17, new ChoronalCoordinate(input).area());
    }

    @Test
    void input_1(){
        List<String> rawInput = Util.readStrings("/six/input.txt");
        System.out.println(new ChoronalCoordinate(rawInput).area());
    }
}
