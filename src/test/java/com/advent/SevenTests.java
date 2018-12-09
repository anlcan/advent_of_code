package com.advent;

import com.advent.seven.Step;
import com.advent.seven.StepParser;
import com.advent.seven.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SevenTests {

    public final String test = "Step L must be finished before step M can begin.\n" +
            "Step B must be finished before step S can begin.\n" +
            "Step F must be finished before step M can begin.\n" +
            "Step C must be finished before step P can begin.\n" +
            "Step D must be finished before step V can begin.\n" +
            "Step T must be finished before step J can begin.\n" +
            "Step E must be finished before step S can begin.\n" +
            "Step Z must be finished before step V can begin.\n" +
            "Step X must be finished before step U can begin.\n" +
            "Step G must be finished before step I can begin.\n" +
            "Step V must be finished before step W can begin.\n" +
            "Step H must be finished before step O can begin.\n" +
            "Step M must be finished before step S can begin.\n" +
            "Step K must be finished before step I can begin.\n" +
            "Step I must be finished before step J can begin.\n" +
            "Step A must be finished before step W can begin.\n" +
            "Step J must be finished before step R can begin.\n" +
            "Step Q must be finished before step S can begin.\n" +
            "Step Y must be finished before step R can begin.\n" +
            "Step O must be finished before step W can begin.\n" +
            "Step R must be finished before step U can begin.\n" +
            "Step P must be finished before step U can begin.\n" +
            "Step S must be finished before step W can begin.\n" +
            "Step U must be finished before step N can begin.\n" +
            "Step W must be finished before step N can begin.\n" +
            "Step P must be finished before step W can begin.\n" +
            "Step J must be finished before step W can begin.\n" +
            "Step F must be finished before step G can begin.\n" +
            "Step U must be finished before step W can begin.\n" +
            "Step Y must be finished before step P can begin.\n" +
            "Step Z must be finished before step I can begin.\n" +
            "Step R must be finished before step W can begin.\n" +
            "Step T must be finished before step X can begin.\n" +
            "Step Q must be finished before step R can begin.\n" +
            "Step B must be finished before step P can begin.\n" +
            "Step Z must be finished before step U can begin.\n" +
            "Step H must be finished before step Y can begin.\n" +
            "Step G must be finished before step A can begin.\n" +
            "Step O must be finished before step P can begin.\n" +
            "Step F must be finished before step D can begin.\n" +
            "Step F must be finished before step Q can begin.\n" +
            "Step T must be finished before step U can begin.\n" +
            "Step I must be finished before step O can begin.\n" +
            "Step K must be finished before step R can begin.\n" +
            "Step E must be finished before step J can begin.\n" +
            "Step Z must be finished before step G can begin.\n" +
            "Step Y must be finished before step W can begin.\n" +
            "Step L must be finished before step V can begin.\n" +
            "Step E must be finished before step X can begin.\n" +
            "Step E must be finished before step U can begin.\n" +
            "Step A must be finished before step N can begin.\n" +
            "Step G must be finished before step N can begin.\n" +
            "Step B must be finished before step C can begin.\n" +
            "Step M must be finished before step U can begin.\n" +
            "Step G must be finished before step R can begin.\n" +
            "Step R must be finished before step N can begin.\n" +
            "Step M must be finished before step K can begin.\n" +
            "Step C must be finished before step E can begin.\n" +
            "Step B must be finished before step U can begin.\n" +
            "Step J must be finished before step Y can begin.\n" +
            "Step X must be finished before step H can begin.\n" +
            "Step E must be finished before step W can begin.\n" +
            "Step A must be finished before step Y can begin.\n" +
            "Step I must be finished before step A can begin.\n" +
            "Step D must be finished before step W can begin.\n" +
            "Step B must be finished before step I can begin.\n" +
            "Step H must be finished before step P can begin.\n" +
            "Step A must be finished before step S can begin.\n" +
            "Step P must be finished before step N can begin.\n" +
            "Step V must be finished before step J can begin.\n" +
            "Step L must be finished before step D can begin.\n" +
            "Step C must be finished before step R can begin.\n" +
            "Step Z must be finished before step Y can begin.\n" +
            "Step F must be finished before step S can begin.\n" +
            "Step O must be finished before step N can begin.\n" +
            "Step X must be finished before step R can begin.\n" +
            "Step E must be finished before step Q can begin.\n" +
            "Step L must be finished before step Z can begin.\n" +
            "Step D must be finished before step O can begin.\n" +
            "Step Y must be finished before step O can begin.\n" +
            "Step S must be finished before step N can begin.\n" +
            "Step D must be finished before step X can begin.\n" +
            "Step T must be finished before step A can begin.\n" +
            "Step Q must be finished before step Y can begin.\n" +
            "Step K must be finished before step J can begin.\n" +
            "Step C must be finished before step S can begin.\n" +
            "Step M must be finished before step P can begin.\n" +
            "Step O must be finished before step R can begin.\n" +
            "Step E must be finished before step Y can begin.\n" +
            "Step V must be finished before step O can begin.\n" +
            "Step D must be finished before step Q can begin.\n" +
            "Step D must be finished before step J can begin.\n" +
            "Step D must be finished before step R can begin.\n" +
            "Step R must be finished before step P can begin.\n" +
            "Step X must be finished before step O can begin.\n" +
            "Step C must be finished before step K can begin.\n" +
            "Step L must be finished before step R can begin.\n" +
            "Step Q must be finished before step U can begin.\n" +
            "Step G must be finished before step K can begin.\n" +
            "Step I must be finished before step S can begin.\n" +
            "Step L must be finished before step X can begin.\n";


    final String smallInput = "" +
            "Step C must be finished before step A can begin.\n" +
            "Step C must be finished before step F can begin.\n" +
            "Step A must be finished before step B can begin.\n" +
            "Step A must be finished before step D can begin.\n" +
            "Step B must be finished before step E can begin.\n" +
            "Step D must be finished before step E can begin.\n" +
            "Step F must be finished before step E can begin.\n";

    @Test
    public void parseTest() {
        StepParser stepParser = new StepParser();
        stepParser.parseSingle("Step A must be finished before step C can begin.");
        stepParser.parseSingle("Step B must be finished before step C can begin.");
        stepParser.parseSingle("Step X must be finished before step A can begin.");
        stepParser.parseSingle("Step X must be finished before step B can begin.");


        assertEquals("X", stepParser.findReady().get(0).getName());
        assertEquals("XABC", stepParser.result());
    }


    @Test
    public void stepTest() {
        Step.WORKERS.add(new Worker("Worker 1"));
        Step.WORKERS.add(new Worker("Worker 2"));

        StepParser stepParser = new StepParser();
        stepParser.parseMultiple(smallInput);
        //assertEquals("CABDFE", stepParser.result());
        assertEquals(15, stepParser.time());
    }

    @Test
    public void test1() {
        StepParser stepParser = new StepParser();
        Step.WORKERS.add(new Worker("Worker 1"));
        Step.WORKERS.add(new Worker("Worker 2"));
        Step.WORKERS.add(new Worker("Worker 3"));
        Step.WORKERS.add(new Worker("Worker 4"));
        Step.WORKERS.add(new Worker("Worker 5"));
        Step.TIME = 60;

        stepParser.parseMultiple(test);
        System.out.println(stepParser.time());
    }
}
