<template>

  <div class="search-box">
    <div id="search-field">
      <input type="text" placeholder="Search by keyword, ingredient, diet, cuisine" v-model="this.search"
        @keyup.enter="sendSearchToView" />
    </div>
  </div>

</template>

<script>
export default {
  data() {
    return {
      search: "",
    };
  },
  methods: {
    sendSearchToView() {
      this.$emit("search", this.search);
      this.search = "";
    },
  },
};
</script>

<style scoped>
.selectedDiet {
  background-color: rgb(148, 245, 137);
}

.search-box {
  display: flexbox;
  text-align: center;
}

h1 {
  margin: 5px;
  font-family: Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;
  font-size: large;
}

input[type="text"] {
  margin: 5px;
  width: 40%;
  padding: 8px;
  border: 1px solid rgb(5, 5, 5);
  border-radius: 5px;
}

.diet-buttons {
  display: flex;
  justify-content: space-between;
  width: 50%;
}

#diet-field {
  margin: 10px;
}

#search-field {
  margin: 20px auto 10px auto;
}
</style>