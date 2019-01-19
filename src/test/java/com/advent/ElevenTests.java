package com.advent;

import com.advent.eleven.FuelCell;
import com.advent.eleven.Grid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created on 2019-01-15.
 */
public class ElevenTests {

    /**
     * The rack ID is 3 + 10 = 13.
     * The power level starts at 13 * 5 = 65.
     * Adding the serial number produces 65 + 8 = 73.
     * Multiplying by the rack ID produces 73 * 13 = 949.
     * The hundreds digit of 949 is 9.
     * Subtracting 5 produces 9 - 5 = 4.
     * <p>
     * Fuel cell at 217,196, grid serial number 39: power level  0.
     * Fuel cell at 101,153, grid serial number 71: power level  4.
     */
    @Test
    void powerLevelTest() {

        assertEquals(4, new FuelCell((short)3, (short)5).getPowerLevel(8));
        assertEquals(-5, new FuelCell((short)122, (short)79).getPowerLevel(57));
        assertEquals(0, new FuelCell((short)217, (short)196).getPowerLevel(39));
        assertEquals(4, new FuelCell((short)101, (short)153).getPowerLevel(71));
    }

    @Test
    void gridTest() {

        Grid g = new Grid(10, 0);
        assertEquals(10 * 10, g.cells.size());

    }

    @Test
    void gridPowerTest() {
        assertEquals("33,45,3", new Grid(300, 18).largestPowerSquare((short)3));
        assertEquals("21,61,3", new Grid(300, 42).largestPowerSquare((short)3));
    }

    @Test
    void gridPuzzle() {
        Grid g = new Grid(300, 5177);
//        System.out.println(g.largestPowerSquare((short)3));
        System.out.println(g.largestPowerSquare());
        ///232,14,7

    }

    @Test
    void gridVariantTest1() {
        /*
        For grid serial number 18, the largest total square (with a total power of 113) is 16x16 and has a top-left corner of 90,269, so its identifier is 90,269,16.
         */
        Grid g = new Grid(300, 18);
        assertEquals("90,269,16", g.largestPowerSquare());
    }


    @Test
    void gridVariantTest2() {
        /*
        For grid serial number 42, the largest total square (with a total power of 119) is 12x12 and has a top-left corner of 232,251, so its identifier is 232,251,12.
         */
        Grid g2 = new Grid(300, 42);
        assertEquals("232,251,12", g2.largestPowerSquare());
    }


    @Test
    void gridSpeedTest() {
//        Grid g = new Grid(150,18);
//        System.out.println(g.largestPowerSquare());

        Grid g = new Grid(150, 18);
        System.out.println(g.largestPowerSquare());
    }

    @Test
    void powerCellLevelTest() {
        Grid g = new Grid(5, 18);
        g.print();
        System.out.println(g.largestPowerSquare());
    }

}
