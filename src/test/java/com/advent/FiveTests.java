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
    void testPolymer(){
        assertEquals(0, new Polymer("zZ").size());
        assertEquals(1, new Polymer("aBb").size());
        assertEquals(0, new Polymer("abBA").size());
        assertEquals(4, new Polymer("abAB").size());
        assertEquals(6, new Polymer("aabAAB").size());
        assertEquals(10, new Polymer("dabAcCaCBAcCcaDA").size());
    }


    @Test
    void input1 (){
        List<String> input = Util.readStrings("/five/input.txt");
        assert(input.size() >0);
        System.out.println(new Polymer(input.get(0)).size());
    }
}
