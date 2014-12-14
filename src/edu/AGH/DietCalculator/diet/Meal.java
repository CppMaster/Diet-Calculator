package edu.AGH.DietCalculator.diet;

import edu.AGH.DietCalculator.data.FoodData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krzysiek on 2014-12-11.
 */
public class Meal {
    public List<FoodData> foods = new ArrayList<>();

    private String name;

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
        return (float) foods.stream().mapToDouble(FoodData::getGlycemicIndex).sum();
    }

    public float getCalories() {
        return (float) foods.stream().mapToDouble(FoodData::getCalories).sum();
    }

    public Meal Clone() {
        Meal clone = new Meal();
        clone.name = name;
        for (FoodData food : foods) {
            clone.foods.add(food);
        }
        return clone;
    }

    public float getProteins() {
        return (float) foods.stream().mapToDouble(FoodData::getProtein).sum();
    }
    public float getCarbohydrates() {
        return (float) foods.stream().mapToDouble(FoodData::getCarbohydrate).sum();
    }
    public float getFats() {
        return (float) foods.stream().mapToDouble(FoodData::getFat).sum();
    }
}
