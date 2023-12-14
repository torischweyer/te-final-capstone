<template>
  <div class="container">
  <div class="recipe-view">
    <!-- for searching saved recipes in database -->
    <div class="search">
      <recipe-search
        @search="searchRecipesByQuery" />
    </div>

    <v-container>
      <v-row align="center" justify="center" no-gutters>
      <recipe-card
        class="d-flex flex-wrap"  
        v-for="recipe in this.recipes"
        v-bind:key="recipe.id"
        v-bind:recipe="recipe"
        v-bind:recipeId="recipe.recipeId"
        v-bind:routeName="'my-recipe-detail'"
        @openPopUp="openAddRecipePopUp"
        @recipeDeleted="refreshSavedRecipes" />
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
import RecipeService from "../services/RecipeService.js";
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

    };
  },
  methods: {
    searchRecipesByQuery(search) {
      // RecipeService.retrieveRecipesByQuery(search).then((response) => {
      //   this.search = search;
      //   const returnedRecipes = response.data.results;
      //   const filteredRecipes = this.filterQueryResults(returnedRecipes);
      //   this.recipes = filteredRecipes;
      // })
    },

    openAddRecipePopUp(payload) {
      const { savedRecipe } = payload;
      this.recipeToAddToMeal = savedRecipe;
      this.enablePopUp = true;
    },
    closePopUp() {
      this.enablePopUp = false;
    },
    refreshSavedRecipes() {
      RecipeService.retrieveSavedRecipes()
        .then((response) => {
          this.recipes = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    }
  },
  created() {
    RecipeService.retrieveSavedRecipes()
      .then((response) => {
        this.recipes = response.data;
      })
      .catch((error) => {
        console.log(error);
      });
  },
};
</script>
  
<style scoped>
.recipe-container {
  display: flex;
  justify-content: space-evenly;
  flex-wrap: wrap;
}
.recipe-view {
    margin: 0px 100px;
}
</style>