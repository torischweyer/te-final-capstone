import { createRouter as createRouter, createWebHistory } from 'vue-router'
import { useStore } from 'vuex'

// Import components
import HomeView from '../views/HomeView.vue';
import LoginView from '../views/LoginView.vue';
import LogoutView from '../views/LogoutView.vue';
import MealPlanView from '../views/MealPlanView.vue';
import MyRecipeView from '../views/MyRecipeView.vue';
import RecipeView from '../views/RecipeView.vue';
import RecipeDetailView from '../views/RecipeDetailView.vue';
import RegisterView from '../views/RegisterView.vue';
import MyRecipeDetailView from '../views/MyRecipeDetailView.vue';
import AddRecipeView from '../views/AddRecipeView.vue';
import VoiceToTextView from '../views/VoiceToTextView.vue';



/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */
const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: '/recipes',
    name: 'recipes',
    component: RecipeView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: '/myrecipes',
    name: 'my-recipes',
    component: MyRecipeView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: '/recipes/:id',
    name: 'recipe-detail',
    component: RecipeDetailView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: '/myrecipes/:id',
    name: 'my-recipe-detail',
    component: MyRecipeDetailView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/mealplans",
    name: "mealplans",
    component: MealPlanView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/login",
    name: "login",
    component: LoginView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/logout",
    name: "logout",
    component: LogoutView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/register",
    name: "register",
    component: RegisterView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: '/addrecipe',
    name: 'add-recipe',
    component: AddRecipeView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: '/vtt',
    name: 'vtt',
    componenet: VoiceToTextView,
    meta: {
      requiresAuth: false
    }
  }
];

// Create the router
const router = createRouter({
  history: createWebHistory(),
  routes: routes
});

router.beforeEach((to) => {

  // Get the Vuex store
  const store = useStore();

  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    return { name: "login" };
  }
  // Otherwise, do nothing and they'll go to their next destination
});

export default router;
