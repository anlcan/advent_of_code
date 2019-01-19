package com.advent.eleven;

/**
 * Created on 2019-01-15.
 */
public class FuelCell {

    public short x;
    public short y;

    public int value;

    public FuelCell(short x, short y) {
        this.x = x;
        this.y = y;
    }

    public FuelCell(short x, short y, int input) {
        this.x = x;
        this.y = y;
        this.value = getPowerLevel(input);
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
        return getPowerLevel(x, y, input);
    }

    public static int getPowerLevel(int x, int y, final int input) {

        int rackId = x + 10;
        int powerLevel = rackId * y;
        powerLevel += input;
        powerLevel = powerLevel * rackId;
        if (powerLevel < 100) {
            powerLevel = 0;
        } else {
//            powerLevel = Math.floorMod(powerLevel, 1000) % 10;
            powerLevel = (powerLevel / 100) % 10;
        }

        return powerLevel - 5;
    }

    @Override
    public String toString() {
        return String.format("(%2d, %2d | %2d)", x,y,value);

    }
}
