package edu.AGH.DietCalculator.diet;

import edu.AGH.DietCalculator.data.FoodData;

import java.util.List;

/**
 * Created by krzysiek on 2014-12-12.
 */
public class DietParameters {
    public DietParameters() {
    }

    public DietParameters(int targetCalories, int maxGlycemicLoad, int meals, List<FoodData> foods) {
        this.targetCalories = targetCalories;
        this.maxGlycemicLoad = maxGlycemicLoad;
        this.meals = meals;
        this.foods = foods;
    }

    public int targetCalories;
    public int maxGlycemicLoad;
    public int meals;
    public List<FoodData> foods;
}