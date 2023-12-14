--GET
--GET ALL RECIPES ASSOCIATED WITH A USER
SELECT r.recipe_id, spoonacular_id, recipe_title, image_url, favorite, servings, ready_in_minutes, source_name, source_url
FROM recipes AS r
JOIN recipes_users AS ru ON r.recipe_id = ru.recipe_id
JOIN users AS u ON ru.user_id = u.user_id
WHERE u.username = ?
ORDER BY r.recipe_id;

--GET ALL RECIPES ASSOCIATED WITH A USER (FAVORITES)
SELECT r.recipe_id, spoonacular_id, recipe_title, image_url, favorite, servings, ready_in_minutes, source_name, source_url
FROM recipes AS r
JOIN recipes_users AS ru ON r.recipe_id = ru.recipe_id
JOIN users AS u ON ru.user_id = u.user_id
WHERE u.username = ? AND favorite = true
ORDER BY r.recipe_id;

--GET A RECIPE BY ID
SELECT recipe_id, spoonacular_id, recipe_title, image_url, favorite, servings, ready_in_minutes, source_name, source_url
FROM recipes
WHERE recipe_id = ?;

--GET ALL INGREDIENTS ASSOCIATED WITH A RECIPE (could be a part of the recipe query)
SELECT i.ingredient_id, i.ingredient_name, i.aisle, ir.amount_us, ir.unit_us
FROM ingredients AS i
JOIN ingredients_recipes AS ir ON i.ingredient_id = ir.ingredient_id
WHERE ir.recipe_id = ?;

--GET ALL INSTRUCTIONS ASSOCIATED WITH A RECIPE (could be a part of the recipe query)
SELECT i.instruction_id, step_number, step 
FROM instructions AS i
JOIN instructions_recipes AS ir ON i.instruction_id = ir.instruction_id
WHERE ir.recipe_id = ?
ORDER BY step_number;

--GET ALL DIETS ASSOCIATED WITH A RECIPE:
SELECT d.diet_name
FROM diets AS d
JOIN diets_recipes AS dr ON d.diet_id = dr.diet_id
WHERE dr.recipe_id = ?
ORDER BY d.diet_name;

--GET ALL CUISINES ASSOCIATED WITH A RECIPE:
SELECT c.cuisine_name
FROM cuisines AS c
JOIN cuisines_recipes AS cr ON c.cuisine_id = cr.cuisine_id
WHERE cr.recipe_id = ?
ORDER BY c.cuisine_name;

--GET ALL DISH TYPES ASSOCIATED WITH A RECIPE:
SELECT dt.dish_type_name
FROM dish_types AS dt
JOIN dish_types_recipes AS dtr ON dt.dish_type_id = dtr.dish_type_id
WHERE dtr.recipe_id = ?
ORDER BY dt.dish_type_name;




--RECIPES BY CATEGORY
--GET ALL RECIPES ASSOCIATED WITH A DIET:
SELECT r.recipe_id, spoonacular_id, recipe_title, image_url, favorite, servings, ready_in_minutes, source_name, source_url
FROM recipes AS r
JOIN diets_recipes AS dr ON r.recipe_id = dr.recipe_id
JOIN diets AS d ON dr.diet_id = d.diet_id
JOIN recipes_users AS ru ON r.recipe_id = ru.recipe_id
JOIN users AS u ON ru.user_id = u.user_id
WHERE u.username = ? AND d.diet_name = ?
ORDER BY r.recipe_id;

--GET ALL RECIPES ASSOCIATED WITH A CUISINE:
SELECT r.recipe_id, spoonacular_id, recipe_title, image_url, favorite, servings, ready_in_minutes, source_name, source_url
FROM recipes AS r
JOIN cuisines_recipes AS cr ON r.recipe_id = cr.recipe_id
JOIN cuisines AS c ON cr.cuisine_id = c.cuisine_id
JOIN recipes_users AS ru ON r.recipe_id = ru.recipe_id
JOIN users AS u ON ru.user_id = u.user_id
WHERE u.username = ?  AND c.cuisine_name = ?
ORDER BY r.recipe_id;

