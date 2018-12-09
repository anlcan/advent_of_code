package com.advent.seven;

public final class Worker {
    public boolean available = true;
    private Step currentStep;
    private final String name;

    public Worker(String name) {
        this.name = name;
    }

    public void assign(final Step name){
        System.out.println(this.name + " assigned to " + name.name);
        this.available = false;
        this.currentStep = name;
    }

    public void done(){
        this.available = true;
        this.currentStep = null;
    }

    public boolean tick() {
        if (currentStep.work()) {
            done();
        }
        return available;
    }

    public boolean available() {
        return available;
    }

    public boolean occupied() {
        return !available();
    }

    @Override
    public String toString() {
        return available? "." : currentStep.name;
    }
}
