package com.advent.eleven;

/**
 * Created on 2019-01-16.
 */
public class PowerSquare implements  Comparable{

    public final short x;
    public final short y;

    public final int size;
    private int value;

    public PowerSquare(short x, short y, int size) {
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
        return x + ","+ y + "," + (size + 1);
    }

    @Override
    public int compareTo(Object o) {
        return Integer.compare(((PowerSquare)o).getValue(),value);
    }
}
