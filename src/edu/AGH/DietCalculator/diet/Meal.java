package edu.AGH.DietCalculator.diet;

import edu.AGH.DietCalculator.data.FoodData;
import edu.AGH.DietCalculator.data.MealData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krzysiek on 2014-12-11.
 */
public class Meal {
    public MealData mealData;
    public List<FoodPortion> foods = new ArrayList<FoodPortion>();

    private String name;

    public Meal(MealData mealData) {
        this.mealData = mealData;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "foods=" + foods +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getGlycemicLoad() {
        return (float) foods.stream().mapToDouble(FoodPortion::getGlycemicIndex).sum();
    }

    public float getCalories() {
        return (float) foods.stream().mapToDouble(FoodPortion::getCalories).sum();
    }

    public Meal Clone() {
        Meal clone = new Meal(mealData);
        clone.name = name;
        for (FoodPortion food : foods) {
            clone.foods.add(food.Clone());
        }
        return clone;
    }

    public float getProteins() {
        return (float) foods.stream().mapToDouble(FoodPortion::getProtein).sum();
    }
    public float getCarbohydrates() {
        return (float) foods.stream().mapToDouble(FoodPortion::getCarbohydrate).sum();
    }
    public float getFats() {
        return (float) foods.stream().mapToDouble(FoodPortion::getFat).sum();
    }
}
