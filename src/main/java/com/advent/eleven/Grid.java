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
    public final Map<String, Integer> powersVariant = new HashMap<>();


    private final int gridSize;
//    private final int serialNumber;

    public Grid(final int size) {
        this.gridSize = size;

        IntStream.rangeClosed(1, size)
                .forEach(i -> IntStream.rangeClosed(1, size)
                        .forEach(j -> cells.put(key(i, j), new FuelCell(i, j))));


    }

    private String key(final int i, final int j) {
        return i + "," + j;
    }

    /* calculates a row */
    private Map<String, Integer> getPowerCellsLevelInTheRow(int i, final int input) {
        return IntStream.range(1, cells.size() - DEFAULT_SQUARE_SIZE)
                .boxed()
//                .parallel()
                .collect(Collectors
                        .toMap(j -> key(i, j),
                                j -> getPowerCellsLevel(i, j, input)));
    }

    private void setPowerCellsLevelInTheRow(int i, final int input, final int squareSize) {
        IntStream.range(1, gridSize - squareSize)
                .boxed()
//                .parallel()
                .forEach(j -> powers.put(key(i, j), getPowerCellsLevel(i, j, input, squareSize)));
    }

    public String largestTotalPower(final int input, final int squareSize) {

        return largestTotalPowerEntry(input, squareSize).getKey();
    }


    public Map.Entry<String, Integer> largestTotalPowerEntry(final int input, final int squareSize){
        IntStream.range(1, gridSize - squareSize)
                .boxed()
//                .parallel()
                .forEach(i -> setPowerCellsLevelInTheRow(i, input, squareSize));

        return Collections.max(powers.entrySet(),
                Comparator.comparingInt(Map.Entry::getValue));
    }

    public String largestPowerSquare(final int input) {

        IntStream.range(1, 16)
                .boxed()
//                .parallel()
                .forEach(size -> {
                    Map.Entry<String, Integer> stringIntegerEntry = largestTotalPowerEntry(input, size);
                    powersVariant.put(stringIntegerEntry.getKey()+","+size, stringIntegerEntry.getValue());
                });


        return Collections.max(powersVariant.entrySet(),
                Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

    public Integer getPowerCellsLevel(final int x, final int y, final int input) {
        return getPowerCellsLevel(x,y,input, DEFAULT_SQUARE_SIZE);
    }

    public List<String> getPowerCellsFor(final int x, final int y) {
        return getPowerCellsFor(x, y, DEFAULT_SQUARE_SIZE);
    }

    public String largestTotalPower(final int input) {
        return largestTotalPower(input, DEFAULT_SQUARE_SIZE);
    }

    public Integer getPowerCellsLevel(final int x, final int y, final int input, final int squareSize) {

        List<String> powerCellsFor = getPowerCellsFor(x, y, squareSize);
        //powerCellsFor.stream().filter(key -> cells.get(key) == null).forEach(System.out::println);

        return powerCellsFor.stream()
                .mapToInt(key -> cells.get(key).getPowerLevel(input))
                .sum();
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
