package com.advent.eleven;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * Created on 2019-01-15.
 */
public class Grid {

    public static final short ANY_SQUARE_SIZE = -1;

    public final Map<String, FuelCell> cells;
    public final PowerSquare[][][] powersVariant;

    private final int gridSize;
    private final short serialNumber;


    public Grid(final int size, final int serialNumber) {
        this.gridSize = size;
        this.serialNumber = (short)serialNumber;

        this.cells = new HashMap<>(size * size);
        this.powersVariant = new PowerSquare[size][size][size];
        IntStream.rangeClosed(1, size)
                .forEach(i -> IntStream.rangeClosed(1, size)
                        .forEach(j -> cells.put(key(i, j), new FuelCell((short)i, (short)j, serialNumber))));

    }

    private String key(final int i, final int j) {
        return i + "," + j;
    }


    public String largestPowerSquare() {
        return largestPowerSquare(ANY_SQUARE_SIZE);
    }

    public String largestPowerSquare(final short desiredSize) {


        Consumer<FuelCell> fixed = desiredSize == ANY_SQUARE_SIZE ? new AllSquares() : new FixedSquare(desiredSize);
        cells.values().stream()
                .parallel()
                .forEach(fixed);



        SortedSet<PowerSquare> sortedSet = new TreeSet<>();

        for (int i = 0; i < this.gridSize; i++) {
            for (int j = 0; j < this.gridSize; j++) {
                for (int k = 0; k < this.gridSize; k++) {
                    if (powersVariant[i][j][k] != null){
                        sortedSet.add(powersVariant[i][j][k]);
                    }
                }
            }
        }

        return sortedSet.first().toString();

    }

    public Integer getPowerCellsLevel(final short x, final short y, final int squareSize) {

        if (squareSize > 1) {
//            PowerSquare smaller = new PowerSquare(x, y, squareSize - 1);
            PowerSquare powerSquare = powersVariant[x][y][squareSize-1];
            if (powerSquare != null) {
                int sum = powerSquare.getValue();

                short y_= (short) (y+ squareSize);
                short x_ = (short) (x+ squareSize);

                for (short i = x; i <= x + squareSize; i++) {
                    sum +=  FuelCell.getPowerLevel(i, y_, serialNumber);
                }
                for (short j = y; j <= y + squareSize - 1; j++) {
                    sum +=  FuelCell.getPowerLevel(x_, j, serialNumber);
                }

                return sum;
            }
        }

        int sum = 0;
        for (short i = x; i <= x + squareSize; i++) {
            for (short j = y; j <= y + squareSize; j++) {
                sum += new FuelCell(i, j, serialNumber).value;
            }
        }
        return sum;
    }

    public void print() {
        IntStream.rangeClosed(1, gridSize).boxed()
                .forEach(x -> IntStream.rangeClosed(1, gridSize).boxed()
                        .forEach(y -> {
                            FuelCell fc = cells.get(key(x, y));

                            System.out.print(String.format("%3d", fc.value));
                            if (fc.y % gridSize == 0) {
                                System.out.println();
                            }
                        }));
    }

    private class BaseSquare {
        void setPowerVariant(FuelCell fc, int desired) {
            PowerSquare ps = new PowerSquare(fc.x, fc.y, desired);
            ps.setValue(getPowerCellsLevel(fc.x, fc.y, desired));

            powersVariant[fc.x][fc.y][desired] = ps;
        }
    }

    private final class FixedSquare extends BaseSquare implements Consumer<FuelCell> {
        final short desired;

        FixedSquare(short desired) {
            this.desired = desired;
        }

        @Override
        public void accept(FuelCell fc) {

            setPowerVariant(fc, desired);
        }
    }

    private final class AllSquares extends BaseSquare implements Consumer<FuelCell> {

        @Override
        public void accept(FuelCell fc) {
            IntStream.rangeClosed(1, Math.min(gridSize - fc.x, gridSize - fc.y))
//                    .parallel()
                    .forEach(s -> setPowerVariant(fc, s));
        }
    }
}
