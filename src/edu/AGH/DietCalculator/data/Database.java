package edu.AGH.DietCalculator.data;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class Database {

	HashMap<String, FoodData> data;
	HashMap<String, List<FoodData>> dataByType;
	HashMap<Integer, List<MealData>> mealData;

	public boolean LoadData(String path) {
		data = new HashMap<String, FoodData>();
		dataByType = new HashMap<String, List<FoodData>>();
		mealData = new HashMap<Integer, List<MealData>>();
		
		try {

			XMLInputFactory inputFactory = XMLInputFactory.newInstance();

			InputStream in = new FileInputStream(path);
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					if (startElement.getName().getLocalPart().equals("item")) {
						FoodData item = new FoodData();
						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = startElement
								.getAttributes();

						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							if (attribute.getName().toString().equals("id")) {
								item.setId(attribute.getValue());
							}
							if (attribute.getName().toString().equals("label")) {
								item.setLabel(attribute.getValue());
							}
							if (attribute.getName().toString().equals("mass")) {
								item.setMass(Float.parseFloat(attribute
										.getValue()));
							}
							if (attribute.getName().toString()
									.equals("calories")) {
								item.setCalories(Float.parseFloat(attribute
										.getValue()));
							}
							if (attribute.getName().toString()
									.equals("carbohydrate")) {
								item.setCarbohydrate(Float.parseFloat(attribute
										.getValue()));
							}
							if (attribute.getName().toString()
									.equals("protein")) {
								item.setProtein(Float.parseFloat(attribute
										.getValue()));
							}
							if (attribute.getName().toString().equals("fat")) {
								item.setFat(Float.parseFloat(attribute
										.getValue()));
							}
							if (attribute.getName().toString()
									.equals("glycemicIndex")) {
								item.setGlycemicIndex(Float
										.parseFloat(attribute.getValue()));
							}
							if (attribute.getName().toString().equals("type")) {
								for (String type : attribute.getValue().split(
										",")) {
									item.AddType(type);

									if (!dataByType.containsKey(type)) {
										dataByType.put(type,
												new LinkedList<FoodData>());
									}
									dataByType.get(type).add(item);
								}
							}

						}

						data.put(item.getId(), item);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			XMLInputFactory inputFactory = XMLInputFactory.newInstance();

			InputStream in = new FileInputStream("meal_data.xml");
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

			MealData item = null;

			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					if (startElement.getName().getLocalPart().equals("meal")) {
						item = new MealData();
						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = startElement
								.getAttributes();

						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							if (attribute.getName().toString().equals("number")) {
								item.setNumber(Integer.parseInt(attribute
										.getValue()));
							}
						}
					}
					
					if (event.asStartElement().getName().getLocalPart().equals("food")) {
				            //event = eventReader.nextEvent();
				            @SuppressWarnings("unchecked")
							Iterator<Attribute> attributes = event.asStartElement().getAttributes();

							while (attributes.hasNext()) {
								Attribute attribute = attributes.next();
								if (attribute.getName().toString().equals("type")) {
									item.AddType(attribute.getValue());
								}
							}
				            continue;
				          }
				}
				
				

				if (event.isEndElement()) {
					EndElement endElement = event.asEndElement();
					if (endElement.getName().getLocalPart().equals("meal")) {
						
						if (!mealData.containsKey(item.getNumber())) {
							mealData.put(item.getNumber(), new LinkedList<MealData>());
						}
						mealData.get(item.getNumber()).add(item);
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public List<FoodData> GetRandomMeals(int count) {
		List<FoodData> list = new LinkedList<FoodData>();

		Random rnd = new Random();
		for (FoodData meal : data.values()) {
			if (rnd.nextFloat() < count * 1f / data.size())
				list.add(meal);
		}
		return list;
	}

	public List<FoodData> GetFoods() {
		return new ArrayList<>(data.values());
	}

	public List<FoodData> GetFood(String type) {
		return new ArrayList<>(dataByType.get(type));
	}
	
	public List<MealData> GetMeals(int number){
		return new ArrayList<>(mealData.get(number));
	}
}
