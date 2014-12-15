package edu.AGH.DietCalculator.diet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import edu.AGH.DietCalculator.data.FoodData;

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
            return MutateRemoveFood(diet);
        }
        return MutateAddFood(diet);
    }

    private Diet MutateSwapFood(Diet diet) {
        Meal meal = RandomElement(diet.meals);
        if (meal.foods.isEmpty())
            return diet;

        FoodData oldFood = RandomElement(meal.foods);
        FoodData newFood = RandomElement(dietParameters.foods);
        meal.foods.remove(oldFood);
        meal.foods.add(newFood);
        return diet;
    }

    private double GetProbability() {
        return generator.nextDouble();
    }

    private Diet MutateAddFood(Diet diet) {
        Meal meal = RandomElement(diet.meals);
        FoodData food = RandomElement(dietParameters.foods);
        if (!meal.foods.contains(food)) {
            meal.foods.add(food);
        }
        return diet;
    }

    private Diet MutateRemoveFood(Diet diet) {
        Meal meal = RandomElement(diet.meals);
        if (!meal.foods.isEmpty()) {
            meal.foods.remove(RandomElement(meal.foods));
        }
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

    private Diet RandomDiet(int meals) {
        Diet diet = new Diet(meals);
        for (int i = 0; i < meals; i++) {
            int foods = generator.nextInt(3) + 1;
            diet.meals.add(RandomMeal(foods));
        }
        return diet;
    }

    private Meal RandomMeal(int foods) {
        Meal meal = new Meal();
        for (int i = 0; i < foods; i++) {
            meal.foods.add(RandomElement(dietParameters.foods));
        }
        return meal;
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
    
    public void AddBan(String id)
    {
    	for(int a = 0; a < dietParameters.foods.size(); ++a)
    	{
    		if(dietParameters.foods.get(a).getId().equals(id))
    		{
    			dietParameters.foods.remove(a);
    			return;
    		}
    	}
    }
    
}
