package edu.AGH.DietCalculator.gui;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
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
		mainFrame.setVisible(true);
		
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
			
			panel.add(new Label("Activity [0-100]"));
			
			TextField activity = new TextField("50");
			panel.add(activity);
			activity.addTextListener(new TextListener() {
				
				@Override
				public void textValueChanged(TextEvent arg0) {
					data.setActivity(Float.parseFloat(((TextField)arg0.getSource()).getText()) / 100.f);
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
	}
	
	public void Calculate()
	{
		System.out.println("Calculate:" + data);
	}
}
