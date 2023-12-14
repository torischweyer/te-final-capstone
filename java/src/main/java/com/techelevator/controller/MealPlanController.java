package com.techelevator.controller;

import com.techelevator.dao.RecipeDao;
import com.techelevator.model.Meal;
import com.techelevator.model.MealPlan;
import com.techelevator.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Date;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class MealPlanController {

    @Autowired
    private RecipeDao dao;

    @RequestMapping(path = "/mealplans", method = RequestMethod.GET)
    public MealPlan retrieveMealPlanByDate(Principal principal, @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        MealPlan mealPlan = new MealPlan();
        mealPlan = dao.retrieveMealPlanByDate(principal.getName(), date);
        if (mealPlan == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Meal Plan Not Found");
        }
        return mealPlan;

    }

    @RequestMapping(path = "/mealplans", method = RequestMethod.POST)
    public MealPlan addRecipeToMealPlan(Principal principal, @RequestBody MealPlan mealPlan) {
        System.out.println(mealPlan);
        MealPlan mealPlanToUpdate = dao.retrieveMealPlanByDate(principal.getName(), mealPlan.getDate());
        if (mealPlanToUpdate.getMealPlanId() == 0) {
            // if meal plan does not exist, create meal plan
            mealPlanToUpdate = dao.createMealPlanByDate(principal.getName(), mealPlan.getDate());
        }
        if (mealPlanToUpdate.getMealPlanId() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating Meal Plan");
        }
        // Set meal plan id from database meal plan
        mealPlan.setMealPlanId(mealPlanToUpdate.getMealPlanId());
        // Add recipe to meal plan
        boolean recipeAdded = dao.addRecipeToMealPlan(principal.getName(), mealPlan);
        if (recipeAdded == false) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Recipe could not be added to meal plan");
        }
        mealPlanToUpdate = dao.retrieveMealPlanByDate(principal.getName(), mealPlan.getDate());
        return mealPlanToUpdate;
    }

    @RequestMapping(path = "/mealplans/{mealPlanId}/meals/{mealId}/recipes/{recipeId}", method = RequestMethod.DELETE)
    public boolean removeRecipeFromMealPlan(Principal principal, @PathVariable int mealPlanId, @PathVariable int mealId, @PathVariable int recipeId) {
        boolean recipeWasRemoved = false;
        recipeWasRemoved = dao.removeRecipeFromMealPlan(principal.getName(), mealPlanId, mealId, recipeId);
    	return recipeWasRemoved;
    }
}
