import { createApp } from 'vue'
import CapstoneApp from './App.vue'
import { createStore } from './store'
import router from './router'
import axios from 'axios'

// vuetify 
import { createVuetify } from 'vuetify'
import 'vuetify/styles'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives' 
import { aliases, mdi } from 'vuetify/iconsets/mdi-svg'
import { mdiHeart, mdiHeartOutline, mdiPlusCircle, mdiPlusCircleOutline, mdiCheckCircle,
         mdiLeafCircle, mdiCarrot, mdiCowOff, mdiBarleyOff, mdiSprout, mdiPlus,
         mdiChefHat, mdiCalendarRange, mdiCalendarToday, mdiPencil, mdiTrashCanOutline,
         mdiChevronLeft, mdiChevronRight, mdiHome, mdiDoorClosed, mdiMagnify, mdiBookmarkPlusOutline,
         mdiFacebook, mdiGithub, mdiInstagram, mdiLinkedin, mdiYoutube } from '@mdi/js'


const vuetify = createVuetify({
  components, directives, icons: {
    defaultSet: 'mdi',
    aliases: {
      ...aliases,
      heart: mdiHeart,
      heartOutline: mdiHeartOutline,
      plusCircle: mdiPlusCircle,
      plusCircleOutline: mdiPlusCircleOutline,
      checkCircle: mdiCheckCircle,
      vegan: mdiLeafCircle,
      vegetarian: mdiCarrot,
      dairyFree: mdiCowOff,
      glutenFree: mdiBarleyOff,
      ketogenic: mdiSprout,
      plus: mdiPlus,
      recipes: mdiChefHat,
      mealPlan: mdiCalendarRange,
      today: mdiCalendarToday,
      edit: mdiPencil,
      delete: mdiTrashCanOutline,
      chevronLeft: mdiChevronLeft,
      chevronRight: mdiChevronRight,
      home: mdiHome,
      logout: mdiDoorClosed,
      search: mdiMagnify,
      addToLibrary: mdiBookmarkPlusOutline,
      facebook: mdiFacebook,
      github: mdiGithub,
      instagram: mdiInstagram,
      linkedin: mdiLinkedin,
      youtube: mdiYoutube,

    },
    sets: {
      mdi,
    },
  },
})

/* sets the base url for server API communication with axios */
axios.defaults.baseURL = import.meta.env.VITE_REMOTE_API;

/*
 * The authorization header is set for axios when you login but what happens when 
 * you come back or the page is refreshed. When that happens you need to check 
 * for the token in local storage and if it exists you should set the header 
 * so that it will be attached to each request.
 */
let currentToken = localStorage.getItem('token');
let currentUser = JSON.parse(localStorage.getItem('user'));

if (currentToken) {
  // Set token axios requests
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

// Create the Vuex store passing in the stored credentials
const store = createStore(currentToken, currentUser);

const app = createApp(CapstoneApp);
app.use(store);
app.use(router);
app.use(vuetify);
app.mount('#app');