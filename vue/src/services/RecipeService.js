import axios from "axios";

export default {

    retrieveSavedRecipes() {
        return axios.get('/recipes');
    },

    saveRecipe(recipe) {
        return axios.post('/recipes', recipe);
    },

    retrieveRecipeByID(recipeId) {
        return axios.get(`/recipes/${recipeId}`);
    },

    deleteRecipeFromLibrary(recipeId) {
        return axios.delete(`/recipes/${recipeId}`);
    }

}
