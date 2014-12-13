package edu.AGH.DietCalculator.diet;

import edu.AGH.DietCalculator.data.FoodData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krzysiek on 2014-12-11.
 */
public class Diet {
    public List<Meal> meals;
    private int mealsNumber;

    public Diet(int mealsNumber) {
        this.mealsNumber = mealsNumber;
        meals = new ArrayList<>(mealsNumber);
    }

    public float getCalories() {
        return (float) meals.stream().mapToDouble(Meal::getCalories).sum();
    }

    @Override
    public String toString() {
        return "Diet{" +
                "calories= " + getCalories() + "\n" +
                "meals=" + meals +
                '}';
    }
}
