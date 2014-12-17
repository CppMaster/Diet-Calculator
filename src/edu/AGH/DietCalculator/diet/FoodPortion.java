package edu.AGH.DietCalculator.diet;

import edu.AGH.DietCalculator.data.FoodData;

/**
 * Created by krzysiek on 2014-12-17.
 */
public class FoodPortion {
    public FoodData foodData;
    public float portion = 1.0f;

    public FoodPortion(FoodData foodData) {
        this.foodData = foodData;
    }

    public String getId() {
        return foodData.getId();
    }

    public String getLabel() {
        return foodData.getLabel();
    }

    public float getMass() {
        return portion * foodData.getMass();
    }
    public float getCalories() {
        return portion * foodData.getCalories();
    }
    public float getCarbohydrate() {
        return portion * foodData.getCarbohydrate();
    }
    public float getProtein() {
        return portion * foodData.getProtein();
    }
    public float getFat() {
        return portion * foodData.getFat();
    }
    public float getGlycemicIndex() {
        return portion * foodData.getGlycemicIndex();
    }

    public FoodPortion Clone() {
        FoodPortion clone = new FoodPortion(foodData);
        clone.portion = portion;
        return clone;
    }
}