--GET ALL RECIPES ASSOCIATED WITH A DISH TYPE:
SELECT r.recipe_id, spoonacular_id, recipe_title, image_url, favorite, servings, ready_in_minutes, source_name, source_url
FROM recipes AS r
JOIN dish_types_recipes AS dtr ON r.recipe_id = dtr.recipe_id
JOIN dish_types AS dt ON dtr.dish_type_id = dt.dish_type_id
JOIN recipes_users AS ru ON r.recipe_id = ru.recipe_id
JOIN users AS u ON ru.user_id = u.user_id
WHERE u.username = ?  AND dt.dish_type_name = ?
ORDER BY r.recipe_id;

--GET ALL RECIPES THAT INCLUDE INGREDIENT
SELECT r.recipe_id, spoonacular_id, recipe_title, image_url, favorite, servings, ready_in_minutes, source_name, source_url
FROM recipes AS r
JOIN ingredients_recipes AS ir ON r.recipe_id = ir.recipe_id
JOIN ingredients AS i ON ir.ingredient_id = i.ingredient_id
JOIN recipes_users AS ru ON r.recipe_id = ru.recipe_id
JOIN users AS u ON ru.user_id = u.user_id
WHERE u.username = ?  AND i.ingredient_name ILIKE ?
ORDER BY r.recipe_id;




--PUT
--UPDATE A RECIPE:
UPDATE recipes
SET spoonacular_id = ?, recipe_title = ?, image_url = ?, favorite = ?, 
servings = ?, ready_in_minutes = ?, source_name = ?, source_url = ?
WHERE recipe_id = ?





--POST
--ADD A RECIPE:
INSERT INTO recipes (spoonacular_id, recipe_title, image_url, favorite, servings, ready_in_minutes, source_name, source_url)
VALUES (?, ?, ?, ?, ?, ?, ?)
RETURNING recipe_id;

--ADD A USER JOIN
INSERT INTO recipes_users (recipe_id, user_id)
VALUES (?, (SELECT user_id FROM users WHERE username = ?));

--ADD AN INGREDIENT (java-side: while <ingredients.length (if ingredient_name doesn't exist)):
INSERT INTO ingredients (ingredient_name, aisle)
VALUES (?, ?);
--ADD TO INGREDIENT-RECIPE(java-side: while <ingredients.length):
INSERT INTO ingredients_recipes (ingredient_id, recipe_id, amount_us, unit_us)
VALUES (?, ?, ?, ?);

--ADD INSTRUCTIONS (java-side: while <instructions.size (map?))
INSERT INTO instructions (recipe_id, step_number, step)
VALUES (?, ?, ?);
-- --ADD TO INSTRUCTIONS-RECIPES (java-side: while <instructions.size)
-- INSERT INTO instructions_recipes (instruction_id, recipe_id)
-- VALUES (?, ?)

--ADD A DIET (java-side while <diets.length (if diet_name doesn't exist)):
INSERT INTO diets (diet_name)
VALUES (?);
--ADD TO DIETS-RECIPES
INSERT INTO diets_recipes (diet_id, recipe_id)
VALUES (?, ?);

--ADD A CUISINE (java-side while <cuisines.length (if cuisine_name doesn't exist)):
INSERT INTO cuisines (cuisine_name)
VALUES (?);
--ADD TO CUISINES-RECIPES
INSERT INTO cuisines_recipes (cuisine_id, recipe_id)
VALUES (?, ?);

--ADD A DISH TYPE (java-side while <dishTypes.length (if dish_type_name doesn't exist)):
INSERT INTO dish_types (dish_type_name)
VALUES (?);
--ADD TO DISH_TYPES_RECIPES
INSERT INTO dish_types_recipes (dish_types_id, recipe_id)
VALUES (?, ?);



--DELETE A RECIPE
--1: DELETE JOIN TABLES (java-side: breakout method)
DELETE FROM ingredients_recipes
WHERE recipe_id = ?;

DELETE FROM instructions_recipes
WHERE recipe_id = ?;

DELETE FROM diets_recipes
WHERE recipe_id = ?;

DELETE FROM cuisines_recipes
WHERE recipe_id = ?;

DELETE FROM dish_types_recipes
WHERE recipe_id = ?;

DELETE FROM recipes_users
WHERE recipe_id = ?;

--2: DELETE RECIPE
DELETE FROM recipes
WHERE recipe_id =?;