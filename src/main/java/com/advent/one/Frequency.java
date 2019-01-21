package com.advent.one;

import java.util.List;

/**
 * Created on 2019-01-21.
 */
public class Frequency {

    private int startingFrequency = 0;

    public int apply(final List<Integer> changes){
        return startingFrequency + changes.stream().mapToInt(Integer::intValue).sum();
    }



}
