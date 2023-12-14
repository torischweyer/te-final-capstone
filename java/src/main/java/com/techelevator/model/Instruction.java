package com.techelevator.model;

import java.util.List;

public class Instruction {
    private List<Step> steps;

    public Instruction() {};

    public Instruction(List<Step> steps) {
        this.steps = steps;
    }

    public void addStep(Step step) {
        steps.add(step);
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
}
