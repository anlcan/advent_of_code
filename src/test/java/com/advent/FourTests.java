package com.advent;

import com.advent.four.GuardWatcher;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created on 2019-01-22.
 */
public class FourTests {

    @Test
    void test(){
        String[]input = {"[1518-11-01 00:00] Guard #10 begins shift" ,
                "[1518-11-01 00:05] falls asleep" ,
                "[1518-11-01 00:25] wakes up" ,
                "[1518-11-01 00:30] falls asleep" ,
                "[1518-11-01 00:55] wakes up" ,
                "[1518-11-01 23:58] Guard #99 begins shift" ,
                "[1518-11-02 00:40] falls asleep" ,
                "[1518-11-02 00:50] wakes up" ,
                "[1518-11-03 00:05] Guard #10 begins shift" ,
                "[1518-11-03 00:24] falls asleep" ,
                "[1518-11-03 00:29] wakes up" ,
                "[1518-11-04 00:02] Guard #99 begins shift" ,
                "[1518-11-04 00:36] falls asleep" ,
                "[1518-11-04 00:46] wakes up" ,
                "[1518-11-05 00:03] Guard #99 begins shift" ,
                "[1518-11-05 00:45] falls asleep" ,
                "[1518-11-05 00:55] wakes up" };

        List<String> logs = new ArrayList<>(Arrays.asList(input));
        List<String> shuffled = new ArrayList<>(Arrays.asList(input));
        Collections.shuffle(shuffled);

        assertEquals(logs, GuardWatcher.sortLogs(shuffled));
        assertEquals(240, GuardWatcher.strategyOne(logs));
        assertEquals(4455, GuardWatcher.strategyTwo(logs));
    }

    @Test
    void input1(){
        List<String> rawLogs = Util.readStrings("/four/input.txt");
        assertEquals(67558,GuardWatcher.strategyOne(rawLogs));
    }

    @Test
    void input2(){
        List<String> rawLogs = Util.readStrings("/four/input.txt");
        assertEquals(78990,GuardWatcher.strategyTwo(rawLogs));
    }
}
