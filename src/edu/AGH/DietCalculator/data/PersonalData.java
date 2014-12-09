package edu.AGH.DietCalculator.data;


public class PersonalData {

	public enum Gender
	{
		Male,
		Female
	};
	
	Gender gender = Gender.Female;
	float height = 180.f;
	float weight = 70.f;
	float activity = 0.5f;
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
	public float getActivity() {
		return activity;
	}
	public void setActivity(float activity) {
		this.activity = activity;
	}
	public float getDiabetes() {
		return diabetes;
	}
	public void setDiabetes(float diabetes) {
		this.diabetes = diabetes;
	}
	
	@Override
	public String toString() {
		return "PersonalData [gender=" + gender + ", height=" + height
				+ ", weight=" + weight + ", activity=" + activity
				+ ", diabetes=" + diabetes + ", isPregnant=" + isPregnant + "]";
	}
	
}
