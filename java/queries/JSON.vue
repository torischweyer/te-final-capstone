<template></template>

<script>
export default {
  data() {
    return {
      mealPlan: [
        {
          name: "Breakfast",
          id: 1,
          recipes: [
            {
              recipeId: 1,
              id: 666,
              title: "devil cake",
              image:
                "https://realfood.tesco.com/media/images/RFO-1400x919-DevilsFoodCake-5a69df04-114b-491c-838d-5c112ede8a27-0-1400x919.jpg",
              servings: 666,
              readyInMinutes: 666,
              sourceUrl: "url placeholder",
              sourceName: "the devil",
              extendedIngredients: [
                {
                  ingredientId: 1,
                  name: "butter",
                  amount: 2.0,
                  unit: "Tbsp",
                  aisle: "The Butter Aisle",
                },
                {
                  ingredientId: 2,
                  name: "pickles",
                  amount: 666.0,
                  unit: "",
                  aisle: "The Pickle Aisle",
                },
                {
                  ingredientId: 3,
                  name: "borscht",
                  amount: 3.0,
                  unit: "cups",
                  aisle: "The Borscht Aisle",
                },
              ],
              analyzedInstructions: [
                {
                  steps: [
                    {
                      number: 1,
                      step: "call the devil on the phone",
                    },
                    {
                      number: 2,
                      step: "leave him a message since he never answers",
                    },
                    {
                      number: 3,
                      step: "give up",
                    },
                  ],
                },
              ],
              vegan: false,
              vegetarian: false,
              ketogenic: true,
              glutenFree: false,
              dairyFree: false,
              favorite: true,
            },
          ],
        },
        {
          name: "Lunch",
          id: 2,
          recipes: [
            {
              recipeId: 2,
              id: 999,
              title: "god cake",
              image:
                "https://i1.wp.com/cakesandcupcakesmumbai.com/wp-content/uploads/2013/09/krishna-god-birthday-designer-cakes-cupcakes-mumbai-31.jpg?resize=604%2C405",
              servings: 999,
              readyInMinutes: 999,
              sourceUrl: "url placeholder",
              sourceName: "god",
              extendedIngredients: [
                {
                  ingredientId: 1,
                  name: "butter",
                  amount: 3.0,
                  unit: "Tbsp",
                  aisle: "The Butter Aisle",
                },
              ],
              analyzedInstructions: [
                {
                  steps: [
                    {
                      number: 1,
                      step: "call god on the phone",
                    },
                    {
                      number: 2,
                      step: "ask him how the new dog is doing",
                    },
                    {
                      number: 3,
                      step: "procure god cake instructions. easy",
                    },
                  ],
                },
              ],
              vegan: true,
              vegetarian: false,
              ketogenic: false,
              glutenFree: false,
              dairyFree: true,
              favorite: true,
            },
          ],
        },
        {
          name: "Dinner",
          id: 3,
          recipes: [
            {
              recipeId: 1,
              id: 666,
              title: "devil cake",
              image:
                "https://realfood.tesco.com/media/images/RFO-1400x919-DevilsFoodCake-5a69df04-114b-491c-838d-5c112ede8a27-0-1400x919.jpg",
              servings: 666,
              readyInMinutes: 666,
              sourceUrl: "url placeholder",
              sourceName: "the devil",
              extendedIngredients: [
                {
                  ingredientId: 1,
                  name: "butter",
                  amount: 2.0,
                  unit: "Tbsp",
                  aisle: "The Butter Aisle",
                },
                {
                  ingredientId: 2,
                  name: "pickles",
                  amount: 666.0,
                  unit: "",
                  aisle: "The Pickle Aisle",
                },
                {
                  ingredientId: 3,
                  name: "borscht",
                  amount: 3.0,
                  unit: "cups",
                  aisle: "The Borscht Aisle",
                },
              ],
              analyzedInstructions: [
                {
                  steps: [
                    {
                      number: 1,
                      step: "call the devil on the phone",
                    },
                    {
                      number: 2,
                      step: "leave him a message since he never answers",
                    },
                    {
                      number: 3,
                      step: "give up",
                    },
                  ],
                },
              ],
              vegan: false,
              vegetarian: false,
              ketogenic: true,
              glutenFree: false,
              dairyFree: false,
              favorite: true,
            },
            {
              recipeId: 2,
              id: 999,
              title: "god cake",
              image:
                "https://i1.wp.com/cakesandcupcakesmumbai.com/wp-content/uploads/2013/09/krishna-god-birthday-designer-cakes-cupcakes-mumbai-31.jpg?resize=604%2C405",
              servings: 999,
              readyInMinutes: 999,
              sourceUrl: "url placeholder",
              sourceName: "god",
              extendedIngredients: [
                {
                  ingredientId: 1,
                  name: "butter",
                  amount: 3.0,
                  unit: "Tbsp",
                  aisle: "The Butter Aisle",
                },
              ],
              analyzedInstructions: [
                {
                  steps: [
                    {
                      number: 1,
                      step: "call god on the phone",
                    },
                    {
                      number: 2,
                      step: "ask him how the new dog is doing",
                    },
                    {
                      number: 3,
                      step: "procure god cake instructions. easy",
                    },
                  ],
                },
              ],
              vegan: true,
              vegetarian: false,
              ketogenic: false,
              glutenFree: false,
              dairyFree: true,
              favorite: true,
            },
          ],
        },
        {
          name: "Snack",
          id: 4,
          recipes: [
            {
              recipeId: 3,
              id: 111,
              title: "elsa",
              image:
                "https://cdn11.bigcommerce.com/s-wumqzdp3rw/images/stencil/1280x1280/products/377/965/GUY_0002-Edit_2k75__84979.1623607467.jpg?c=1",
              servings: 111,
              readyInMinutes: 111,
              sourceUrl: "url placeholder",
              sourceName: "elsa",
              extendedIngredients: [
                {
                  ingredientId: 1,
                  name: "butter",
                  amount: 4.0,
                  unit: "Tbsp",
                  aisle: "The Butter Aisle",
                },
                {
                  ingredientId: 3,
                  name: "borscht",
                  amount: 1.5,
                  unit: "cups",
                  aisle: "The Borscht Aisle",
                },
              ],
              analyzedInstructions: [
                {
                  steps: [
                    {
                      number: 1,
                      step: "go to whole foods",
                    },
                    {
                      number: 2,
                      step: "kill yourself",
                    },
                    {
                      number: 3,
                      step: "easy",
                    },
                  ],
                },
              ],
              vegan: false,
              vegetarian: false,
              ketogenic: false,
              glutenFree: true,
              dairyFree: false,
              favorite: false,
            },
          ],
        },
        {
          name: "Fun Meal",
          id: 5,
          recipes: [],
        },
      ],
    };
  },
};
</script>

<style>
</style>
