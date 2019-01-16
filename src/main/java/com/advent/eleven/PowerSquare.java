package com.advent.eleven;

/**
 * Created on 2019-01-16.
 */
public class PowerSquare implements  Comparable{

    public final int x;
    public final int y;

    public final int size;
    private int value;

    public PowerSquare(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public int getValue() {
        return value;
    }

    public PowerSquare setValue(int value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return x + ","+ y + "," + size;
    }

    @Override
    public int compareTo(Object o) {
        return Integer.compare(((PowerSquare)o).getValue(),value);
    }
}
