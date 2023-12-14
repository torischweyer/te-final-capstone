<template>
  <div>

    <v-card max-width="448" class="mx-auto" color="grey-lighten-3">
      <v-layout>
        <v-app-bar class="public-header" color="green-lighten-1">
          <template v-slot:prepend>
            <router-link v-bind:to="{ name: 'home' }"><v-btn avatar>
                <v-avatar> <img src="../assets/placeholder2.png" alt="Placeholder logo" /></v-avatar>
              </v-btn></router-link>
          </template>

          <v-app-bar-title id="site-name">Chef's Compass</v-app-bar-title>

          <v-spacer></v-spacer>

          <!-- menu list -->
          <v-menu>
            <template v-slot:activator="{ props }">
              <v-app-bar-nav-icon v-bind="props">
              </v-app-bar-nav-icon>
            </template>
            <v-list class="hamburger">
              <v-list-item>
                <router-link v-bind:to="{ name: 'home' }">Home</router-link>
              </v-list-item>
              <v-list-item>
                <router-link v-bind:to="{ name: 'recipes' }">Search Recipes</router-link>
              </v-list-item>
              <v-list-item>
                <router-link v-bind:to="{ name: 'login' }">Log In</router-link>
              </v-list-item>
            </v-list>
          </v-menu>

        </v-app-bar>
      </v-layout>
    </v-card>

  </div>
</template>

<script>
export default {

};
</script>

<style scoped>

#site-name {
  font-weight: 580;

}
.public-header {
  background-color: #66BB6A;
}
.hamburger a {
  color: black;
  text-decoration: none;
}

img {
  width: 100%;
  height: auto;
  margin: 20px;
}

.logo {
  width: 100px;
  height: auto;
  margin: 0 auto;

}

#container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 95%;
  height: 150px;
}

#nav a {
  /* display: block; */
  margin: 5px;
  padding: 6px 20px;
  color: white;
  border-radius: 6px;
  text-align: center;
  text-decoration: none;
}
a:hover {
  text-decoration: none;
color: grey
}

h1 {
  font-size: 4rem;
  /* font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif; */
  margin: 0;
  padding: 0;
  font-display: inline-block;
}

p {
  font-size: 1.5rem;
  /* font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif; */
  margin: 0;
  padding: 0;
  display: inline-block;
}
</style>