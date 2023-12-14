<template>
    <div>
        <!--clickable side nav bar-->
        <v-card v-click-outside="onClickOutside">
            <v-layout>
                <v-navigation-drawer v-model="drawer" :rail="rail" permanent @click="rail = !rail" color="green-lighten-1">
                    <v-list nav>
                        <v-list-item prepend-avatar='src\assets\placeholder2.png' title="Chef's Compass" nav />
                    </v-list>
                    <v-divider></v-divider>

                    <v-list density="compact" nav>
                        <router-link v-bind:to="{ name: 'home' }">
                            <v-list-item prepend-icon=$home title="Home" base-color="#ffffff"></v-list-item>
                        </router-link>
                        <router-link v-bind:to="{ name: 'recipes' }">
                            <v-list-item prepend-icon=$search title="Search Recipes" base-color="#ffffff"></v-list-item>
                        </router-link>
                        <router-link v-bind:to="{ name: 'my-recipes' }">
                            <v-list-item prepend-icon=$recipes title="My Recipes" base-color="#ffffff"></v-list-item>
                        </router-link>
                        <router-link v-bind:to="{ name: 'mealplans' }">
                            <v-list-item prepend-icon=$mealPlan title="My Meal Plan" base-color="#ffffff"></v-list-item>
                        </router-link>

                        <v-list-item prepend-icon=$heart title="My Favorites" base-color="#ffffff"></v-list-item>
                        
                        <v-list-item prepend-icon=$edit title="Add Recipe" base-color="#ffffff"></v-list-item>

                        <router-link v-bind:to="{ name: 'logout' }">
                            <v-list-item prepend-icon=$logout title="Log Out" base-color="#ffffff"></v-list-item>
                        </router-link>

                    </v-list>
                </v-navigation-drawer>
            </v-layout>
        </v-card>
    </div>
</template>

<script>
export default {
    data() {
        return {
            drawer: true,
            rail: true,
        }
    },
    methods: {
        onClickOutside() {
            this.rail = true
        },
    },
}
</script>


<style scoped>
a {
    color: #ffffff;
    text-decoration: none;
}
</style>