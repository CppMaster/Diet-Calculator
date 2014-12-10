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
	
}
