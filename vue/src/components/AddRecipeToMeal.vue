<template>
  <div class="container">
    <v-container class="meal-calendar">
      <v-row justify="space-around">
        <v-date-picker color="green-lighten-1" v-model="selectedDate"></v-date-picker>
      </v-row>
    </v-container>
    <div class="add-to-meal-btns">
      <v-menu class="meal-list" transition="slide-x-transition">
        <template v-slot:activator="{ props }">
          <v-btn color="green-lighten-1" v-bind="props"> Select Meal </v-btn>
        </template>

        <v-list>
          <v-list-item
            v-for="(item, i) in items"
            :key="i"
            @click="selectedMeal = item.title"
          >
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
      <v-btn
        class="add-recipe"
        color="green-lighten-1"
        v-bind:disabled="!allowSubmit"
        @click.prevent="addRecipeToMealPlan"
        >Submit</v-btn
      >
    </div>
  </div>
</template>

<script>
import MealPlanService from "../services/MealPlanService.js";
import RecipeService from "../services/RecipeService.js";

export default {
  props: ["recipe"],
  data() {
    return {
      items: [
        { title: "Breakfast" },
        { title: "Lunch" },
        { title: "Dinner" },
        { title: "Snack" },
        { title: "Fun" },
      ],
      selectedMeal: null,
      selectedDate: null,
      recipeToAdd: {},
      mealPlanToEdit: {
        mealPlanId: 0,
        date: "",
        breakfast: {
          mealId: 0,
          name: "Breakfast",
          recipes: [],
        },
        lunch: {
          mealId: 0,
          name: "Lunch",
          recipes: [],
        },
        dinner: {
          mealId: 0,
          name: "Dinner",
          recipes: [],
        },
        snack: {
          mealId: 0,
          name: "Snack",
          recipes: [],
        },
        fun: {
          mealId: 0,
          name: "Fun Meal",
          recipes: [],
        },
      },
    };
  },
  methods: {
    addRecipeToMealPlan() {
      console.log("inside addRecipeToMealPlan");
      console.log("recipe prop: ", this.recipe.id , " ", this.recipe)
      console.log("recipe to add: ", this.recipeToAdd.id , " ", this.recipeToAdd)
      if (this.$route.name === "recipe-detail" || this.$route.name === "recipes") {
        RecipeService.saveRecipe(this.recipe)
          .then((response) => {
            if (response.status === 201 || response.status === 200) {
              const savedRecipe = response.data;
              this.saveMealPlan(savedRecipe)
            }
          })
          .catch((error) => {
            console.log(error);
          });
      } else {
        this.saveMealPlan(this.recipeToAdd);
      }
    },
    saveMealPlan(recipe) {
      // User chooses calender date - date is saved to Meal Plan object
      const formattedDate = new Date(this.selectedDate).toISOString().split('T')[0];

      this.mealPlanToEdit.date = formattedDate;
      // User chooses meal name - recipe is saved to Meal Plan object by meal name

      switch (this.selectedMeal) {
        case "Breakfast":
          this.mealPlanToEdit.breakfast.recipes.push(recipe);
          console.log("Breakfast: " , this.mealPlanToEdit.breakfast.recipes)
          break;
        case "Lunch":
          this.mealPlanToEdit.lunch.recipes.push(recipe);
          break;
        case "Dinner":
          this.mealPlanToEdit.dinner.recipes.push(recipe);
          break;
        case "Snack":
          this.mealPlanToEdit.snack.recipes.push(recipe);
          break;
        case "Fun":
          this.mealPlanToEdit.fun.recipes.push(recipe);
          break;
      }

      MealPlanService.addRecipeToMealPlan(this.mealPlanToEdit)
        .then((response) => {
          if (response.status === 201) {
            console.log("Recipe added to meal plan");
          }
        })
        .catch((error) => {
          console.log(error);
        });
      this.$emit("closePopUp", false);
    },
  },
  computed: {
    allowSubmit() {
      return this.selectedMeal !== null && this.selectedDate !== null;
    },
  },
  created() {
    this.recipeToAdd = this.recipe;
  },
  mounted() {
    
  },
  
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 15px 0px 5px 0px;
}
.meal-list, .add-recipe {
  margin: 0px 20px;
}
.add-to-meal-btns {
    display:flex;
    justify-content: space-around;
    align-items: center;
}
</style>