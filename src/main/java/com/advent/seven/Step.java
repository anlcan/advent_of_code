package com.advent.seven;

import java.util.*;
import java.util.stream.Collectors;

public class Step implements Comparable {

    public static int TIME = 0;
    public static final List<Worker> WORKERS = new ArrayList<>();

    private final List<Step> dependencies = new ArrayList<>();
    private final List<Step> dependees = new ArrayList<>();

    private boolean done = false;

    private int duration;

    public final String name;

    public Step(String name) {
        this.name = name;
        if (!name.equals(""))
            this.duration = totalDuration();
        else
            this.duration = 0;
    }

    public int totalDuration() {
        return TIME + name.charAt(0) - 64;
    }

    public String traverse() {

        String result = "";
        while (true) {

            List<Step> steps = availableSteps();

            if (steps.size() == 0) {
                break;
            } else {
                Step step = steps.get(0);
                System.out.println("traversing " + step);

                step.done = true;
                result = result.concat(step.name);

            }
        }

        return result;
    }

    public int time() {

        int second = 0;
        while (true) {

            List<Step> steps = availableSteps();

            if (steps.size() == 0) {
                break;
            } else {

                System.out.println("second " + second);
                List<Worker> availableWorkers = WORKERS.stream()
                        .filter(Worker::available)
                        .collect(Collectors.toList());

                List<Step> availableWork = availableSteps().stream()
                        .filter(s -> !s.isInProgress())
                        .collect(Collectors.toList());

                for (int i = 0; i < Math.min(availableWorkers.size(), availableWork.size()); i++) {
                    availableWorkers.get(i).assign(availableWork.get(i));
                }

                WORKERS.stream()
                        .filter(Worker::occupied)
                        .forEach(Worker::tick);

                second++;
            }
        }

        return second;
    }

    public List<Step> availableSteps() {

        if (isNotDone() &&  available()) {
            return Collections.singletonList(this);
        } else {
            return dependees.stream()
                    .filter(Step::available)
                    .flatMap(step -> step.availableSteps().stream())
                    .sorted()
                    .distinct()
                    .collect(Collectors.toList());
        }

    }

    // all dependencies are done
    public boolean available() {
        return dependencies.stream()
                .map(Step::isDone)
                .reduce(true, Boolean::logicalAnd);
    }

    public boolean isInProgress() {
         return isNotDone() && duration < totalDuration();
    }

    public boolean work() {

        if (--duration == 0) {
            done = true;
        }
        return done;
    }


    public void makeDo() {
        assert (available());
        done = true;
    }

    public boolean isDone() {
        return done;
    }

    public boolean isNotDone() {
        return !done;
    }

    public void addDependency(final Step s) {
        dependencies.add(s);
        s.addDependee(this);
    }

    public void addDependee(Step step) {
        this.dependees.add(step);
    }


    public List<Step> getDependencies() {
        return dependencies;
    }

    public List<Step> getDependees() {
        return dependees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Step step = (Step) o;
        return Objects.equals(name, step.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Step::" + name;
    }

    @Override
    public int compareTo(Object o) {
        return this.getName().compareTo(((Step) o).getName());
    }

}
