package edu.AGH.DietCalculator.diet;

import edu.AGH.DietCalculator.data.Database;
import edu.AGH.DietCalculator.data.FoodData;

import java.util.List;

/**
 * Created by krzysiek on 2014-12-12.
 */
public class DietParameters {
    public Database database;

    public DietParameters() {
    }

    public DietParameters(int targetCalories, int meals, Database database, List<DietPenalty> penalties) {
        this.targetCalories = targetCalories;
        this.meals = meals;
        this.database = database;
        this.penalties = penalties;
    }

    public int targetCalories;
    public int meals;
    public List<FoodData> foods;
    public List<DietPenalty> penalties;
}
