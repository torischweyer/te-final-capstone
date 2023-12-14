package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JdbcRecipeDao implements RecipeDao{

    private final JdbcTemplate jdbcTemplate;
    private int alreadySavedRecipeId = 0;

    public JdbcRecipeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Meal Plan methods
    @Override
    public MealPlan retrieveMealPlanByDate(String username, LocalDate date) {
        MealPlan mealPlan = new MealPlan();
        String mealPlanSql = "SELECT plan_id, date_of_plan, user_id, breakfast_id, lunch_id, dinner_id, snack_id, fun_id \n" +
                "FROM plans\n" +
                "WHERE user_id = ? AND date_of_plan = ?;";
        try {
            int user_id = getUserIdByUsername(username);
            SqlRowSet result = jdbcTemplate.queryForRowSet(mealPlanSql, user_id, date);
            if (result.next()) {
                mealPlan = buildMealPlan(result);
            }
        } catch (Exception e) {
            throw new DaoException("Error retrieving meal plan.", e);
        }
        return mealPlan;
    }
    private Meal getMealById(int mealId) {
        Meal meal = new Meal();
        String mealSql = "SELECT meal_id, meal_name \n" +
                "FROM meals\n" +
                "WHERE meal_id = ?;";
        String recipeSql = "SELECT r.recipe_id, spoonacular_id, recipe_title, image_url, favorite, servings, ready_in_minutes\n" +
                "FROM recipes AS r\n" +
                "JOIN meals_recipes AS mr ON r.recipe_id = mr.recipe_id\n" +
                "WHERE mr.meal_id = ?;";
        try {
            SqlRowSet mealResult = jdbcTemplate.queryForRowSet(mealSql, mealId);
            if (mealResult.next()) {
                meal.setMealId(mealResult.getInt("meal_id"));
                meal.setName(mealResult.getString("meal_name"));
            }
            SqlRowSet recipeResults = jdbcTemplate.queryForRowSet(recipeSql, mealId);
            while (recipeResults.next()) {
                Recipe recipe = mapRowToRecipe(recipeResults);
                recipe = attachIngredientsToRecipe(recipe);
                recipe = attachInstructionsToRecipe(recipe);
                recipe = attachDietsToRecipe(recipe);
                meal.addToRecipes(recipe);
            }
        } catch (Exception e) {
            throw new DaoException("Error retrieving meal.", e);
        }
        return meal;
    }
    @Override
    public boolean addRecipeToMealPlan(String username, MealPlan mealPlan) {
        int rowsAffected = 0;
        // check if meal exists in meal table
        String sql = "INSERT INTO meals_recipes (meal_id, recipe_id)\n" +
                "VALUES (?, ?);";
        try {
            int userId = getUserIdByUsername(username);
            if (mealPlan.getBreakfast().getRecipes().size() > 0) {
                String breakfastQuerySql = "SELECT breakfast_id FROM plans WHERE plan_id = ?;";
                int breakfastId = 0;
                SqlRowSet result = jdbcTemplate.queryForRowSet(breakfastQuerySql, mealPlan.getMealPlanId());
                if (result.next()){
                    breakfastId = result.getInt("breakfast_id");
                }
                if(breakfastId == 0) {
                    String makeMealSql = "INSERT INTO meals (meal_name, user_id)\n" +
                            "VALUES ('Breakfast', ?) RETURNING meal_id;";
                    breakfastId = jdbcTemplate.queryForObject(makeMealSql, Integer.class, userId);
                    String addMealToPlanSql = "UPDATE plans SET breakfast_id = ? WHERE plan_id = ?;";
                    jdbcTemplate.update(addMealToPlanSql, breakfastId, mealPlan.getMealPlanId());
                }
                for (Recipe recipe : mealPlan.getBreakfast().getRecipes()) {
                    rowsAffected += jdbcTemplate.update(sql, breakfastId, recipe.getRecipeId());
                }
            }
            if (mealPlan.getLunch().getRecipes().size() > 0) {
                String lunchQuerySQl = "SELECT lunch_id FROM plans WHERE plan_id = ?;";
                int lunchId = 0;
                SqlRowSet result = jdbcTemplate.queryForRowSet(lunchQuerySQl, mealPlan.getMealPlanId());
                if (result.next()){
                    lunchId = result.getInt("lunch_id");
                }
                if(lunchId == 0) {
                    String makeMealSql = "INSERT INTO meals (meal_name, user_id)\n" +
                            "VALUES ('Lunch', ?) RETURNING meal_id;";
                    lunchId = jdbcTemplate.queryForObject(makeMealSql, Integer.class, userId);
                    String addMealToPlanSql = "UPDATE plans SET lunch_id = ? WHERE plan_id = ?;";
                    jdbcTemplate.update(addMealToPlanSql, lunchId, mealPlan.getMealPlanId());
                }
                for (Recipe recipe : mealPlan.getLunch().getRecipes()) {
                    rowsAffected += jdbcTemplate.update(sql, lunchId, recipe.getRecipeId());
                }
            }
            if (mealPlan.getDinner().getRecipes().size() > 0) {
                String dinnerQuerySQl = "SELECT dinner_id FROM plans WHERE plan_id = ?;";
                int dinnerId = 0;
                SqlRowSet result = jdbcTemplate.queryForRowSet(dinnerQuerySQl, mealPlan.getMealPlanId());
                if (result.next()){
                    dinnerId = result.getInt("dinner_id");
                }
                if(dinnerId == 0) {
                    String makeMealSql = "INSERT INTO meals (meal_name, user_id)\n" +
                            "VALUES ('Dinner', ?) RETURNING meal_id;";
                    dinnerId = jdbcTemplate.queryForObject(makeMealSql, Integer.class, userId);
                    String addMealToPlanSql = "UPDATE plans SET dinner_id = ? WHERE plan_id = ?;";
                    jdbcTemplate.update(addMealToPlanSql, dinnerId, mealPlan.getMealPlanId());
                }
                for (Recipe recipe : mealPlan.getDinner().getRecipes()) {
                    rowsAffected += jdbcTemplate.update(sql, dinnerId, recipe.getRecipeId());
                }
            }
            if (mealPlan.getSnack().getRecipes().size() > 0) {
                String snackQuerySQl = "SELECT snack_id FROM plans WHERE plan_id = ?;";
                int snackId = 0;
                SqlRowSet result = jdbcTemplate.queryForRowSet(snackQuerySQl, mealPlan.getMealPlanId());
                if (result.next()){
                    snackId = result.getInt("snack_id");
                }
                if(snackId == 0) {
                    String makeMealSql = "INSERT INTO meals (meal_name, user_id)\n" +
                            "VALUES ('Snack', ?) RETURNING meal_id;";
                    snackId = jdbcTemplate.queryForObject(makeMealSql, Integer.class, userId);
                    String addMealToPlanSql = "UPDATE plans SET snack_id = ? WHERE plan_id = ?;";
                    jdbcTemplate.update(addMealToPlanSql, snackId, mealPlan.getMealPlanId());
                }
                for (Recipe recipe : mealPlan.getSnack().getRecipes()) {
                    rowsAffected += jdbcTemplate.update(sql, snackId, recipe.getRecipeId());
                }
            }
            if (mealPlan.getFun().getRecipes().size() > 0) {
                String funQuerySQl = "SELECT fun_id FROM plans WHERE plan_id = ?;";
                int funId = 0;
                SqlRowSet result = jdbcTemplate.queryForRowSet(funQuerySQl, mealPlan.getMealPlanId());
                if (result.next()){
                    funId = result.getInt("fun_id");
                }
                if(funId == 0) {
                    String makeMealSql = "INSERT INTO meals (meal_name, user_id)\n" +
                            "VALUES ('Fun', ?) RETURNING meal_id;";
                    funId = jdbcTemplate.queryForObject(makeMealSql, Integer.class, userId);
                    String addMealToPlanSql = "UPDATE plans SET fun_id = ? WHERE plan_id = ?;";
                    jdbcTemplate.update(addMealToPlanSql, funId, mealPlan.getMealPlanId());
                }
                for (Recipe recipe : mealPlan.getFun().getRecipes()) {
                    rowsAffected += jdbcTemplate.update(sql, funId, recipe.getRecipeId());
                }
            }
            if (rowsAffected == 0) {
                return false;
            }

        } catch (Exception e) {
            throw new DaoException("Error adding recipe to meal plan.", e);
        }
        return true;
    }
    private MealPlan buildMealPlan(SqlRowSet result) {
        MealPlan mealPlan = new MealPlan();
        if(result.getInt("plan_id") != 0) {
            mealPlan.setMealPlanId(result.getInt("plan_id"));
        }
        if(result.getDate("date_of_plan") != null) {
            mealPlan.setDate(result.getDate("date_of_plan").toLocalDate());
        }
        if(result.getInt("breakfast_id") != 0) {
            int mealId = result.getInt("breakfast_id");
            Meal breakfast = getMealById(mealId);
            mealPlan.setBreakfast(breakfast);
        }
        if(result.getInt("lunch_id") != 0) {
            Meal lunch = getMealById(result.getInt("lunch_id"));
            mealPlan.setLunch(lunch);
        }
        if(result.getInt("dinner_id") != 0) {
            Meal dinner = getMealById(result.getInt("dinner_id"));
            mealPlan.setDinner(dinner);
        }
        if(result.getInt("snack_id") != 0) {
            Meal snack = getMealById(result.getInt("snack_id"));
            mealPlan.setSnack(snack);
        }
        if(result.getInt("fun_id") != 0) {
            Meal fun = getMealById(result.getInt("fun_id"));
            mealPlan.setFun(fun);
        }

        return mealPlan;
    }
    @Override
    public MealPlan createMealPlanByDate(String username, LocalDate date) {
        MealPlan mealPlan = new MealPlan();
        String mealPlanSql = "INSERT INTO plans (date_of_plan, user_id)\n" +
                "VALUES (?, ?) RETURNING plan_id;";
        try {
            int user_id = getUserIdByUsername(username);
            int plan_id = jdbcTemplate.queryForObject(mealPlanSql, Integer.class, date, user_id);
            if (plan_id == 0) {
                throw new DaoException("Error creating meal plan.");
            }
            mealPlan.setMealPlanId(plan_id);
            mealPlan.setDate(date);
        } catch (Exception e) {
            throw new DaoException("Error creating meal plan.", e);
        }
        return mealPlan;
    }
    @Override
    public boolean removeRecipeFromMealPlan(String username, int mealPlanId, int mealId, int recipeId) {
        boolean recipeWasRemoved = false;
        String sql = "DELETE FROM meals_recipes\n" +
                "WHERE meal_id = ? AND recipe_id = ?;";
        try {
            int rowsAffected = jdbcTemplate.update(sql, mealId, recipeId);
            if (rowsAffected == 1) {
                recipeWasRemoved = true;
            }
        } catch (Exception e) {
            throw new DaoException("Error removing recipe from meal plan.", e);
        }
        return recipeWasRemoved;
    }

    //recipe methods
    @Override
    public List<Recipe> retrieveAllSavedRecipes(String username) {
        List<Recipe> recipes = new ArrayList<>();
        String sql = "SELECT r.recipe_id, spoonacular_id, recipe_title, image_url, " +
                "favorite, servings, ready_in_minutes, source_name, source_url\n" +
                "FROM recipes AS r\n" +
                "JOIN recipes_users AS ru ON r.recipe_id = ru.recipe_id\n" +
                "WHERE ru.user_id = ? " +
                "ORDER BY r.recipe_id;";
        try {
            // get recipes (from recipes table)
            int userId = getUserIdByUsername(username);
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                Recipe recipe = mapRowToRecipe(results);
                recipe = attachIngredientsToRecipe(recipe);
                recipe = attachInstructionsToRecipe(recipe);
                recipe = attachDietsToRecipe(recipe);
                recipes.add(recipe);
            }
        } catch (Exception e) {
            throw new DaoException("Error retrieving recipes", e);
        }
        return recipes;
    }
    @Override
    public Recipe getRecipeById(int recipeId){
        Recipe recipe = new Recipe();
        String sql = "SELECT recipe_id, spoonacular_id, recipe_title, image_url, " +
                "favorite, servings, ready_in_minutes, source_name, source_url " +
                "FROM recipes " +
                "WHERE recipe_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, recipeId);
            if (results.next()) {
                recipe = mapRowToRecipe(results);
                recipe = attachIngredientsToRecipe(recipe);
                recipe = attachInstructionsToRecipe(recipe);
                recipe = attachDietsToRecipe(recipe);
                return recipe;
            }
        } catch (Exception e) {
            throw new DaoException("Error retrieving recipe", e);
        }
        return recipe;
    }

    private boolean recipeExists(String username, int spoonacularId) {
        String sql = "SELECT r.recipe_id  \n" +
                "FROM recipes AS r \n" +
                "JOIN recipes_users AS ru ON r.recipe_id = ru.recipe_id \n" +
                "WHERE ru.user_id = ? AND r.spoonacular_id = ?;";
        try {
            int userId = getUserIdByUsername(username);
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId, spoonacularId);
            if (result.next()) {
                alreadySavedRecipeId = result.getInt("recipe_id");
                return true;
            }
        } catch (Exception e) {
            throw new DaoException("Error retrieving recipe", e);
        }
        return false;
    }

    public Recipe favoriteRecipe(String username, Recipe recipe){
        return null;
    }

    @Override
    @Transactional
    public Recipe saveRecipe(String username, Recipe recipe) {
        String recipeSql = "INSERT INTO recipes (spoonacular_id, recipe_title, " +
                "image_url, favorite, servings, ready_in_minutes, source_name, source_url)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING recipe_id;";
        try {
            boolean alreadySaved = recipeExists(username, recipe.getId());
            if (alreadySaved) {
                recipe = getRecipeById(alreadySavedRecipeId);
            } else {
                // Add recipe
                int recipeId = jdbcTemplate.queryForObject(recipeSql, Integer.class,
                        recipe.getId(), recipe.getTitle(), recipe.getImage(),
                        recipe.isFavorite(), recipe.getServings(), recipe.getReadyInMinutes(),
                        recipe.getSourceName(), recipe.getSourceUrl());
                if (recipeId == 0) {
                    throw new DaoException("Error adding recipe, no id returned");
                }
                recipe.setRecipeId(recipeId);
                postRecipeToUserJoin(recipe.getRecipeId(), username);
                postInstructionsByRecipe(recipe);
                postIngredientsByRecipe(recipe);
                postDietsByRecipe(recipe);
            }
        } catch (Exception e) {
            throw new DaoException("Error adding recipe", e);
        }
        return recipe;
    }

    private Recipe attachIngredientsToRecipe(Recipe recipe) {
        String ingredientsSql = "SELECT ingredient_id, ingredient_name, aisle, amount_us, unit_us\n" +
                "FROM ingredients " +
                "WHERE recipe_id = ?;";
        try {
            SqlRowSet ingredientResults = jdbcTemplate.queryForRowSet(ingredientsSql, recipe.getRecipeId());
            List<Ingredient> ingredients = new ArrayList<>();
            while (ingredientResults.next()) {
                Ingredient ingredient = mapRowToIngredient(ingredientResults);
                ingredients.add(ingredient);
            }
            if (!ingredients.isEmpty()) {
                recipe.setExtendedIngredients(ingredients);
            }
        } catch (Exception e) {
            throw new DaoException("Error retrieving ingredients", e);
        }
        return recipe;
    }

    private Recipe attachInstructionsToRecipe(Recipe recipe) {
        String instructionSql = "SELECT step_number, step \n" +
                "FROM instructions " +
                "WHERE recipe_id = ?\n" +
                "ORDER BY step_number;";
        try {
            List<Step> steps = new ArrayList<>();
            SqlRowSet stepResults = jdbcTemplate.queryForRowSet(instructionSql, recipe.getRecipeId());
            while (stepResults.next()) {
                Step step = mapRowToStep(stepResults);
                steps.add(step);
            }
            if (!steps.isEmpty()) {
                Instruction instructions = new Instruction();
                instructions.setSteps(steps);
                List<Instruction> instructionsList = new ArrayList<>();
                instructionsList.add(instructions);
                recipe.setAnalyzedInstructions(instructionsList);
            }
        } catch (Exception e) {
            throw new DaoException("Error retrieving instructions", e);
        }
        return recipe;
    }

    private Recipe attachDietsToRecipe(Recipe recipe) {
        String dietsSql = "SELECT d.diet_name\n" +
                "FROM diets AS d\n" +
                "JOIN diets_recipes AS dr ON d.diet_id = dr.diet_id\n" +
                "WHERE dr.recipe_id = ?\n" +
                "ORDER BY d.diet_name;";
        try {
            List<String> diets = new ArrayList<>();
            SqlRowSet dietResults = jdbcTemplate.queryForRowSet(dietsSql, recipe.getRecipeId());
            while (dietResults.next()) {
                diets.add(dietResults.getString("diet_name"));
            }
            if (!diets.isEmpty()) {
                recipe = mapDietsToRecipe(recipe, diets);
            }
        } catch (Exception e) {
            throw new DaoException("Error retrieving diets", e);
        }
        return recipe;
    }

    private void postRecipeToUserJoin(int recipeId, String username){
        String userSql = "INSERT INTO recipes_users (recipe_id, user_id) " +
                "VALUES (?, (SELECT user_id FROM users WHERE username = ?));";
        try {
            int rowsAffected = jdbcTemplate.update(userSql, recipeId, username);
            if (rowsAffected == 0) {
                throw new DaoException("Error adding recipe to user's account");
            }
        } catch (Exception e) {
            throw new DaoException("Error adding recipe to user's account", e);
        }
    }
    private boolean postInstructionsByRecipe(Recipe recipe) {
        boolean instructionsPosted = false;
        String instructionsSql = "INSERT INTO instructions (recipe_id, step_number, step)\n" +
                "VALUES (?, ?, ?);";
        try {
            if (recipe.getAnalyzedInstructions().isEmpty()) {
                return false;
            }
            for (Step step : recipe.getAnalyzedInstructions().get(0).getSteps()) {
                int rowsAffected = jdbcTemplate.update(instructionsSql, recipe.getRecipeId(),
                        step.getNumber(), step.getStep());
                if (rowsAffected == 0) {
                    return false;
                }
                instructionsPosted = true;
            }
        } catch (Exception e) {
            throw new DaoException("Error adding instructions", e);
        }
        return instructionsPosted;
    }

    private boolean postIngredientsByRecipe(Recipe recipe) {
        String ingredientsSql = "INSERT INTO ingredients (recipe_id, ingredient_name, aisle, amount_us, unit_us)\n" +
                "VALUES (?, ?, ?, ?, ?) RETURNING ingredient_id;";
        try {
            for (Ingredient ingredient : recipe.getExtendedIngredients()) {
                int ingredientId = jdbcTemplate.queryForObject(ingredientsSql, Integer.class, recipe.getRecipeId(),
                            ingredient.getName(), ingredient.getAisle(), ingredient.getAmount(), ingredient.getUnit());
                if(ingredientId == 0) {
                    return false;
                }
            }
        } catch (Exception e) {
            throw new DaoException("Error adding ingredients", e);
        }
        return true;
    }

    private void postDietsByRecipe(Recipe recipe) {
        String dietJoinSql = "INSERT INTO diets_recipes (recipe_id, diet_id)\n" +
                "VALUES (?, (SELECT diet_id FROM diets WHERE diet_name = ?));";
        try {
            if (recipe.isDairyFree()) {
                jdbcTemplate.update(dietJoinSql, recipe.getRecipeId(), "Dairy Free");
            }
            if (recipe.isGlutenFree()) {
                jdbcTemplate.update(dietJoinSql, recipe.getRecipeId(), "Gluten Free");
            }
            if (recipe.isKetogenic()) {
                jdbcTemplate.update(dietJoinSql, recipe.getRecipeId(), "Ketogenic");
            }
            if (recipe.isVegan()) {
                jdbcTemplate.update(dietJoinSql, recipe.getRecipeId(), "Vegan");
            }
            if (recipe.isVegetarian()) {
                jdbcTemplate.update(dietJoinSql, recipe.getRecipeId(), "Vegetarian");
            }
        } catch (Exception e) {
            throw new DaoException("Error adding diets", e);
        }
    }

    /* Might be overthinking, but was wondering whether to just delete from the recipe_user join table because,
     * theoretically, multiple users could share the same recipe? In which case, we could use the following model:
        * String sqlJoinCuisines = "DELETE FROM cuisines_recipes " +
                    "JOIN recipes_users ON cuisines_recipes.recipe_id = recipes_users.recipe_id " +
                    "WHERE cuisines_recipes.recipe_id = ? AND recipes_users.user_id = ?";
        * Tori reply: I set the recipe/user relationship up so that a user would be able to edit their own
        * saved instance of a spoonacular recipe in case we got to that user story. Otherwise, yes this would have been awesome.
        * We'll also never user cuisines and dishtypes during this capstone haha
        */

    @Override
    public boolean removeRecipeFromLibrary(String username, int recipeId) {
        int rowsAffected = 0;
        boolean recipeRemoved = false;

        String sqlJoinCuisines = "DELETE FROM cuisines_recipes " +
                "WHERE recipe_id = ?";
        String sqlJoinDishTypes = "DELETE FROM dish_types_recipes " +
                "WHERE recipe_id = ?";
        String sqlJoinDiets = "DELETE FROM diets_recipes " +
                "WHERE recipe_id = ?";
        String sqlJoinMeals = "DELETE FROM meals_recipes " +
                "WHERE recipe_id = ?";
        String sqlIngredients = "DELETE FROM ingredients " +
                "WHERE recipe_id = ?";
        String sqlInstructions = "DELETE FROM instructions " +
                "WHERE recipe_id = ?";
        String sqlJoinUsers = "DELETE FROM recipes_users " +
                "WHERE recipe_id = ? AND user_id = ?;";
        String sqlRecipes = "DELETE FROM recipes " +
                "WHERE recipe_id = ?;";


        try {
            int userId = getUserIdByUsername(username);
            rowsAffected += jdbcTemplate.update(sqlJoinCuisines, recipeId);
            rowsAffected += jdbcTemplate.update(sqlJoinDishTypes, recipeId);
            rowsAffected += jdbcTemplate.update(sqlJoinDiets, recipeId);
            rowsAffected += jdbcTemplate.update(sqlJoinMeals, recipeId);
            rowsAffected += jdbcTemplate.update(sqlIngredients, recipeId);
            rowsAffected += jdbcTemplate.update(sqlInstructions, recipeId);
            rowsAffected += jdbcTemplate.update(sqlJoinUsers, recipeId, userId);
            rowsAffected += jdbcTemplate.update(sqlRecipes, recipeId);
            if (rowsAffected > 0) {
                recipeRemoved = true;
            }
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Error deleting recipe " + recipeId, e);
        }
        return recipeRemoved;
    }

    //userID method
    private int getUserIdByUsername(String username) {
        String sql = "SELECT user_id FROM users WHERE username = ?;";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, username);
        } catch (Exception e) {
            throw new DaoException("Error retrieving user id", e);
        }
    }

    //mapping methods
    private Recipe mapRowToRecipe(SqlRowSet results) {
        Recipe recipe = new Recipe();
        recipe.setRecipeId(results.getInt("recipe_id"));
        recipe.setId(results.getInt("spoonacular_id"));
        recipe.setTitle(results.getString("recipe_title"));
        recipe.setImage(results.getString("image_url"));
        recipe.setFavorite(results.getBoolean("favorite"));
        recipe.setServings(results.getInt("servings"));
        recipe.setReadyInMinutes(results.getInt("ready_in_minutes"));
        return recipe;
    }

    private Ingredient mapRowToIngredient(SqlRowSet results) {
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientId(results.getInt("ingredient_id"));
        ingredient.setName(results.getString("ingredient_name"));
        ingredient.setAmount(results.getDouble("amount_us"));
        ingredient.setUnit(results.getString("unit_us"));
        ingredient.setAisle(results.getString("aisle"));
        return ingredient;
    }

    private Step mapRowToStep(SqlRowSet results) {
        Step step = new Step();
        step.setNumber(results.getInt("step_number"));
        step.setStep(results.getString("step"));
        return step;
    }

    private Recipe mapDietsToRecipe(Recipe recipe, List<String> diets) {
        for (String diet : diets) {
            if (diet.equalsIgnoreCase("gluten free")) {
                recipe.setGlutenFree(true);
            } else if (diet.equalsIgnoreCase("ketogenic")) {
                recipe.setKetogenic(true);
            } else if (diet.equalsIgnoreCase("vegetarian")) {
                recipe.setVegetarian(true);
            } else if (diet.equalsIgnoreCase("vegan")) {
                recipe.setVegan(true);
            } else if (diet.equalsIgnoreCase("dairy free")) {
                recipe.setDairyFree(true);
            }
        }
        return recipe;
    }

}
