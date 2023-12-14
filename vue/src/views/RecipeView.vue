<template>
  <div class="container">
  <div class="main" v-bind:class="{ 'logged-out': this.$store.state.token == '' }">
    <div class="search">
      <!-- for searching Spoonacular recipes -->
        <recipe-search @search="searchRecipesByQuery" />
    </div>
    
    <div id="diet-field" class="d-flex justify-center ga-2">
      <v-btn-toggle v-model="toggle" divided variant="outlined">
      <v-btn prepend-icon="$vegetarian" v-model="isVegetarian"
        @click="() => { isVegetarian = !isVegetarian; filterRecipeByDiet('vegetarian'); }"
        v-bind:class="{ 'selectedDiet': isVegetarian }">
        Vegetarian
      </v-btn>
      <v-btn prepend-icon="$vegan" v-model="isVegan"
        @click="() => { isVegan = !isVegan; filterRecipeByDiet('vegan'); }" v-bind:class="{ 'selectedDiet': isVegan }">
        Vegan
      </v-btn>
      <v-btn prepend-icon="$dairyFree" v-model="isDairyFree"
        @click="() => { isDairyFree = !isDairyFree; filterRecipeByDiet('dairyFree'); }"
        v-bind:class="{ 'selectedDiet': isDairyFree }">
        Dairy-Free
      </v-btn>
      <v-btn prepend-icon="$glutenFree" v-model="isGlutenFree"
        @click="() => { isGlutenFree = !isGlutenFree; filterRecipeByDiet('glutenFree'); }"
        v-bind:class="{ 'selectedDiet': isGlutenFree }">
        Gluten-Free
      </v-btn>
      <v-btn prepend-icon="$ketogenic" v-model="isKetogenic"
        @click="() => { isKetogenic = !isKetogenic; filterRecipeByDiet('ketogenic'); }"
        v-bind:class="{ 'selectedDiet': isKetogenic }">
        Ketogenic
      </v-btn>
    </v-btn-toggle>
    </div>

    <v-container>
      <v-row align="center" justify="center" no-gutters>
        <recipe-card 
          class="d-flex flex-wrap" 
          v-for="recipe in this.recipes" 
          v-bind:key="recipe.id"
          v-bind:recipe="recipe" 
          v-bind:recipeId="recipe.id" 
          v-bind:routeName="'recipe-detail'" 
          @openPopUp="openAddRecipePopUp"/>
      </v-row>
    </v-container>

    <v-col>
        <v-dialog v-model="enablePopUp" transition="dialog-top-transition" width="auto" >
          <template v-slot:default="{ isActive }">
            <v-card>
              <v-card-text>
                <add-recipe-to-meal v-bind:recipe="this.recipeToAddToMeal" @closePopUp="closePopUp" />
              </v-card-text>
              <v-card-actions class="justify-center">
                <v-btn variant="text" @click="isActive.value = false">Close</v-btn>
              </v-card-actions>
            </v-card>
          </template>
        </v-dialog>
      </v-col>

  </div>
</div>
</template>

<script>
import RecipeSearch from "../components/RecipeSearch.vue";
import RecipeCard from "../components/RecipeCard.vue";
import SpoonService from "../services/SpoonService.js";
import AddRecipeToMeal from "../components/AddRecipeToMeal.vue";

export default {
  components: {
    RecipeSearch,
    RecipeCard,
    AddRecipeToMeal
  },
  data() {
    return {
      recipes: [],
      search: "",
      diet: "",
      enablePopUp: false,
      recipeToAddToMeal: {},

      isVegetarian: false,
      isVegan: false,
      isGlutenFree: false,
      isDairyFree: false,
      isKetogenic: false,
      toggle: null,

    };
  },
  methods: {
    searchRecipesByQuery(search) {
      SpoonService.retrieveRecipesByQuery(search).then((response) => {
        this.search = search;
        const returnedRecipes = response.data.results;
        const filteredRecipes = this.filterQueryResults(returnedRecipes);
        this.recipes = filteredRecipes;
      })
    },
    filterRandomResults(recipes) {
      const filteredRecipes = recipes.filter((recipe) => {
        const hasImage = recipe.image !== null && recipe.image !== undefined && recipe.image !== "";
        const hasTitle = recipe.title !== null && recipe.title !== undefined && recipe.title !== "";
        const hasInstructions = recipe.analyzedInstructions !== null && recipe.analyzedInstructions !== undefined && recipe.analyzedInstructions.length > 0;
        const hasIngredients = recipe.extendedIngredients !== null && recipe.extendedIngredients !== undefined && recipe.extendedIngredients.length > 0;
        const hasReadyInMinutes = recipe.readyInMinutes !== null && recipe.readyInMinutes !== undefined && recipe.readyInMinutes > 0;
        const hasServings = recipe.servings !== null && recipe.servings !== undefined && recipe.servings > 0;
        return hasImage && hasTitle && hasInstructions && hasIngredients && hasReadyInMinutes && hasServings;

      });
      return filteredRecipes;
    },
    filterQueryResults(recipes) {
      const filteredRecipes = recipes.filter((recipe) => {
        const hasImage = recipe.image !== null && recipe.image !== undefined && recipe.image !== "";
        const hasTitle = recipe.title !== null && recipe.title !== undefined && recipe.title !== "";
        return hasImage && hasTitle;
      });
      return filteredRecipes;
    },
    filterRecipeByDiet(diet) {
      this.diet = diet;
      let filteredRecipes = [];
      SpoonService.retrieveRecipeByDiet(diet).then((response) => {
        filteredRecipes = response.data.results;
        this.recipes = this.filterQueryResults(filteredRecipes);
      });
    },
    openAddRecipePopUp(payload) {
      const { spoonacularId } = payload;
      SpoonService.retrieveRecipeByID(spoonacularId).then((response) => {
        this.recipeToAddToMeal = response.data;
      }).catch((error) => {
        console.log(error);
      });
      this.enablePopUp = true;
    },
    closePopUp() {
      this.enablePopUp = false;
    },
  },
  created() {
    SpoonService.retrieveRandomRecipes().then((response) => {
      console.log(response.data)
      const recipes = response.data.recipes;
      this.recipes = this.filterRandomResults(recipes);
    }).catch((error) => {
      console.log(error);
    })
  },
};
</script>

<style scoped>
.container {
  position: relative;
}
.recipe-container {
  display: flex;
  justify-content: space-evenly;
  flex-wrap: wrap;
}

.main {
  margin: 0px 100px;
}

.logged-out {
  margin: 100px 100px;
}
add-recipe-to-meal {
  position: absolute;
  top: 0;
  left: 0;
}
</style>