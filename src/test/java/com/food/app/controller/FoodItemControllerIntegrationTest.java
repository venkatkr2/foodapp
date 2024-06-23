package com.food.app.controller;

import com.food.app.entity.FoodItem;
import com.food.app.entity.Recipe;
import com.food.app.repository.FoodItemRepository;
import com.food.app.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
//@SpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FoodItemControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    public void setUp() {
        foodItemRepository.deleteAll();
        recipeRepository.deleteAll();

        Recipe recipe = new Recipe();
        recipe.setName("Pasta");
        recipeRepository.save(recipe);

        FoodItem foodItem = new FoodItem();
        foodItem.setName("Tomato");
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(recipe);
        foodItem.setRecipes(recipes);
        foodItemRepository.save(foodItem);
    }

    @Test
    public void testGetAllFoodItems() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/food-items")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testGetFoodItemById() throws Exception {
        FoodItem foodItem = foodItemRepository.findAll().get(0);

        mockMvc.perform(MockMvcRequestBuilders.get("/food-items/" + foodItem.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tomato"));
    }

    @Test
    public void testAddRecipeToFoodItem() throws Exception {
        FoodItem foodItem = foodItemRepository.findAll().get(0);
        Recipe recipe = recipeRepository.findAll().get(0);

        mockMvc.perform(MockMvcRequestBuilders.post("/food-items/" + foodItem.getId() + "/recipes/" + recipe.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipes", hasSize(1)));
    }

}
