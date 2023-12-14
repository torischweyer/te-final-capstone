<template>
    <div>
        <v-card class="mx-auto ma-1 pa-0" max-width="710">
            <v-container class="mx-auto">
                <v-row no-gutters class="justify-space-between">
                    <v-col >
                        <v-card-title id="title">{{ this.meal.name }}</v-card-title>
                    </v-col>
                </v-row>

                <v-row>
                    <v-col class="d-flex justify-space-between flex-wrap">
                        <meal-recipe-card v-for="recipe in this.meal.recipes" v-bind:key="recipe.recipeId"
                            v-bind:recipe="recipe" v-bind:mealId="this.meal.mealId" v-bind:mealPlanId="this.mealPlanId"
                            v-bind:mealPlan="this.mealPlan" @recipeRemoved="recipeWasRemoved" />
                    </v-col>
                </v-row>
            </v-container>
        </v-card>
    </div>
</template>
  
<script>
import MealRecipeCard from './MealRecipeCard.vue';

export default {
    props: ['meal', 'mealPlanId', 'mealPlan'],

    components: {
        MealRecipeCard
    },
    methods: {
        recipeWasRemoved() {
            this.$emit('recipeRemoved');
        }
    },

}
</script>
  
<style scoped>
.mx-auto {
    padding: 10px;
    margin: 10px;
    box-shadow: 1px;
    background-color: #F7FEED;
}

.cards {
    display: inline;
    padding: 10px;
    margin: 10px;

}

#title {
    text-align: center;
    font-size: 30px;
    margin: 10px;
    justify-content: center;

}

a {
    text-decoration: none;
    color: black;
}

.button-wrapper {
    display: flex;
    justify-content: flex-end;
}

.container {
    display: flex;
    flex-direction: row;
    align-items: baseline;
    width: 100%;
}

.add-recipe {
    justify-content: end;
}

</style>