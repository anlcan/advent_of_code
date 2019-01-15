package com.advent.eleven;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created on 2019-01-15.
 */
public class Grid {

    public static final int POWER_SQUARE_SIZE = 3;

    public final Map<String, FuelCell> cells = new HashMap<>();
    public final Map<String, Integer> powers = new HashMap<>();

    private final int size;

    public Grid(final int size) {
        this.size = size;
        IntStream.rangeClosed(1, size)
                .forEach(i -> IntStream.rangeClosed(1, size)
                        .forEach(j -> cells.put(key(i, j), new FuelCell(i, j))));


    }

    private String key(final int i, final int j) {
        return i + "," + j;
    }

    /* calculates a row */
    private Map<String, Integer> getPowerCellsLevelInTheRow(int i, final int input) {
        return IntStream.range(1, cells.size() - POWER_SQUARE_SIZE)
                .boxed()
//                .parallel()
                .collect(Collectors
                        .toMap(j -> key(i, j),
                                j -> getPowerCellsLevel(i, j, input)));
    }

    private void setPowerCellsLevelInTheRow(int i, final int input) {
        IntStream.range(1, size - POWER_SQUARE_SIZE)
                .boxed()
//                .parallel()
                .forEach(j -> powers.put(key(i, j), getPowerCellsLevel(i, j, input)));
    }

    public String largestTotalPower(final int input) {

        IntStream.range(1, size- POWER_SQUARE_SIZE )
                .boxed()
                .forEach(i -> setPowerCellsLevelInTheRow(i, input));

        Map.Entry<String, Integer> max = Collections.max(powers.entrySet(),
                Comparator.comparingInt(Map.Entry::getValue));
        System.out.println(max.getValue());
        return max.getKey();

    }

    public Integer getPowerCellsLevel(final int x, final int y, final int input) {

        List<String> powerCellsFor = getPowerCellsFor(x, y);
        powerCellsFor.stream().filter(key -> cells.get(key) == null).forEach(System.out::println);

        return powerCellsFor.stream()
                .mapToInt(key -> cells.get(key).getPowerLevel(input))
                .sum();

    }


    public List<String> getPowerCellsFor(final int x, final int y) {
        return getPowerCellsFor(x, y, POWER_SQUARE_SIZE);
    }

    public List<String> getPowerCellsFor(final int x, final int y, final int squareSize) {

        return IntStream.range(x, x + squareSize)
                .boxed()
                .flatMap(i -> IntStream.range(y, y + squareSize)
                        .boxed()
                        .map(j -> key(i, j)))
                .collect(Collectors.toList());
    }
}
