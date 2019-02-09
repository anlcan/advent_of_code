package com.advent;

import com.advent.thirteen.Cart;
import com.advent.thirteen.Direction;
import com.advent.thirteen.Tracks;
import com.advent.thirteen.Turn;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created on 2019-02-08.
 */
public class ThirteenTests {

    @Test
    void oneDirection(){
        assertEquals(Direction.UP, Direction.LEFT.turn(Turn.RIGHT));
        assertEquals(Direction.DOWN, Direction.LEFT.turn(Turn.LEFT));
        assertEquals(Direction.LEFT, Direction.LEFT.turn(Turn.STRAIGHT));


        assertEquals(Direction.LEFT, Direction.DOWN.turn(Turn.RIGHT));
        assertEquals(Direction.RIGHT, Direction.DOWN.turn(Turn.LEFT));

        assertEquals(Direction.LEFT, Direction.Companion.parse('<'));
        assertEquals(Direction.RIGHT, Direction.Companion.parse('>'));
        assertEquals(Direction.UP, Direction.Companion.parse('^'));
        assertEquals(Direction.DOWN, Direction.Companion.parse('v'));
    }

    @Test
    void compareTest() {
        Cart cart1 = new Cart(0, 0, Direction.UP);
        Cart cart2 = new Cart(1, 0, Direction.UP);
        Cart cart3 = new Cart(1, 1, Direction.UP);
        assertTrue(cart1.compareTo(cart2) < 0);
        assertTrue(cart2.compareTo(cart3) < 0);
    }

    @Test
    void runCaseOne() {
        List<String> input = Util.readStrings("/thirteen/case1.txt");
        Tracks tracks = new Tracks(input);
        while (tracks.tick()){

        }
    }

    @Test
    void runinputOne() {
        List<String> input = Util.readStrings("/thirteen/input.txt");
        Tracks tracks = new Tracks(input);
        while (tracks.tick()){

        }
    }

    @Test
    void runCaseTwo() {
        List<String> input = Util.readStrings("/thirteen/case2.txt");
        Tracks tracks = new Tracks(input);
        while (tracks.tick2()){

        }
    }

    @Test
    void runinputTwo() {
        List<String> input = Util.readStrings("/thirteen/input.txt");
        Tracks tracks = new Tracks(input);
        while (tracks.tick2()){

        }

    }
}
