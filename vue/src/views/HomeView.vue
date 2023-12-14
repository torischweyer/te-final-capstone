<template>
  <div class="home">
    
    <div class="home-banner">
      <div class="background-image">
        <div class="white-box"></div>
        <img src="../assets/home-page-background.jpg" alt="Background image" />
      </div>
      <div class="welcome-message">
        <h1 id="site-name">Chef's Compass</h1>
        <h2 id="tagline">Pointing you towards a home-cooked meal</h2><br><br>
        <router-link v-bind:to="{ name: 'recipes' }">
          
          <v-btn class="call-to-action" variant="tonal" size="x-large">Find your next recipe!</v-btn>
        </router-link>

        <head>
          <link rel="stylesheet" type="text/css" href="styles.css" />
        </head>
      </div>
    </div>
    <div class="all-screenshots">
    <h2 class="screenshot-title">Search for New Recipes</h2>
    <div class="screenshot">
      <img src="../assets/screenshot-search-recipes.png" alt="screenshot of the search recipe page">
    </div>
    <h2 class="screenshot-title">View Recipes</h2>
    <div class="screenshot">
      <img src="../assets/screenshot-recipe-detail.png" alt="screenshot of the recipe detail page">
    </div>
    <h2 class="screenshot-title">Save Recipes for Later</h2>
    <div class="screenshot">
      <img src="../assets/screenshot-saved-recipes.png" alt="screenshot of the saved recipes page">
    </div>
    <h2 class="screenshot-title">Design Daily Meal Plans</h2>
    <div class="screenshot">
      <img src="../assets/screenshot-meal-plan.png" alt="screenshot of the meal plan page">
    </div>
  </div>
  </div>
</template> 

<script>
export default {};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Cinzel:wght@600&family=Libre+Franklin:wght@900&family=Montserrat&display=swap");
.all-screenshots{
  margin-top: 100px;
  margin-bottom: 100px;
  align-content: center;
  text-align: center;
}
.screenshot-title {
  text-align: center;
  font-size: 36px;
  color: black;
  text-shadow: 2px 2px 4px #c1c1c1;
  margin-bottom: 20px;
  margin-top: 100px
}
.screenshot {
  text-align: center;
  width: 75%;
  margin: 0 auto;
}
.screenshot img {
  width: 100%;
  height: auto;
  border-radius: 10px;
  box-shadow: 2px 2px 4px #c1c1c1;
}
.white-box {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(to bottom, rgba(255, 255, 255, 0) 90%, rgba(255, 255, 255, 1) 100%);
  pointer-events: none; /* Allows interaction with elements behind the white box */
  z-index: 0;
}
#site-name {
  font-family: "Montserrat", sans-serif;
  font-size: 72px;
  color: black;
  text-shadow: 2px 2px 4px #c1c1c1;
  margin-bottom: 60px;
  margin-top: 20px
}

#tagline {
  font-family: "Montserrat", "Libre Franklin", sans-serif;
  font-size: 25px;
  color: rgb(121, 121, 121);
}

.call-to-action {
  background-color: #66BB6A;
  border: none;
  color: white;
  font-size: medium;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  margin: 0px auto;
  cursor: pointer;
}

.call-to-action:hover {
  background-color: #81C784;

}

.background-image {
  position: relative;
  width: 110%;
  height: auto;
  overflow: hidden;
  text-align: center;
  top: -70px;
  left: -5%;
  right: -5%  ;
}

.background-image img {
  width: 100%;
  /* height: auto; */
  height: 100%;
  opacity: 0.95;
  position: relative;
  object-fit: cover;
  /* left: -4%;
  top: -100px; */
  z-index: -1;
}

.welcome-message {
  position: absolute;
  top: 56%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
  /* z-index: 1; */
}
</style>