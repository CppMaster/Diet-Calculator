package edu.AGH.DietCalculator.data;

import java.util.LinkedList;
import java.util.List;

public class MealData {

	int number;
	List<String> types;
	
	MealData()
    {
    	types = new LinkedList<String>();
    }
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	

	public void AddType(String type)
    {
    	types.add(type);
    }

	public List<String> GetTypes()
    {
    	return types;
    }
    
    public boolean HasType(String type)
    {
    	return types.contains(type);
    }
	
    @Override
    public String toString() {
    	return "MealData [number=" + number + ", types=" + types + "]";
    }
}
