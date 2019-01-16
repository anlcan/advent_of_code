package com.advent.eleven;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created on 2019-01-15.
 */
public class Grid {

    public static final int DEFAULT_SQUARE_SIZE = 3;

    public final Map<String, FuelCell> cells = new HashMap<>();
    public final Map<String, Integer> powers = new HashMap<>();
    public final Map<String, PowerSquare> powersVariant = new HashMap<>();

    private final int gridSize;
    private final int serialNumber;

    public Grid(final int size, final int serialNumber) {
        this.gridSize = size;
        this.serialNumber = serialNumber;

        IntStream.rangeClosed(1, size)
                .forEach(i -> IntStream.rangeClosed(1, size)
                        .forEach(j -> cells.put(key(i, j), new FuelCell(i, j, serialNumber))));

    }

    private String key(final int i, final int j) {
        return i + "," + j;
    }

    /* calculates a row */
    private Map<String, Integer> getPowerCellsLevelInTheRow(int i) {
        return IntStream.range(1, cells.size() - DEFAULT_SQUARE_SIZE)
                .boxed()
//                .parallel()
                .collect(Collectors
                        .toMap(j -> key(i, j),
                                j -> getPowerCellsLevel(i, j, serialNumber)));
    }

    private void setPowerCellsLevelInTheRow(final int i, final int squareSize) {
        IntStream.range(1, gridSize - squareSize)
                .boxed()
//                .parallel()
                .forEach(j -> powers.put(key(i, j), getPowerCellsLevel(i, j, squareSize)));
    }

    public String largestTotalPower(final int squareSize) {

        return largestTotalPowerEntry(squareSize).getKey();
    }


    private Map.Entry<String, Integer> largestTotalPowerEntry(final int squareSize) {
        IntStream.range(1, gridSize - squareSize)
                .boxed()
                .forEach(i -> setPowerCellsLevelInTheRow(i, squareSize));

        return Collections.max(powers.entrySet(),
                Comparator.comparingInt(Map.Entry::getValue));
    }

    public String largestPowerSquare() {

//        IntStream.range(1, gridSize)
//                .boxed()
//                .forEach(x -> IntStream.range(1, gridSize)
//                        .parallel()
//                        .forEach(y -> IntStream.range(0, Math.min(gridSize -x, gridSize - y))
//                                .boxed()
//
//                                .forEach(s -> {
//
//
//                                    PowerSquare ps = new PowerSquare(x,y,s);
//                                    ps.setValue(getPowerCellsLevel(x, y, s));
//
//                                    powersVariant.put(ps.toString(), ps);
//                                })));

        for ( int x = 1; x < gridSize; x++) {
            for (int y = 1; y < gridSize; y++) {
//                for (int s = 0; s < Math.min(gridSize - x, gridSize - y); s++) {
//                    PowerSquare ps = new PowerSquare(x, y, s);
//                    ps.setValue(getPowerCellsLevel(x, y, s));
//
//                    powersVariant.put(ps.toString(), ps);
//                }
                final int x_ = x;
                final int y_ = y;
                IntStream.range(0, Math.min(gridSize - x_, gridSize - y_))
                                .boxed()
                                .parallel()
                                .forEach(s -> {


                                    PowerSquare ps = new PowerSquare(x_,y_,s);
                                    ps.setValue(getPowerCellsLevel(x_, y_, s));

                                    powersVariant.put(ps.toString(), ps);
                                });
            }

        }

//        return Collections.max(powersVariant.entrySet(),
//                Comparator.comparingInt(Map.Entry::getValue)).getKey();

        List<PowerSquare> ps = new ArrayList(powersVariant.values());


        Collections.sort(ps);
        return ps.get(0).toString();
    }


    public Integer getPowerCellsLevel(final int x, final int y, final int squareSize) {

//       return IntStream.range(x, x + squareSize)
//                .boxed()
//                .mapToInt(i -> IntStream.range(y, y + squareSize)
//                        .boxed()
//                        .mapToInt(j -> cells.get(key(i, j)).value)
//                        .sum())
//                .sum();
        if (squareSize > 0) {
            PowerSquare smaller = new PowerSquare(x, y, squareSize - 1);
            PowerSquare powerSquare = powersVariant.get(smaller.toString());
            if (powerSquare != null) {
                int sum = powerSquare.getValue();
                for (int i = x; i < x + squareSize; i++) {
                    sum += cells.get(key(i, y+squareSize)).value;
                }
                for (int j = y; j < y + squareSize; j++) {
                    sum += cells.get(key(x+squareSize, j)).value;
                }

                return sum;

            }

        }

        int sum = 0;
        for (int i = x; i < x + squareSize; i++) {
            for (int j = y; j < y + squareSize; j++) {
                sum += cells.get(key(i, j)).value;
            }
        }
        return sum;
    }

    public Integer getPowerCellsLevel(final int x, final int y) {
        return getPowerCellsLevel(x, y, DEFAULT_SQUARE_SIZE);
    }


    public String largestTotalPower() {
        return largestTotalPower(DEFAULT_SQUARE_SIZE);
    }

}
