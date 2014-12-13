package edu.AGH.DietCalculator.diet;

/**
 * Created by krzysiek on 2014-12-12.
 */
public class GeneticAlgorithmParameters {
    public int populationSize;
    public double mutationChance;

    public GeneticAlgorithmParameters(double mutationChance, int populationSize) {
        this.mutationChance = mutationChance;
        this.populationSize = populationSize;
    }

    public GeneticAlgorithmParameters() {

    }
}
