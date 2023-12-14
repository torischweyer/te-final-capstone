<template>
  <div class="login-page">
    <div class="home-banner">
      <div class="background-image">
        <div class="white-box"></div>
        <img src="../assets/home-page-background.jpg" alt="Background image" />
      </div>
      <div class="login-message">
        <div id="login">
          <v-card
            class="mx-auto px-6 py-8"
            max-width="344"
          >
            <v-form
              class="login-form"
              v-model="form"
              v-on:submit.prevent="login"
            >
              <h1>Please Sign In</h1>
              <div role="alert" v-if="invalidCredentials">
                Invalid username and password!
              </div>
              <div role="alert" v-if="this.$route.query.registration">
                Thank you for registering, please sign in.
              </div>
              <v-text-field
                v-model="this.user.username"
                :readonly="loading"
                :rules="[required]"
                class="mb-2"
                clearable
                label="Username"
              ></v-text-field>

              <v-text-field
                v-model="this.user.password"
                :readonly="loading"
                :rules="[required]"
                clearable
                type="password"
                label="Password"
                placeholder="Enter your password"
              ></v-text-field>

              <br />
              <v-btn
                :disabled="!form"
                :loading="loading"
                block
                color="success"
                size="large"
                type="submit"
                variant="elevated"
              >
                Sign In
              </v-btn>
              <p class="register-link">
                <router-link v-bind:to="{ name: 'register' }"
                  >Need an account? Sign up.</router-link
                >
              </p>
            </v-form>
          </v-card>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import authService from "../services/AuthService";

export default {
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
      invalidCredentials: false,
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/mealplans");
          }
        })
        .catch((error) => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },
  },
};
</script>

<style scoped>
/* .form-input-group {
  margin-bottom: 1rem;
  width: 20%;
  text-align: center;

}

label {
  margin-right: 0.5rem;
} */
/* .white-box {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(to bottom, rgba(255, 255, 255, 0) 90%, rgba(255, 255, 255, 1) 100%);
  pointer-events: none; 
  z-index: 0;
} */
h1 {
  text-align: center;
  font-size: 1.5rem;
  font-weight: 300;
  margin-bottom: 1.5rem;
  color: #43A047
;
}
#login {
  padding: 10rem;
  display: flex;
  justify-content: center;
}
a {
  color: #388e3c;
  text-decoration: none;
}
a:hover {
  color: #66BB6A
;
}
.register-link {
  text-align: center;
  margin-top: 2rem;
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
.login-message {
  position: absolute;
  top: 56%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
  /* z-index: 1; */
}
</style>