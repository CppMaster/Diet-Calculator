package edu.AGH.DietCalculator.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

import edu.AGH.DietCalculator.data.Database;
import edu.AGH.DietCalculator.data.FoodData;
import edu.AGH.DietCalculator.data.MealData;
import edu.AGH.DietCalculator.data.PersonalData;
import edu.AGH.DietCalculator.diet.*;

public class MainGui 
{

	private Frame mainFrame;
	private Checkbox pregnancy;
	PersonalData data;
	Database database;
	ResultFrame resultFrame = null;
	Set<String> bans;
	
	public static void main(String[] args) {
		MainGui window = new MainGui();
		window.prepareGUI();
	}
	
	MainGui()
	{
		data = new PersonalData();
		database = new Database();
		database.LoadData("food_data.xml");
		
		bans = new HashSet<String>();
	}

	private void prepareGUI() {
		
		mainFrame = new Frame("Diet Calculator");
		mainFrame.setSize(400, 300);
		mainFrame.setLayout(new GridLayout(8, 0));
		mainFrame.setBackground(Color.lightGray);
		mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
		
		{
			Panel panel = new Panel();
			CheckboxGroup genderChoice = new CheckboxGroup();
			
			Checkbox maleOption = new Checkbox("Male", genderChoice, true);
			maleOption.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					data.setGender(PersonalData.Gender.Male);	
					pregnancy.setEnabled(false);
				}
			});
			panel.add(maleOption);
			
			Checkbox femaleOption = new Checkbox("Female", genderChoice, true);
			femaleOption.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					data.setGender(PersonalData.Gender.Female);
					pregnancy.setEnabled(true);
				}
			});
			panel.add(femaleOption);
			
			mainFrame.add(panel);
		}
		
		{
			Panel panel = new Panel();
			
			panel.add(new Label("Height [cm]"));
			
			TextField height = new TextField("180");
			panel.add(height);
			height.addTextListener(new TextListener() {
				
				@Override
				public void textValueChanged(TextEvent arg0) {
					data.setHeight(Float.parseFloat(((TextField)arg0.getSource()).getText()));
				}
			});
			
			mainFrame.add(panel);
		}
		
		{
			Panel panel = new Panel();
			
			panel.add(new Label("Weight [kg]"));
			
			TextField weight = new TextField("72");
			panel.add(weight);
			weight.addTextListener(new TextListener() {
				
				@Override
				public void textValueChanged(TextEvent arg0) {
					data.setWeight(Float.parseFloat(((TextField)arg0.getSource()).getText()));
				}
			});
			
			mainFrame.add(panel);
		}

        {
            Panel panel = new Panel();

            panel.add(new Label("Age [years]"));

            TextField age = new TextField("22");
            panel.add(age);
            age.addTextListener(new TextListener() {

                @Override
                public void textValueChanged(TextEvent arg0) {
                    data.setAge(Float.parseFloat(((TextField) arg0.getSource()).getText()));
                }
            });

            mainFrame.add(panel);
        }
		
		{
			Panel panel = new Panel();
			
			panel.add(new Label("Exercise"));
			
			final Choice exercise = new Choice();
            exercise.add("none");
            exercise.add("light");
            exercise.add("moderate");
            exercise.add("heavy");
            exercise.add("very heavy");
			panel.add(exercise);
			exercise.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    data.setExercise(exercise.getSelectedItem());
                }
            });
			
			mainFrame.add(panel);
		}
		
		{
			Panel panel = new Panel();
			
			panel.add(new Label("Diabetes [0-100]"));
			
			TextField diabetes = new TextField("50");
			panel.add(diabetes);
			diabetes.addTextListener(new TextListener() {
				
				@Override
				public void textValueChanged(TextEvent arg0) {
					data.setDiabetes(Float.parseFloat(((TextField)arg0.getSource()).getText()) / 100.f);
				}
			});
			
			mainFrame.add(panel);
		}
		
		{
			Panel panel = new Panel();
			
			pregnancy = new Checkbox("Pregnancy");
			pregnancy.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					data.setPregnant(((Checkbox)arg0.getSource()).getState());	
				}
			});
			panel.add(pregnancy);
			
			mainFrame.add(panel);
		}
		
		{
			Panel panel = new Panel();
			
			Button calculateButton = new Button("Calculate");
			calculateButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Calculate();
				}
			});
			panel.add(calculateButton);

			mainFrame.add(panel);
		}

        mainFrame.setVisible(true);

	}
	
	public void Calculate()
	{
        float bmi = data.CalculateBodyMassIndex();
        float bmr = data.CalculateBaseMetabolicRate();
        float caloriesNeeded = data.CalculateCaloriesNeeded();
		System.out.println("Calculate:" + data);
        System.out.println("BMI: " + bmi);
        System.out.println("BMR: " + bmr);
        System.out.println("Calories needed: " + caloriesNeeded);
        
        //Algorithm

        float targetProteins = data.ProteinNeeded();
        float targetCarbohydrates = data.CarbohydrateNeeded();
        float targetFats = data.FatNeeded();

        List<DietPenalty> penalties = GetDietPenalties(caloriesNeeded, targetProteins, targetCarbohydrates, targetFats);
        DietParameters dietParameters = new DietParameters((int)caloriesNeeded, 5, database, penalties);

        double mutationRate = 0.05;
        int populationSize = 500;
        int iterations = 70;
        GeneticAlgorithmParameters geneticAlgorithmParameters = new GeneticAlgorithmParameters(mutationRate, populationSize);
        GeneticDietCreator geneticDietCreator = new GeneticDietCreator(dietParameters, geneticAlgorithmParameters);
        for(String ban : bans)
        {
        	geneticDietCreator.AddBan(ban);
        }
        Diet diet = geneticDietCreator.GenerateDiet(iterations);
        System.out.println(Arrays.toString(geneticDietCreator.GetChampionRatings()));

        if(resultFrame == null) resultFrame = new ResultFrame();
        resultFrame.mainGui = this;
        resultFrame.SetNeeds(bmi, bmr, caloriesNeeded);
        resultFrame.SetNutritions(targetCarbohydrates, targetProteins, targetFats);
        resultFrame.SetDiet(diet);
   
    }

    private List<DietPenalty> GetDietPenalties(float targetCalories, float targetProteins, float targetCarbohydrates, float targetFats) {
        float caloriesWeight = 1.0f;
        float glycemicLoadWeight = 0.01f * data.getDiabetes(); //NOTICE: glycemic load penalty is QUADRATIC and the rest is LINEAR, so it's weight doesn't scale the same!
        float proteinsWeight = 0.2f;
        float carbohydratesWeight = 0.3f;
        float fatsWeight = 0.2f;
        float repeatingFoodsWeight = 20;

      List<DietPenalty> penalties = new ArrayList<DietPenalty>();
        penalties.add(diet -> caloriesWeight * Math.abs(targetCalories - diet.getCalories()));
        penalties.add(diet -> glycemicLoadWeight * diet.meals.stream().mapToDouble(meal -> Math.pow(meal.getGlycemicLoad(), 2)).sum());
        penalties.add(diet -> proteinsWeight * Math.abs(targetProteins - diet.getProteins()));
        penalties.add(diet -> carbohydratesWeight * Math.abs(targetCarbohydrates - diet.getCarbohydrates()));
        penalties.add(diet -> fatsWeight * Math.abs(targetFats - diet.getFats()));
        penalties.add(diet -> repeatingFoodsWeight * diet.CountRepeatingFoods());
      return penalties;
    }
    
    public void Ban(String id)
    {
    	bans.add(id);
    }

}
