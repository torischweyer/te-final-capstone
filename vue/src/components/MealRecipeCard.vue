<template>
  <div>

    <v-card class="ma-1 pa-0" width="330" height="350">

      <router-link v-bind:to="{ name: 'my-recipe-detail', params: { id: this.recipe.recipeId } }">
        <v-img v-bind:src="this.recipe.image" v-if="recipe.recipeId" height="200px" cover></v-img>
      </router-link>

      <v-card-title>{{ this.recipe.title }}</v-card-title>

      <v-card-subtitle>Ready in {{ this.recipe.readyInMinutes }} minutes</v-card-subtitle>

      <v-card-actions>
        <v-btn icon=$heart color="orange" title="Favorite"></v-btn>
        <v-btn icon=$delete color="red" title="Delete from Meal Plan" @click.prevent="deleteRecipeFromMealPlan"></v-btn>
      </v-card-actions>

      <!-- If we want to add diet tags:
        <v-btn color="brown" icon="$dairyFree" variant="text" title="Dairy-free" v-show="this.recipe.dairyFree" />
        <v-btn color="orange" prepend-icon="$glutenFree" variant="text" title="Gluten-free"
        v-show="this.recipe.glutenFree" />
        <v-btn color="green" icon="$ketogenic" variant="text" title="Ketogenic" v-show="this.recipe.ketogenic" />
        <v-btn color="green" icon="$vegetarian" variant="text" title="Vegetarian" v-show="this.recipe.vegetarian" />
        <v-btn color="green" icon="$vegan" variant="text" title="Vegan" v-show="this.recipe.vegan" /> -->

    </v-card>

  </div>
</template>
  
<script>
import MealPlanService from '../services/MealPlanService.js';

export default {
  props: ['recipe', 'mealPlanId', 'mealId', 'mealPlan'],
  data() {
    return {
      selectedMealPlan: null,
      selectedMeal: null
    }
  },

  methods: {
    deleteRecipeFromMealPlan() {
      MealPlanService.deleteRecipeFromMealPlan(this.mealPlanId, this.mealId, this.recipe.recipeId)
        .then((response) => {
          if (response.status === 200) {
            console.log("Recipe deleted from meal plan");
            MealPlanService.retrieveMealPlanByDate(this.mealPlan.date);
            this.$emit('recipeRemoved');
          }
        })
        .catch((error) => {
          console.log(error);
        });
    }
  }

};
</script>
  
<style scoped>
.mx-auto {
  margin: 10px;
  box-shadow: 1px;
}

a {
  text-decoration: none;
}
</style>

