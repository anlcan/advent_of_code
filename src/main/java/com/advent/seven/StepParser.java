package com.advent.seven;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StepParser {

    public static final String regex = "Step ([A-Z]) must be finished before step ([A-Z]) can begin.";
    public static final Pattern pattern = Pattern.compile(regex);

    public final Map<String, Step> steps = new HashMap<>();

    private Step getOrNew(final String name){
        if (steps.containsKey(name)){
            return steps.get(name);
        } else {
            Step value = new Step(name);
            steps.put(name, value);
            return value;
        }
    }

    public String result() {

        Step missed = new Step("");
        List<Step> ready = findReady();
        ready.forEach(missed::addDependee);
        missed.makeDo();

        return missed.traverse();
    }

    public int time() {

        Step missed = new Step("");
        List<Step> ready = findReady();
        ready.forEach(missed::addDependee);
        missed.makeDo();

        return missed.time();
    }


    public List<Step> findReady(){
        return steps.values().stream()
                .filter(x -> x.getDependencies().size() ==0)
                .sorted()
                .collect(Collectors.toList());

    }

    public List<Step> parseMultiple(final String input) {
        return Arrays.stream(input.split("\n"))
                .map(this::parseSingle)
                .collect(Collectors.toList());

    }

    public Step parseSimple(final String finishedBefore, final String canBegin){
        Step s = getOrNew(canBegin);
        s.addDependency(getOrNew(finishedBefore));
        return s;

    }

    public  Step parseSingle(final String input) {
        Matcher matcher = pattern.matcher(input);
        matcher.find();
        String dep = matcher.group(1);
        String step = matcher.group(2);

        return parseSimple(dep, step);
    }

}
