package com.techelevator.controller;

import com.techelevator.dao.RecipeDao;
import com.techelevator.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
//@PreAuthorize("isAuthenticated()")
public class RecipeController {

    @Autowired
    private RecipeDao dao;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/recipes", method = RequestMethod.GET)
    public List<Recipe> retrieveListOfSavedRecipes(Principal principal) {
        List<Recipe> recipes = dao.retrieveAllSavedRecipes(principal.getName());

        if (recipes.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No recipes were found");
        }

        return recipes;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/recipes", method = RequestMethod.POST)
    public Recipe saveRecipe(Principal principal, @RequestBody Recipe recipe) {
        if (recipe == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Recipe cannot be null");
        }
        Recipe returnedRecipe = dao.saveRecipe(principal.getName(), recipe);
        if (returnedRecipe.getRecipeId() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error saving recipe");
        }
        return returnedRecipe;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/recipes/{recipeId}", method = RequestMethod.DELETE)
    public boolean removeRecipeFromLibrary(Principal principal, @PathVariable int recipeId) {
        boolean recipeRemoved = false;
        recipeRemoved = dao.removeRecipeFromLibrary(principal.getName(), recipeId);
        return recipeRemoved;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/recipes/{recipeId}", method = RequestMethod.GET)
    public Recipe getRecipeById(@PathVariable int recipeId) {
        if (recipeId == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Recipe cannot be null");
        }
        Recipe recipe = new Recipe();
        // TODO: Implement this method
        recipe = dao.getRecipeById(recipeId);
        if (recipe.getRecipeId() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error getting recipe");
        }
    	return recipe;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "", method = RequestMethod.PUT)
    public Recipe favoriteRecipe(Principal principal) {
        // TODO: Implement this method, decide on path, and decide on request body
        return null;
    }


}
