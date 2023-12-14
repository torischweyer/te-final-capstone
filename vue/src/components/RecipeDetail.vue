<template>
  <v-container>
    
    <v-row dense>
      <v-col class="d-flex justify-center order-sm-1" cols="12">
        <h1>{{ this.recipe.title }}</h1>
      </v-col>
    </v-row>

    <v-row dense>
      <v-col cols="5" class="ma-2 pa-1 align-center">
        <div class="recipe-img">
          <v-img class="justify-start" v-if="recipe.id" v-bind:src="this.recipe.image" />
        </div>
        <div class="ingredients-list">
          <h3>Ingredients</h3>
          <v-list-item v-for="ingredient in this.recipe.extendedIngredients" v-bind:key="ingredient.id">
            {{ ingredient.amount }} {{ ingredient.unit }} {{ ingredient.name }}
          </v-list-item>
        </div>
      </v-col>

      <v-col cols="6" class="ma-2 pa-1">
        <div>
          <h4 class="diet-box"
            v-show="this.recipe.dairyFree || ($route.name === 'recipe-detail' && this.recipe.diets.includes('dairyFree'))">
            Dairy-free</h4>
          <h4 class="diet-box"
            v-show="this.recipe.glutenFree || ($route.name === 'recipe-detail' && this.recipe.diets.includes('glutenFree'))">
            Gluten-free</h4>
          <h4 class="diet-box"
            v-show="this.recipe.ketogenic || ($route.name === 'recipe-detail' && this.recipe.diets.includes('ketogenic'))">
            Keto</h4>
          <h4 class="diet-box"
            v-show="this.recipe.vegetarian || ($route.name === 'recipe-detail' && this.recipe.diets.includes('vegetarian'))">
            Vegetarian</h4>
          <h4 class="diet-box"
            v-show="this.recipe.vegan || ($route.name === 'recipe-detail' && this.recipe.diets.includes('vegan'))">Vegan
          </h4>
        </div>
        <div>
          <h4 id="minutes">
            Ready in {{ this.recipe.readyInMinutes }} minutes
          </h4>
          <h4 id="servings">Servings: {{ this.recipe.servings }}</h4>
        </div>

        <v-card-actions class="ma-0 pa-0">
          <v-list-item>
            <v-btn icon>
              <v-icon icon="$heartOutline" color="red" title="Favorite" />
            </v-btn>
          </v-list-item>

          <v-list-item v-if="this.$store.state.token != ''">
            <v-btn icon v-on:click.prevent="saveRecipe" v-bind:key="this.enableSave" v-on:click="enableSave = true">
              <v-icon v-if="!enableSave" icon="$addToLibrary" color="green" title="Add to library" />
              <v-icon v-if="enableSave" icon="$checkCircle" color="green" title="Added to library" />
            </v-btn>
          </v-list-item>

          <v-list-item>
            <v-btn icon v-if="this.$store.state.token != ''" v-on:click.stop="openAddRecipeToMealPopUp">
              <v-icon icon="$plusCircle" color="green" title="Add to meal plan" />
            </v-btn>
          </v-list-item>

          <v-list-item>
            <v-btn icon v-if="this.$route.name == 'my-recipe-detail'" v-on:click.stop="deleteRecipe">
              <v-icon icon="$delete" color="red" title="Delete from library" />
            </v-btn>
          </v-list-item>
        </v-card-actions>

        <h3>Instructions</h3>
        <div v-for="step in this.recipe.analyzedInstructions[0].steps" v-bind:key="step.number">
          <p class="step-number">{{ step.number }}. {{ step.step }}</p>
        </div>
      </v-col>
    </v-row>

  </v-container>
</template>

<script>
import RecipeService from "../services/RecipeService.js";

export default {
  props: ["recipe"],
  data() {
    return {
      enableSave: false,
    };
  },

  methods: {
    saveRecipe() {
      RecipeService.saveRecipe(this.recipe)
        .then((response) => {
          if (response.status === 201) {
            console.log(response.data);
            this.enableSave = true;
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    openAddRecipeToMealPopUp() {
      this.$emit("openPopUp", true);
    },
    deleteRecipe() {
      RecipeService.deleteRecipeFromLibrary(this.recipe.recipeId)
        .then((response) => {
          if (response.status === 200) {
            console.log("Recipe deleted");
            this.$emit("recipeDeleted");
          }
        })
        .catch((error) => {
          console.log(error);
        });
    }

  },
};
</script>

<style scoped>
.container {
  width: 100%;
  height: 100%;
  margin: 0 auto;
  padding: 0;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  gap: 15px;
}

.left-side {
  flex: 0 0 30%;
}

.recipe-img {
  width: 100%;
  height: auto;
  margin: 0 auto;
  /* text-align: left; */
  overflow: hidden;
}

img {
  width: 100%;
  height: 100%;
  margin: 0 auto;
  border-radius: 2px;
}

.ingredients-list {
  width: 100%;
  height: 100%;
  margin: 15px auto;
  padding-left: 15px;
  flex-wrap: wrap;
}

.middle {
  flex: 0 0 65%;
  margin-left: 10px;
}

.diet-box {
  background-color: rgb(50, 181, 50);
  color: white;
  border-radius: 5px;
  padding: 5px 10px;
  margin: 10px;
  display: inline-block;
}

#servings,
#minutes {
  display: inline-block;
  margin: 5px 10px;
}

h1 {
  margin: 0px 0px 10px 10px;
}

.instructions-list {
  width: 100%;
  height: 100%;
  margin: 10px auto;
  padding-left: 10px;
}

/* .step-number {
  display: inline-block;
  margin: 0px 10px 0px 0px;
} */
.right-side {
  flex: 0 0 5%;
  /* display: flex-block; */
  /* flex-direction: column; */
}

li {
  display: block;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.circular-button {
  width: 40px;
  height: 40px;
  background-color: #c6344f;
  /* Change the background color as needed */
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: background-color 0.3s ease;
  /* Add a transition for a smooth effect */

  /* Optional: Add box shadow for depth */
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

.circular-button:hover {
  background-color: #e85570;
  /* Change the hover background color as needed */
}

.plus {
  color: #fff;
  /* Set the color of the plus sign */
  font-size: 20px;
  /* Adjust the font size of the plus sign */
  font-weight: bold;
}</style>