<template>
  <div>

    <div v-bind:class="{ 'capstone-app-logged-in': this.$store.state.token != '' , 'capstone-app-logged-out': this.$store.state.token == '' }">
      <div>
        <router-view />
      </div>
      <div>
        <the-header v-if="this.$store.state.token == ''" />
      </div>
      <div>
        <side-nav-dashboard v-if="this.$store.state.token != ''" />
      </div>
      
    </div>
    <footer>
        <the-footer />
      </footer>
  </div>
</template>

<script>
import TheHeader from "./components/TheHeader.vue";
import TheFooter from "./components/TheFooter.vue";
import SideNavDashboard from "./components/SideNavDashboard.vue";

export default {
  data() {
    return {
      showSideNav: false,
    };
  },
  components: {
    TheHeader,
    TheFooter,
    SideNavDashboard,
  },
};
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=Libre+Franklin:wght@900&family=Montserrat&display=swap");
* {
  font-family: "Libre Franklin", sans-serif;
  font-family: "Montserrat", sans-serif;
}


</style>
