<template>
  <div class="recipe-form-container">
    <h2>Add a New Recipe</h2>
    <form @submit.prevent="submitForm" class="recipe-form">
      <div class="form-group">
        <label for="title">Title</label>
        <input type="text" id="title" v-model="recipe.title" placeholder="Enter title" class="form-control"/>
      </div>

      <div class="form-group">
        <label for="ingredients">Ingredients</label>
        <textarea id="ingredients" v-model="recipe.ingredients" placeholder="List ingredients" class="form-control"></textarea>
      </div>

      <div class="form-group">
        <label for="instructions">Instructions</label>
        <textarea id="instructions" v-model="recipe.instructions" placeholder="Describe the steps" class="form-control"></textarea>
      </div>

      <div class="form-group">
        <label for="image">Recipe Image</label>
        <input type="file" id="image" @change="onFileChange" class="form-control-file"/>
      </div>

      <button type="submit" class="btn btn-primary">Add Recipe</button>
    </form>
  </div>
</template>


<script>



export default {

}
</script>

<style>
.recipe-form-container {
  max-width: 600px;
  margin: auto;
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
}

.recipe-form h2 {
  text-align: center;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

.form-control {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-sizing: border-box;
}

.form-control-file {
  border: none;
}

.btn-primary {
  background-color: #007bff;
  border-color: #007bff;
  color: white;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
}

.btn-primary:hover {
  background-color: #0069d9;
  border-color: #0062cc;
}
</style>