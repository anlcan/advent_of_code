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
     *
     * Fuel cell at 217,196, grid serial number 39: power level  0.
     * Fuel cell at 101,153, grid serial number 71: power level  4.
     */
    @Test
    void powerLevelTest(){

        assertEquals(4, new FuelCell(3,5).getPowerLevel(8));
        assertEquals(-5, new FuelCell(122, 79).getPowerLevel(57));
        assertEquals(0, new FuelCell(217, 196).getPowerLevel(39));
        assertEquals(4, new FuelCell(101, 153).getPowerLevel(71));
    }

    @Test
    void gridTest(){

        Grid g = new Grid(10);
        assertEquals(10*10, g.cells.size());

        assertEquals(4, g.getPowerCellsFor(0,0,2).size());
        assertEquals(9, g.getPowerCellsFor(0,0,3).size());
        assertEquals(16, g.getPowerCellsFor(0,0,4).size());


    }

    @Test
    void gridPowerTest() {
        Grid g = new Grid(300);
        assertEquals("33,45", g.largestTotalPower(18));
        assertEquals("21,61", g.largestTotalPower(42));

    }

    @Test
    void gridPuzzle(){
        Grid g = new Grid(300);
        System.out.println( g.largestTotalPower(5177));

    }


}
