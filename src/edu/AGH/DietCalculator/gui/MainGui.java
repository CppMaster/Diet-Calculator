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

import edu.AGH.DietCalculator.data.Database;
import edu.AGH.DietCalculator.data.PersonalData;

public class MainGui 
{

	private Frame mainFrame;
	private Checkbox pregnancy;
	PersonalData data;
	Database database;
	
	public static void main(String[] args) {
		MainGui window = new MainGui();
		window.prepareGUI();
	}
	
	MainGui()
	{
		data = new PersonalData();
		database = new Database();
		database.LoadData("food_data.xml");
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

            TextField age = new TextField("72");
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
        float caloriesNeeded = CalculateCaloriesNeeded(bmr, data.getExercise()); //applying the Harris-Benedict Principle
		System.out.println("Calculate:" + data);
        System.out.println("BMI: " + bmi);
        System.out.println("BMR: " + bmr);
        System.out.println("Calories needed: " + caloriesNeeded);
	}

    private float CalculateCaloriesNeeded(float bmr, String exercise) {
        float exerciseFactor = GetExerciseFactor(exercise);
        return bmr * exerciseFactor;
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
