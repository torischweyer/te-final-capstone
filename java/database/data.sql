BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

INSERT INTO recipes (spoonacular_id, recipe_title, image_url, favorite, servings, ready_in_minutes, source_name, source_url)
VALUES (666, 'devil cake', 'https://realfood.tesco.com/media/images/RFO-1400x919-DevilsFoodCake-5a69df04-114b-491c-838d-5c112ede8a27-0-1400x919.jpg', true, 666, 666, 'the devil', 'url placeholder'),
(999, 'god cake', 'https://i1.wp.com/cakesandcupcakesmumbai.com/wp-content/uploads/2013/09/krishna-god-birthday-designer-cakes-cupcakes-mumbai-31.jpg?resize=604%2C405', true, 999, 999, 'god', 'url placeholder'),
(111, 'elsa', 'https://cdn11.bigcommerce.com/s-wumqzdp3rw/images/stencil/1280x1280/products/377/965/GUY_0002-Edit_2k75__84979.1623607467.jpg?c=1', false, 111, 111, 'elsa', 'url placeholder');

INSERT INTO recipes_users (recipe_id, user_id)
VALUES (1, 1), (2, 1), (3, 1);

insert into ingredients (recipe_id, ingredient_name, aisle, amount_us, unit_us) values 
(1, 'butter', 'The Butter Aisle', 2, 'Tbsp'), 
(1, 'sugar', 'The Sugar Aisle', 3, 'Tbsp'), 
(1, 'flour', 'The Flour Aisle', 4, 'Tbsp'),
(1, 'pickles', 'The Pickle Aisle', 666, ''),
(2, 'holy water', 'The Holy Water Aisle', 3, 'goblets'),
(2, 'bread', 'The Bread Aisle', 1, 'body'),
(2, 'wine', 'The Wine Aisle', 1, 'blood'),
(3, 'borscht', 'The Borscht Aisle', 1.5, 'cups'),
(3, 'sprinkles', 'The Sprinkle Aisle', 15, 'handfuls'),
(3, 'frosting', 'The Frosting Aisle', 1, 'tub');
	
insert into instructions (recipe_id, step_number, step) values (1, 1, 'call the devil on the phone'), (1, 2, 'leave him a message since he never answers'), (1, 3, 'give up');
insert into instructions (recipe_id, step_number, step) values (2, 1, 'call god on the phone'), (2, 2, 'ask him how the new dog is doing'), (2, 3, 'procure god cake instructions. easy');
insert into instructions (recipe_id, step_number, step) values (3, 1, 'go to whole foods'), (3, 2, 'spend your whole paycheck'), (3, 3, 'easy');

insert into diets (diet_name) values ('Vegan'), ('Vegetarian'), ('Gluten Free'), ('Dairy Free'), ('Ketogenic');

insert into diets_recipes (diet_id, recipe_id) values (5, 1), (1, 2), (4, 2), (3, 3);

INSERT INTO meals (meal_name, user_id) VALUES ('Breakfast', 1);
INSERT INTO meals (meal_name, user_id) VALUES ('Lunch', 1);
INSERT INTO meals (meal_name, user_id) VALUES ('Dinner', 1);
INSERT INTO meals (meal_name, user_id) VALUES ('Snack', 1);
INSERT INTO meals (meal_name, user_id) VALUES ('Fun Meal', 1);
INSERT INTO meals (meal_name, user_id) VALUES ('Breakfast', 1);
INSERT INTO meals (meal_name, user_id) VALUES ('Lunch', 1);
INSERT INTO meals (meal_name, user_id) VALUES ('Dinner', 1);
INSERT INTO meals (meal_name, user_id) VALUES ('Snack', 1);
INSERT INTO meals (meal_name, user_id) VALUES ('Fun Meal', 1);
INSERT INTO meals (meal_name, user_id) VALUES ('Breakfast', 1);
INSERT INTO meals (meal_name, user_id) VALUES ('Lunch', 1);
INSERT INTO meals (meal_name, user_id) VALUES ('Dinner', 1);
INSERT INTO meals (meal_name, user_id) VALUES ('Snack', 1);
INSERT INTO meals (meal_name, user_id) VALUES ('Fun Meal', 1);
INSERT INTO plans (date_of_plan, user_id, breakfast_id, lunch_id, dinner_id, snack_id, fun_id) VALUES ('2023-12-08', 1, 1, 2, 3, 4, 5);
INSERT INTO plans (date_of_plan, user_id, breakfast_id, lunch_id, dinner_id, snack_id, fun_id) VALUES ('2023-12-09', 1, 6, 7, 8, 9, 10);
INSERT INTO plans (date_of_plan, user_id, breakfast_id, lunch_id, dinner_id, snack_id, fun_id) VALUES ('2023-12-10', 1, 11, 12, 13, 14, 15);
INSERT INTO meals_recipes (meal_id, recipe_id) VALUES (1, 1);
INSERT INTO meals_recipes (meal_id, recipe_id) VALUES (2, 2);
INSERT INTO meals_recipes (meal_id, recipe_id) VALUES (3, 1);
INSERT INTO meals_recipes (meal_id, recipe_id) VALUES (3, 2);
INSERT INTO meals_recipes (meal_id, recipe_id) VALUES (4, 3);
INSERT INTO meals_recipes (meal_id, recipe_id) VALUES (6, 2);
INSERT INTO meals_recipes (meal_id, recipe_id) VALUES (7, 1);
INSERT INTO meals_recipes (meal_id, recipe_id) VALUES (9, 2);
INSERT INTO meals_recipes (meal_id, recipe_id) VALUES (10, 1);
INSERT INTO meals_recipes (meal_id, recipe_id) VALUES (11, 2);
INSERT INTO meals_recipes (meal_id, recipe_id) VALUES (12, 1);
INSERT INTO meals_recipes (meal_id, recipe_id) VALUES (12, 2);
INSERT INTO meals_recipes (meal_id, recipe_id) VALUES (13, 3);
INSERT INTO meals_recipes (meal_id, recipe_id) VALUES (15, 3);

COMMIT TRANSACTION;
