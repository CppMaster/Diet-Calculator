package edu.AGH.DietCalculator.gui;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import edu.AGH.DietCalculator.data.FoodData;

@SuppressWarnings("serial")
public class ResultFrame extends Frame
{
	final int columnCount = 7;
	
	Label bmiLabel;
	Label bmrLabel;
	Label caloriesLabel;
	JTable dietTable = null;
	JScrollPane scrollPane = null;
	
	public ResultFrame()
	{
		super("Result");
		setLayout(null);
		setSize(600, 300);
		setBackground(Color.lightGray);
		addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
		
		{
			Panel panel = new Panel();
			
			bmiLabel = new Label("BMI: 21.5");
			panel.add(bmiLabel);
			
			bmrLabel = new Label("BMR: 1652.5");
			panel.add(bmrLabel);
			
			caloriesLabel = new Label("Calories needed: 2272.2");
			panel.add(caloriesLabel);
			
			panel.setBounds(10, 30, 580, 50);
			add(panel);
		}
		
		setVisible(true);
	}
	
	public void SetNeeds(float _bmi, float _bmr, float _calories)
	{
		bmiLabel.setText("BMI: " + _bmi);
		bmrLabel.setText("BMR: " + _bmr);
		caloriesLabel.setText("Calories needed: " + _calories);
	}
	
	public void SetDiet(List<FoodData> diet)
	{
		Object[][] data = new Object[diet.size()][];
		
		Object[] labels = new Object[columnCount];
		labels[0] = "Name";
		labels[1] = "Mass";
		labels[2] = "Calories";
		labels[3] = "Carbohydrate";
		labels[4] = "Protein";
		labels[5] = "Fat";
		labels[6] = "Glicemic Wage";
		
		for(int a = 0; a < diet.size(); ++a)
		{
			data[a] = new Object[columnCount];
			FoodData currRow = diet.get(a);
			data[a][0] = currRow.getLabel();
			data[a][1] = currRow.getMass();
			data[a][2] = currRow.getCalories();
			data[a][3] = currRow.getCarbohydrate();
			data[a][4] = currRow.getProtein();
			data[a][5] = currRow.getFat();
			data[a][6] = currRow.getGlycemicIndex();
		}
		
		if(scrollPane != null) remove(scrollPane);
		dietTable = new JTable(data, labels);
		scrollPane = new JScrollPane(dietTable);
		scrollPane.setBounds(10, 75, 580, 240);
		add(scrollPane);
	}
	
}
