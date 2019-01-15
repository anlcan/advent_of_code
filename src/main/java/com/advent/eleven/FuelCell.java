package com.advent.eleven;

/**
 * Created on 2019-01-15.
 */
public class FuelCell {

    public int x;
    public int y;

    public FuelCell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Find the fuel cell's rack ID, which is its X coordinate plus 10.
     * Begin with a power level of the rack ID times the Y coordinate.
     * Increase the power level by the value of the grid serial number (your puzzle input).
     * Set the power level to itself multiplied by the rack ID.
     * Keep only the hundreds digit of the power level (so 12345 becomes 3; numbers with no hundreds digit become 0).
     * Subtract 5 from the power level.
     */
    public int getPowerLevel(final int input) {
        int rackId = x+10;
        int powerLevel = rackId * y;
        powerLevel += input;
        powerLevel = powerLevel * rackId;
        if (powerLevel < 100) {
            powerLevel = 0;
        } else {
//            powerLevel = Math.floorMod(powerLevel, 1000) % 10;
            powerLevel = (powerLevel /100)  % 10;
        }

        return  powerLevel-5;

    }
}
