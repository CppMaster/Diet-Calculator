package edu.AGH.DietCalculator.diet;

import edu.AGH.DietCalculator.data.FoodData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BinaryOperator;

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

    public float getProteins() {
        return (float) meals.stream().mapToDouble(Meal::getProteins).sum();
    }
    public float getCarbohydrates() {
        return (float) meals.stream().mapToDouble(Meal::getCarbohydrates).sum();
    }
    public float getFats() {
        return (float) meals.stream().mapToDouble(Meal::getFats).sum();
    }
    
    public float getMass()
    {
    	return (float) meals.stream().mapToDouble(Meal::getMass).sum();
    }

    public int CountRepeatingFoods()
    {
        HashMap<String, Integer> counts = new HashMap<>();
        for (Meal meal : meals){
            for (FoodPortion food : meal.foods) {
                int count = counts.getOrDefault(food.getLabel(), 0);
                counts.put(food.getLabel(), count + 1);
            }
        }

        int multiple = counts.values().stream().reduce(0, (acc, val) -> acc + val - 1);
        return multiple;
    }
}
