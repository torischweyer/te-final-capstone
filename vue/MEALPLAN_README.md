# Final Capstone: Chef's Compass, Meal Planning Application
## Team Juliett: 
- David Whitaker
- Tori Schweyer
- Rafael Fogel
- Kristina Escondo
​
## Description
​
Our meal planning application allows users to search for recipes using a web API and, if they wish, browse recipes by diet style. Logged in users are also able to save recipes to their own library, as well as create and modify daily meal plans.

## Technologies used
- Java
- Spring Boot
- SQL
- Vue.js
- RESTful APIs
- GitLab
- IntelliJ
- PostGreSQL
- Postman
- Visual Studio Code
- Material Design (Vuetify)

### API documentation​
```js 
List saved recipes
Path: /recipes
Request method: GET
Return: list of recipes
Status: 200 SUCCESS
Error: 400 Bad Request

Descr: View a saved recipe
Path: /recipes/{id}
Request method: GET
Return: list of recipes
Status: 200 SUCCESS
Error: 404 Not Found

Descr: Add (save) a recipe
Path: /recipes
Request method: POST
Return: recipe object
Status: 201 CREATED
Error: 400 Bad Request

Descr: List (view) meal plans
Path: /mealplans
Request method: GET
Return: list of meal plans
Status: 200 SUCCESS
Error: 400 Bad Request

Descr: View a meal plan
Path: /mealplans?date={date}
Request method: GET
Return: meal plan object
Status: 200 SUCCESS
Error: 404 Not Found

Descr: Add Recipe to Meal Plan
Path: /mealplans
Request method: POST
Return: meal plan object
Status: 201 CREATED
Error: 400 Bad Request

Descr: Remove Recipe from Meal Plan
Path: /mealplans/{mealPlanId}/meals/{mealId}/recipes/{recipeId}
Request method: DELETE
Status: 200 
Error: 400 Bad Request
```
## Screenshots​
![Home page](src\assets\screenshot-homepage.png)
![Search recipes](src\assets\screenshot-search-recipes.png)
![Recipe detail](src\assets\screenshot-recipe-detail.png)
![My saved recipes](src\assets\screenshot-saved-recipes.png)
![Meal plan view](src\assets\screenshot-meal-plan.png)
