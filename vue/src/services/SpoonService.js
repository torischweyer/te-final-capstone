import axios from 'axios';

const http = axios.create({
    baseURL: ' https://api.spoonacular.com',
});
const API_KEY = '9c14af5fa50d485ea49306d80f7d7803';
const appendApiKey = '&apiKey=' + API_KEY;
export default {

    retrieveRandomRecipes() {
        return http.get('/recipes/random?number=30' + appendApiKey);
        // returns "recipes" array
    },

    retrieveRecipeByDiet(diet) {
        return http.get('/recipes/complexSearch?number=30&diet=' + diet + appendApiKey);
        // could later pull in multiple diets
        // returns "results" array
    },

    retrieveRecipesByQuery(query) {
        return http.get('/recipes/complexSearch?number=30&query=' + query + appendApiKey);
        // returns "results" array
    },

    retrieveRecipeByID(id) {   
        return http.get('/recipes/' + id + '/information?includeNutrition=false' + appendApiKey);
        //returns single recipe object
    }


}