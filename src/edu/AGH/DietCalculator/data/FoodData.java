package edu.AGH.DietCalculator.data;

import java.util.LinkedList;
import java.util.List;

public class FoodData {

	String id;
	String label;
	float mass;
	float calories;
	float carbohydrate;
	float protein;
	float fat;
	float glycemicIndex;
    private String name;
    List<String> types;

    FoodData()
    {
    	types = new LinkedList<String>();
    }
    
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public float getMass() {
		return mass;
	}
	public void setMass(float mass) {
		this.mass = mass;
	}
	public float getCalories() {
		return calories;
	}
	public void setCalories(float calories) {
		this.calories = calories;
	}
	public float getCarbohydrate() {
		return carbohydrate;
	}
	public void setCarbohydrate(float carbohydrate) {
		this.carbohydrate = carbohydrate;
	}
	public float getProtein() {
		return protein;
	}
	public void setProtein(float protein) {
		this.protein = protein;
	}
	public float getFat() {
		return fat;
	}
	public void setFat(float fat) {
		this.fat = fat;
	}
	public float getGlycemicIndex() {
		return glycemicIndex;
	}
	public void setGlycemicIndex(float glycemicIndex) {
		this.glycemicIndex = glycemicIndex;
	}
	
	@Override
	public String toString() {
		return "FoodData [id=" + id + ", label=" + label + ", mass=" + mass
				+ ", calories=" + calories + ", carbohydrate=" + carbohydrate
				+ ", protein=" + protein + ", fat=" + fat + ", glycemicIndex="
				+ glycemicIndex + "]";
	}

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public void AddType(String type)
    {
    	types.add(type);
    }
    
    public List<String> GetTypes()
    {
    	return types;
    }
    
    public boolean IsOfType(String type)
    {
    	return types.contains(type);
    }
}
