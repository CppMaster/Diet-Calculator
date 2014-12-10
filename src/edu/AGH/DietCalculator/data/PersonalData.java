package edu.AGH.DietCalculator.data;


public class PersonalData {

    private float age;
    private String exercise = "light";

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getExercise() {
        return exercise;
    }


    public enum Gender
	{
		Male,
		Female
	};
	
	Gender gender = Gender.Female;
	float height = 180.f;
	float weight = 70.f;
	float diabetes = 0.5f;
	boolean isPregnant = false;
	
	public boolean isPregnant() {
		return isPregnant;
	}
	public void setPregnant(boolean isPregnant) {
		this.isPregnant = isPregnant;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public float getDiabetes() {
		return diabetes;
	}
	public void setDiabetes(float diabetes) {
		this.diabetes = diabetes;
	}
    public float getAge() { return age; }
    public void setAge(float age) { this.age = age; }

	@Override
	public String toString() {
		return "PersonalData [gender=" + gender + ", height=" + height + ", age=" + age
				+ ", weight=" + weight + ", exercise=" + exercise
				+ ", diabetes=" + diabetes + ", isPregnant=" + isPregnant + "]";
	}
	
	public static float CalculateBaseMetabolicRate(PersonalData.Gender gender, float weight, float height, float age) {
        //based on The Revised Harris-Benedict Equation:
        if (gender == PersonalData.Gender.Male){
            return 13.397f * weight + 4.799f * height + 5.667f * age + 88.362f;
        } else {
            return 9.247f * weight + 3.098f * height + 4.330f * age + 447.593f;
        }
	}
	
	public float CalculateBaseMetabolicRate()
	{
		return CalculateBaseMetabolicRate(gender, weight, height, age);
	}

    public static float CalculateBodyMassIndex(float weight, float height) {
        float height_meters = height / 100.0f;
        return weight / (height_meters * height_meters);
    }
    
    public float CalculateBodyMassIndex()
    {
    	return CalculateBodyMassIndex(weight, height);
    }
	
}
