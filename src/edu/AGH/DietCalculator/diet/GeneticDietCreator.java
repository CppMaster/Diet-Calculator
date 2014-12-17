package edu.AGH.DietCalculator.diet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.AGH.DietCalculator.data.FoodData;
import edu.AGH.DietCalculator.data.MealData;

/**
 * Created by krzysiek on 2014-12-11.
 */
public class GeneticDietCreator {
    private List<Diet> population;
    private List<Diet> champions = new ArrayList<>();

    private Random generator = new Random();
    private DietParameters dietParameters;
    private GeneticAlgorithmParameters geneticParameters;

    public GeneticDietCreator(DietParameters dietParameters, GeneticAlgorithmParameters geneticParameters) {
        this.dietParameters = dietParameters;
        this.geneticParameters = geneticParameters;
    }

    private Diet PossiblyMutate(Diet diet) {
        if (GetProbability() < geneticParameters.mutationChance) {
            return Mutate(diet);
        }
        return diet;
    }

    private Diet Mutate(Diet diet) {
        double random = GetProbability();
        if (random < 1.0/3) {
            return MutateSwapFood(diet);
        }
        if (random < 2.0/3) {
            return MutateMoreFood(diet);
        }
        return MutateLessFood(diet);
    }

    private Diet MutateSwapFood(Diet diet) {
        Meal meal = RandomElement(diet.meals);
        if (meal.foods.isEmpty())
            return diet;

        int oldFoodIndex = generator.nextInt(meal.foods.size());
        String foodType = meal.mealData.GetTypes().get(oldFoodIndex);
        FoodPortion newFood = RandomFood(foodType);
        meal.foods.set(oldFoodIndex, newFood);
        return diet;
    }

    private double GetProbability() {
        return generator.nextDouble();
    }

    private Diet MutateLessFood(Diet diet) {
        Meal meal = RandomElement(diet.meals);
        FoodPortion food = RandomElement(meal.foods);
        if (food.portion >= 0.5) {
            food.portion -= 0.5;
        }
        return diet;
    }

    private Diet MutateMoreFood(Diet diet) {
        Meal meal = RandomElement(diet.meals);
        FoodPortion food = RandomElement(meal.foods);
        food.portion += 0.5;
        return diet;
    }

    private Diet Crossover(Diet mother, Diet father) {
        Diet child = new Diet(dietParameters.meals);
        for (int i = 0; i < dietParameters.meals; i++) {
            double random = GetProbability();
            Diet parent = random < 0.5 ? mother : father;
            child.meals.add(parent.meals.get(i).Clone());
        }
        return child;
    }

    private double RateDiet(Diet diet) {
        return dietParameters.penalties.stream().mapToDouble(p -> p.run(diet)).sum();
    }

    public Diet GenerateDiet(int iterations) {
        population = GenerateRandomPopulation();
        champions.add(GetChampion(population));

        for (int i = 0; i < iterations; i++) {
            population = GenerateNextPopulation();
            champions.add(GetChampion(population));
        }

        return GetChampion(champions);
    }

    private Diet GetChampion(List<Diet> population) {
        Diet champion = population.get(0);
        for (int i = 1; i < population.size(); i++) {
            Diet contestant = population.get(i);
            if (RateDiet(champion) > RateDiet(contestant)) {
                champion = contestant;
            }
        }
        return champion;
    }

    public Diet RandomDiet(int meals) {
        Diet diet = new Diet(meals);
        for (int i = 0; i < meals; i++) {
            List<MealData> mealTemplates = dietParameters.database.GetMeals(i + 1);
            MealData mealTemplate = RandomElement(mealTemplates);
            diet.meals.add(RandomMeal(mealTemplate));
        }
        return diet;
    }

    private Meal RandomMeal(MealData mealTemplate) {
        Meal meal = new Meal(mealTemplate);
        for (String foodType : mealTemplate.GetTypes()) {
            meal.foods.add(RandomFood(foodType));
        }
        return meal;
    }

    private FoodPortion RandomFood(String foodType) {
        List<FoodData> foodsOfType = dietParameters.database.GetFood(foodType);
        FoodData foodData = RandomElement(foodsOfType);
        FoodPortion food = new FoodPortion(foodData);
        food.portion = 0.5f * (generator.nextInt(3) + 1);
        return food;
    }

    private List<Diet> GenerateRandomPopulation() {
        List<Diet> nextPopulation = new ArrayList<>(geneticParameters.populationSize);
        for (int i = 0; i < geneticParameters.populationSize; i++) {
            nextPopulation.add(RandomDiet(dietParameters.meals));
        }
        return nextPopulation;
    }

    private List<Diet> GenerateNextPopulation() {
        List<Diet> nextPopulation = new ArrayList<>(geneticParameters.populationSize);
        for (int i = 0; i < geneticParameters.populationSize; i++) {
            Diet mother = Tournament();
            Diet father = Tournament();
            Diet child = Crossover(mother, father);
            child = PossiblyMutate(child);
            nextPopulation.add(child);
        }
        return nextPopulation;
    }

    private Diet Tournament(int level)
    {
        if (level == 1) {
            return Tournament();
        }
        return Tournament(Tournament(level - 1), Tournament(level - 1));
    }

    private Diet Tournament() {
        Diet first = RandomElement(population);
        Diet second = RandomElement(population);
        return Tournament(first, second);
    }

    private Diet Tournament(Diet first, Diet second) {
        return RateDiet(first) < RateDiet(second) ? first : second;
    }

    private <T> T RandomElement (List<T> list) {
        return list.get(generator.nextInt(list.size()));
    }

    public double[] GetChampionRatings() {
        return champions.stream().mapToDouble(ch -> RateDiet(ch)).toArray();
    }
    
}
