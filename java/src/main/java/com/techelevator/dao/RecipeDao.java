package com.techelevator.dao;

import com.techelevator.model.MealPlan;
import com.techelevator.model.Recipe;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface RecipeDao {

    //recipe methods
    List<Recipe> retrieveAllSavedRecipes(String username);
    Recipe saveRecipe( String username, Recipe recipe);

    Recipe getRecipeById(int recipeId);

    Recipe favoriteRecipe(String username, Recipe recipe);
    boolean removeRecipeFromLibrary(String username, int recipeId);

    //meal plan methods
    MealPlan retrieveMealPlanByDate(String username, LocalDate date);

    boolean addRecipeToMealPlan(String username, MealPlan mealPlan);

    MealPlan createMealPlanByDate(String username, LocalDate date);

    boolean removeRecipeFromMealPlan(String username, int mealPlanId, int mealId, int recipeId);

}
