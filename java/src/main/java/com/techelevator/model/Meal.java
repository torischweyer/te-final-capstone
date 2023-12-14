package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

public class Meal {
    private int mealId;
    private String name;
    private List<Recipe> recipes = new ArrayList<>();

    public Meal () {

    }

    public Meal (String name) {
        this.name = name;

    }


    public void addToRecipes(Recipe recipe) {
        this.recipes.add(recipe);
    }


    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

}
