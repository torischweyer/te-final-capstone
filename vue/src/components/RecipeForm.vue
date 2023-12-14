<template>
  <div>
    <form @submit.prevent="submitForm">
      <input type="text" v-model="recipe.title" placeholder="Title" />
      <textarea v-model="recipe.ingredients" placeholder="Ingredients"></textarea>
      <textarea v-model="recipe.instructions" placeholder="Instructions"></textarea>
      <input type="file" @change="onFileChange" />
      <button type="submit">Add Recipe</button>
    </form>
  </div>
</template>
  
<script>
export default {
  data() {
    return {
      recipe: {
        title: '',
        ingredients: '',
        instructions: '',
        image: null
      }
    };
  },
  methods: {
    onFileChange(e) {
      this.recipe.image = e.target.files[0];
    },
    async submitForm() {
      const formData = new FormData();
      formData.append('title', this.recipe.title);
      formData.append('ingredients', this.recipe.ingredients);
      formData.append('instructions', this.recipe.instructions);
      formData.append('image', this.recipe.image);

      try {
        await this.axios.post('/addrecipe', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });

      } catch (error) {
        console.log(error);
      }
    }
  }
};
</script>

<style></style>