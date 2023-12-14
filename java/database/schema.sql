BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, recipes, recipes_users, ingredients, ingredients_recipes, 
instructions, cuisines, cuisines_recipes, diets, 
diets_recipes, dish_types, dish_types_recipes CASCADE;

--PROVIDED:
CREATE TABLE users (
	user_id SERIAL,
	username TEXT NOT NULL UNIQUE,
	password_hash TEXT NOT NULL,
	role TEXT NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

--TEAM MADE:
CREATE TABLE recipes (
	recipe_id SERIAL,
	spoonacular_id int NOT NULL,
	recipe_title TEXT,
	image_url TEXT,
	favorite boolean DEFAULT false,
	servings int,
	ready_in_minutes int,
	source_name TEXT,
	source_url TEXT,
	CONSTRAINT PK_recipe PRIMARY KEY (recipe_id)
	
);

CREATE TABLE recipes_users (
	recipe_id int,
	user_id int,
	CONSTRAINT PK_recipe_user PRIMARY KEY (recipe_id, user_id),
	CONSTRAINT FK_recipe FOREIGN KEY (recipe_id) REFERENCES recipes (recipe_id),
	CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE ingredients (
	ingredient_id SERIAL,
	recipe_id int,
	ingredient_name TEXT NOT NULL,
	aisle TEXT,
	amount_us numeric(12,2),
	unit_us TEXT,
	CONSTRAINT PK_ingredient PRIMARY KEY (ingredient_id),
	CONSTRAINT FK_recipe FOREIGN KEY (recipe_id) REFERENCES recipes (recipe_id)
);

CREATE TABLE instructions (
	instruction_id SERIAL,
	recipe_id int,
	step_number int,
	step TEXT,
	CONSTRAINT PK_instruction PRIMARY KEY (instruction_id),
	CONSTRAINT FK_recipe FOREIGN KEY (recipe_id) REFERENCES recipes (recipe_id)
);

CREATE TABLE cuisines (
	cuisine_id SERIAL,
	cuisine_name TEXT NOT NULL,
	CONSTRAINT PK_cuisine PRIMARY KEY (cuisine_id)
);

CREATE TABLE cuisines_recipes (
	cuisine_id int,
	recipe_id int,
	CONSTRAINT PK_cuisine_recipe PRIMARY KEY (cuisine_id, recipe_id),
	CONSTRAINT FK_cuisine FOREIGN KEY (cuisine_id) REFERENCES cuisines (cuisine_id),
	CONSTRAINT FK_recipe FOREIGN KEY (recipe_id) REFERENCES recipes (recipe_id)
);

CREATE TABLE diets (
	diet_id SERIAL,
	diet_name TEXT NOT NULL,
	CONSTRAINT PK_diet PRIMARY KEY (diet_id)
);

CREATE TABLE diets_recipes (
	diet_id int,
	recipe_id int,
	CONSTRAINT PK_diet_recipe PRIMARY KEY (diet_id, recipe_id),
	CONSTRAINT FK_diet FOREIGN KEY (diet_id) REFERENCES diets (diet_id),
	CONSTRAINT FK_recipe FOREIGN KEY (recipe_id) REFERENCES recipes (recipe_id)
);

CREATE TABLE dish_types (
	dish_type_id SERIAL,
	dish_type_name TEXT NOT NULL,
	CONSTRAINT PK_dish_type PRIMARY KEY (dish_type_id)
);

CREATE TABLE dish_types_recipes (
	dish_type_id int,
	recipe_id int,
	CONSTRAINT PK_dish_type_recipe PRIMARY KEY (dish_type_id, recipe_id),
	CONSTRAINT FK_dish_type FOREIGN KEY (dish_type_id) REFERENCES dish_types (dish_type_id),
	CONSTRAINT FK_recipe FOREIGN KEY (recipe_id) REFERENCES recipes (recipe_id)
);

CREATE TABLE meals (
	meal_id SERIAL,
	meal_name TEXT NOT NULL,
	user_id int NOT NULL,
	CONSTRAINT PK_meal PRIMARY KEY (meal_id),
	CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE plans (
	plan_id SERIAL,
	date_of_plan DATE NOT NULL,
	user_id int NOT NULL,
	breakfast_id int,
	lunch_id int,
	dinner_id int,
	snack_id int,
	fun_id int,
	CONSTRAINT PK_plan PRIMARY KEY (plan_id),
	CONSTRAINT FK_breakfast FOREIGN KEY (breakfast_id) REFERENCES meals (meal_id),
	CONSTRAINT FK_lunch FOREIGN KEY (lunch_id) REFERENCES meals (meal_id),
	CONSTRAINT FK_dinner FOREIGN KEY (dinner_id) REFERENCES meals (meal_id),
	CONSTRAINT FK_snack FOREIGN KEY (snack_id) REFERENCES meals (meal_id),
	CONSTRAINT FK_fun FOREIGN KEY (fun_id) REFERENCES meals (meal_id),
	CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE meals_recipes (
	meal_id int NOT NULL,
	recipe_id int NOT NULL,
	CONSTRAINT PK_meal_recipe PRIMARY KEY (meal_id, recipe_id),
	CONSTRAINT FK_meal FOREIGN KEY (meal_id) REFERENCES meals (meal_id),
	CONSTRAINT FK_recipe FOREIGN KEY (recipe_id) REFERENCES recipes (recipe_id)
);





COMMIT TRANSACTION;
