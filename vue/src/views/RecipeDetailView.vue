<template>
  <div class="container">
    <div class="recipe-detail" v-bind:class="{ 'logged-out': this.$store.state.token == '' }" >
      <recipe-detail v-bind:recipe="this.recipe" @openPopUp="openAddRecipePopUp"/>

      <v-col>
        <v-dialog v-model="enablePopUp" transition="dialog-top-transition" width="auto" >
          <template v-slot:default="{ isActive }">
            <v-card>
              <v-card-text>
                <add-recipe-to-meal v-bind:recipe="this.recipe" @closePopUp="closePopUp" />
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
import RecipeDetail from "../components/RecipeDetail.vue";
import SpoonService from "../services/SpoonService.js";
import AddRecipeToMeal from "../components/AddRecipeToMeal.vue";


export default {
  components: {
    RecipeDetail,
    AddRecipeToMeal
  },
  data() {
    return {
      recipe: {},
      enablePopUp: false,
    };
  },
  methods: {
    openAddRecipePopUp() {
      this.enablePopUp = true;
    },
    closePopUp() {
      this.enablePopUp = false;
    },
  },
  created() {
    let id = this.$route.params.id;
    SpoonService.retrieveRecipeByID(id)
    .then((response) => {
      this.recipe = response.data;
    })
    .catch((error) => {
        console.log(error);
      });
  },
};
</script>

<style scoped>

.logged-out {
  margin: 100px 100px;
}
.container {
  position: relative;
}

recipe-detail {
    display: flex;
    justify-content: center;
    align-items: center;
}

.recipe-detail {
  position: relative;
  margin: 50px 100px;
}

add-recipe-to-meal {
  position: absolute;
  top: 0;
  left: 0;
}
</style>