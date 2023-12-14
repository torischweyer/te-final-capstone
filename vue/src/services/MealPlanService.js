import axios from "axios";

export default {

    retrieveMealPlanByDate(date) {
        return axios.get('/mealplans?date=' + date);
    },

    addRecipeToMealPlan(mealPlanToUpdate) {
        return axios.post('/mealplans', mealPlanToUpdate);
    },

    //deleting a recipe from a meal plan does not delete the meal or meal plan
    deleteRecipeFromMealPlan(mealPlanId, mealId, recipeId) {
        return axios.delete(`/mealplans/${mealPlanId}/meals/${mealId}/recipes/${recipeId}`);
    }
    
}
