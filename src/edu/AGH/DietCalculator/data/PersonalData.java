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
		return isPregnant && getGender() == Gender.Female;
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

    public float CalculateCaloriesNeeded() {
        //applying the Harris-Benedict Principle
        float bmr = CalculateBaseMetabolicRate();
        float exerciseFactor = GetExerciseFactor(exercise);
        float pregnancyFactor = isPregnant() ? 1.1f : 1.0f;
        return bmr * exerciseFactor * pregnancyFactor;
    }
    
    public float CarbohydrateNeeded()
    {
    	float carbohydratePerKG = 3f;
    	if (exercise.equals("none")) {
    		carbohydratePerKG = 3f;
        } else if (exercise.equals("light")) {
        	carbohydratePerKG = 4f;
        } else if (exercise.equals("moderate")) {
        	carbohydratePerKG = 6f;
        } else if (exercise.equals("heavy")) {
        	carbohydratePerKG = 8f;
        } else if (exercise.equals("very heavy")) {
        	carbohydratePerKG = 10f;
        } else {
        	carbohydratePerKG = 3f;
        }
    	return carbohydratePerKG * weight;
    }
    
    public float FatNeeded()
    {
    	return CalculateCaloriesNeeded() * 0.25f / 9f;
    }
    
    public float ProteinNeeded()
    {	
    	float ageFactor = 1f;
    	if(isPregnant())
    		ageFactor = 1.1f;
    	else if(age < 1f)
    		ageFactor = 1.2f;
    	else if(age < 3f)
    		ageFactor = 1.05f;
    	else if(age < 13f)
    		ageFactor = 0.95f;
    	else if(age < 18f)
    		ageFactor = 0.85f;
    	else
    		ageFactor = 0.8f;
    	
    	return ageFactor * weight;
    }

    private float GetExerciseFactor(String exercise) {
        if (exercise.equals("none")) {
            return 1.2f;
        } else if (exercise.equals("light")) {
            return 1.375f;
        } else if (exercise.equals("moderate")) {
            return 1.55f;
        } else if (exercise.equals("heavy")) {
            return 1.725f;
        } else if (exercise.equals("very heavy")) {
            return 1.9f;
        } else {
            return 1.2f;
        }
    }
}
