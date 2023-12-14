<template>
  <v-card class="mx-auto" width="340" height="400">
    <router-link v-bind:to="{ name: routeName, params: { id: this.recipeId } }"
      style="text-decoration: none; color: inherit">
      <v-img class="align-end text-white" v-if="recipe.id" v-bind:src="this.recipe.image" width="340" max-height="220"
        cover></v-img>
      <v-card-title class="recipe-title text-wrap text-center ma-1" style="max-width: 320px">
        {{ recipe.title }}</v-card-title>
      <v-card-subtitle class="minutes text-center ma-0 pa-0" v-if="routeName == 'my-recipe-detail'">Ready in
        {{ recipe.readyInMinutes }} minutes</v-card-subtitle>
    </router-link>

    <!-- If the user is logged in, they can use these buttons -->
    <v-card-actions v-if="this.$store.state.token != ''" class="ma-2">
      <v-btn icon>
        <v-icon icon="$heartOutline" color="red" title="Favorite" />
      </v-btn>

      <v-btn icon v-on:click.stop="saveRecipe" v-if="this.routeName === 'recipe-detail'" v-bind:key="this.enableSave"
        v-on:click="enableSave = true">
        <v-icon v-if="!enableSave" icon="$addToLibrary" color="green" title="Add to library" />
        <v-icon v-if="enableSave" icon="$checkCircle" color="green" title="Added to library" />
      </v-btn>

      <v-btn icon v-on:click.stop="openAddRecipeToMealPopUp">
        <v-icon icon="$plusCircle" color="green" title="Add to meal plan" />
      </v-btn>

      <v-btn icon v-on:click.stop="deleteRecipe" v-if="this.routeName != 'recipe-detail'">
        <v-icon icon="$delete" color="red" title="Delete from library" />
      </v-btn>
    </v-card-actions>

    <!-- If the user is not logged in, clicking the buttons takes them to the login -->
    <v-card-actions v-if="this.$store.state.token == ''" class="ma-2">
      <v-btn icon>
        <router-link v-bind:to="{ name: 'login' }">
          <v-icon icon="$heartOutline" color="red" title="Favorite" />
        </router-link>
      </v-btn>

      <v-btn icon>
        <router-link v-bind:to="{ name: 'login' }">
          <v-icon icon="$addToLibrary" color="green" title="Add to library" />
        </router-link>
      </v-btn>

      <v-btn icon>
        <router-link v-bind:to="{ name: 'login' }">
          <v-icon icon="$plusCircle" color="green" title="Add to meal plan" />
        </router-link>
      </v-btn>

    </v-card-actions>

  </v-card>
</template>

<script>
import RecipeService from "../services/RecipeService.js";
import SpoonService from "../services/SpoonService.js";

export default {
  props: ["recipe", "recipeId", "routeName"],
  data() {
    return {
      fullRecipe: {},
      enableSave: false,
    };
  },
  methods: {
    saveRecipe() {
      SpoonService.retrieveRecipeByID(this.recipe.id)
        .then((response) => {
          this.fullRecipe = response.data;
          RecipeService.saveRecipe(this.fullRecipe).then((response) => {
            if (response.status === 201) {
              console.log(response.data);
            }
          });
        })
        .catch((error) => {
          console.log(error);
        });
    },
    openAddRecipeToMealPopUp() {
      if (this.routeName === "recipe-detail" || this.$route.name === "recipes") {
        this.$emit("openPopUp", { spoonacularId: this.recipe.id });
      } else if (this.routeName === "my-recipe-detail") {
        this.$emit("openPopUp", { savedRecipe: this.recipe });
      }
    },
    deleteRecipe() {
      RecipeService.deleteRecipeFromLibrary(this.recipeId)
        .then((response) => {
          if (response.status === 200) {
            console.log("Recipe deleted");
            RecipeService.retrieveSavedRecipes();
            this.$emit('recipeDeleted');
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
.mx-auto {
  margin: 10px;
  box-shadow: 2px;
}

.container {
  display: flexbox;
  border-radius: 4px;
  width: 15%;
  height: 250px;
  margin: 10px;
  padding: 8px;
  align-items: center;
  justify-items: center;
  flex-wrap: wrap;
  box-shadow: 2px 2px 2px 2px #bbbbbb;
}

img {
  display: block;
  width: 90%;
  height: 100%;
  margin: 10px;
  object-fit: cover;
  box-shadow: 1px 1px 1px 1px #bbbbbb;
}

router-link {
  text-decoration: none;
}

/*the code below is to truncate recipe titles to 2 lines (webkit line clamp)*/
.recipe-title {
  text-decoration: none;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  white-space: normal;
}

.save {
  display: flex;
  justify-content: center;
  position: relative;
}

.save .save_btn {
  background-color: lightgray;
  display: block;
  width: 20%;
  padding: 0.5rem;
  font-size: 1.25rem;
  color: #3363ff;
  background-color: #e6ecff;
  border: none;
  border-radius: 0.5rem;
  transition: 0.2s;
  cursor: pointer;
  text-align: center;
}

.recipe-title {
  font-size: 1.1rem;
  /* font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif; */
  text-decoration: none;
}

.minutes {
  font-size: 0.8rem;
  /* font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif; */
  text-decoration: none;
}

.card .recipe-title {
  font-size: 1rem;
}

/* .v-card-title{
    flex-wrap: wrap;
} */
</style>