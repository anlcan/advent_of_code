package com.advent;

import com.advent.five.Polymer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created on 2019-01-22.
 */
public class FiveTests {

    @Test
    void testPolymer() {
        assertEquals(0, new Polymer("zZ").react());
        assertEquals(1, new Polymer("aBb").react());
        assertEquals(0, new Polymer("abBA").react());
        assertEquals(4, new Polymer("abAB").react());
        assertEquals(6, new Polymer("aabAAB").react());
        assertEquals(10, new Polymer("dabAcCaCBAcCcaDA").react());
        assertEquals(4, new Polymer("dabAcCaCBAcCcaDA").shortestWithoutProblematic());
    }


    @Test
    void input1() {
        List<String> input = Util.readStrings("/five/input.txt");
        assert (input.size() > 0);
        System.out.println(new Polymer(input.get(0)).react());
    }

    @Test
    void input2() {
        List<String> input = Util.readStrings("/five/input.txt");
        assert (input.size() > 0);
        System.out.println(new Polymer(input.get(0)).shortestWithoutProblematic());
    }
}
