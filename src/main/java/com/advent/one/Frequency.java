package com.advent.one;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019-01-21.
 */
public class Frequency {
    public static final int NO_FREQUENCY = Integer.MIN_VALUE;

    private int startingFrequency = 0;
    private List<Integer> pastFrequencies = new ArrayList<>();

    public Frequency(int startingFrequency) {
        this.startingFrequency = startingFrequency;
    }

    public int getFirstRepeatingFrequency(final List<Integer> changes){

        for (int i = 0; i < changes.size(); i++ ) {
            startingFrequency += changes.get(i);
            if (pastFrequencies.contains(startingFrequency)){
                return startingFrequency;
            } else {
                pastFrequencies.add(startingFrequency);
            }
        }

        return getFirstRepeatingFrequency(changes);
    }

    public int apply(final List<Integer> changes){
        return startingFrequency + changes.stream().mapToInt(Integer::intValue).sum();
    }

    public List<Integer> getPastFrequencies() {
        return pastFrequencies;
    }

    public Frequency setStartingFrequency(int startingFrequency) {
        this.startingFrequency = startingFrequency;
        return this;
    }


}
