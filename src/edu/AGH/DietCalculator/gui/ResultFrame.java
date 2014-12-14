package edu.AGH.DietCalculator.gui;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import edu.AGH.DietCalculator.data.FoodData;
import edu.AGH.DietCalculator.diet.Diet;
import edu.AGH.DietCalculator.diet.Meal;

@SuppressWarnings("serial")
public class ResultFrame extends Frame
{
	final int columnCount = 8;
	
	Label bmiLabel;
	Label bmrLabel;
	Label caloriesLabel;
	JTable dietTable = null;
	JScrollPane scrollPane = null;
	String[] ids;
	
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
			
			JButton banButton = new JButton("Ban selected and recalculate");
			panel.add(banButton);
			banButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Recalculate();
					
				}
			});
			
			
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
	
	public void SetDiet(Diet diet)
	{
        int totalFoodItems = diet.meals.stream().mapToInt(meal -> meal.foods.size()).sum();
		ids = new String[diet.size()];
		
		Object[][] data = new Object[totalFoodItems][];
		
		Object[] labels = new Object[columnCount];
		labels[0] = "Name";
		labels[1] = "Mass";
		labels[2] = "Calories";
		labels[3] = "Carbohydrate";
		labels[4] = "Protein";
		labels[5] = "Fat";
		labels[6] = "Glicemic Wage";
		labels[7] = "Meal";
		
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
=======
		labels[6] = "Glycemic load";

        int row = 0;
        int mealNumber = 1;
        for (Meal meal : diet.meals) {
            for (FoodData food : meal.foods) {
                data[row] = new Object[columnCount];
                data[row][0] = food.getLabel();
                data[row][1] = food.getMass();
                data[row][2] = food.getCalories();
                data[row][3] = food.getCarbohydrate();
                data[row][4] = food.getProtein();
                data[row][5] = food.getFat();
                data[row][6] = food.getGlycemicIndex();
                data[row][7] = mealNumber;
                row++;
            }
	    ids[row] = meal.getId();
            mealNumber++;
        }
		
		if(scrollPane != null) remove(scrollPane);
		dietTable = new JTable(data, labels);
		scrollPane = new JScrollPane(dietTable);
		scrollPane.setBounds(10, 75, 580, 240);
		add(scrollPane);
	}
	
	void Recalculate()
	{
		int[] selected = dietTable.getSelectedRows();
		String[] selectedId = new String[selected.length];
		for(int a = 0; a < selected.length; ++a)
		{
			selectedId[a] = ids[selected[a]];
		}
		
		//Ban
		System.out.print("Ban: ");
		for(String id : selectedId)
		{
			System.out.print(id + ", ");
		}
		System.out.println();
	}
	
}
