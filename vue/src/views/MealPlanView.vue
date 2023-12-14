<template>
  <div class="main">

    <div class="meal-nav">
      <v-btn @click="previousDay">Previous</v-btn>

      <v-btn color="green-lighten-1" dark v-on:click.prevent="dialog = !dialog"> {{ date }} </v-btn>
      <v-dialog v-model="dialog" width="auto">
        <v-date-picker color="green-lighten-1" elevation="2" v-model="this.selectDate"
          @click.stop="getMealBySelectedDate"></v-date-picker>
      </v-dialog>

      <v-btn @click="nextDay">Next</v-btn>
    </div>

    <div class="corner-button">
      <v-menu transition="slide-x-transition">
        <template v-slot:activator="{ props }">
          <v-btn icon="$plus" color="green-lighten-1" v-bind="props" class="justify-right">
          </v-btn>
        </template>
        <v-list>
          <v-list-item>
            <router-link :to="{ name: 'recipes' }">
              <v-list-item-title> Search Recipes </v-list-item-title>
            </router-link>
          </v-list-item>
          <v-list-item>
            <router-link :to="{ name: 'my-recipes' }">
              <v-list-item-title> My Recipes </v-list-item-title>
            </router-link>
          </v-list-item>
        </v-list>
      </v-menu>
    </div>

    <div class="meal-plans">
            <the-meal v-bind:meal="this.mealPlan.breakfast" v-bind:mealPlanId="this.mealPlan.mealPlanId"
              v-bind:mealPlan="this.mealPlan" @recipeRemoved="refreshMealPlan" />

            <the-meal v-bind:meal="this.mealPlan.lunch" v-bind:mealPlanId="this.mealPlan.mealPlanId"
              v-bind:mealPlan="this.mealPlan" @recipeRemoved="refreshMealPlan" />

          <the-meal v-bind:meal="this.mealPlan.dinner" v-bind:mealPlanId="this.mealPlan.mealPlanId"
            v-bind:mealPlan="this.mealPlan" @recipeRemoved="refreshMealPlan" />

          <the-meal v-bind:meal="this.mealPlan.snack" v-bind:mealPlanId="this.mealPlan.mealPlanId"
            v-bind:mealPlan="this.mealPlan" @recipeRemoved="refreshMealPlan" />

          <the-meal v-bind:meal="this.mealPlan.fun" v-bind:mealPlanId="this.mealPlan.mealPlanId"
            v-bind:mealPlan="this.mealPlan" @recipeRemoved="refreshMealPlan" />
    </div>

  </div>
</template>
  
<script>
import MealPlanService from "../services/MealPlanService.js";
import TheMeal from "../components/TheMeal.vue";

export default {
  data() {
    return {
      date: new Date().toLocaleDateString("en-us", {
        weekday: "long",
        year: "numeric",
        month: "long",
        day: "numeric",
      }),
      selectDate: null,
      mealPlan: [],
      menu: false,
      showCalendar: false,
      dialog: false,
    };
  },
  components: {
    TheMeal
  },
  methods: {
    getMealByDate(date) {
      MealPlanService.retrieveMealPlanByDate(date)
        .then((response) => {
          this.mealPlan = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    nextDay() {
      const currentDate = new Date(this.date);
      const nextDate = new Date(currentDate);
      nextDate.setDate(nextDate.getDate() + 1);
      this.date = nextDate.toLocaleDateString("en-us", {
        weekday: "long",
        year: "numeric",
        month: "long",
        day: "numeric",
      });
      this.getMealByDate(nextDate.toISOString().split("T")[0]);
    },
    previousDay() {
      const currentDate = new Date(this.date);
      const previousDate = new Date(currentDate);
      previousDate.setDate(previousDate.getDate() - 1);
      this.date = previousDate.toLocaleDateString("en-us", {
        weekday: "long",
        year: "numeric",
        month: "long",
        day: "numeric",
      });
      this.getMealByDate(previousDate.toISOString().split("T")[0]);
    },
    getMealBySelectedDate() {
      const selectedDate = new Date(this.selectDate);
      this.date = selectedDate.toLocaleDateString("en-us", {
        weekday: "long",
        year: "numeric",
        month: "long",
        day: "numeric",
      });
      this.getMealByDate(selectedDate.toISOString().split("T")[0]);
      this.showCalendar = false;
    },
    refreshMealPlan() {
      const formattedDate = new Date(this.date).toISOString().split("T")[0];
      this.getMealByDate(formattedDate);
    },
  },
  created() {
    const today = new Date(this.date).toISOString().split("T")[0]; // get today's date in YYYY-MM-DD format
    this.getMealByDate(today);
  },
};
</script>


<style scoped>
h1 {
  text-align: center;
  font-size: 30px;
  margin: 10px;
  justify-content: center;
}

.meal-plan {
  margin-left: 70px
}

.meal-nav {
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  margin: 30px;
}

.meal-plans {

  transition: margin-left 0.3s ease-in-out;
  padding: 16px;
}

.main {
  margin: 0px 50px 0px 100px;
}

.corner-button {
  position: fixed;
  top: 25px;
  right: 80px;
  z-index: 1;
}

a {
  text-decoration: none;
  color: black
}

a:hover {
  color: grey
}</style>