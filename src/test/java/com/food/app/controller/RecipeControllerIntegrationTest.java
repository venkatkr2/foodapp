package com.food.app.controller;

import com.food.app.entity.Recipe;
import com.food.app.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RecipeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    public void setUp() {
        recipeRepository.deleteAll();
        Recipe recipe = new Recipe();
        recipe.setName("Pasta");
        recipeRepository.save(recipe);
    }

    @Test
    public void testGetAllRecipes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testGetRecipeById() throws Exception {
        Recipe recipe = recipeRepository.findAll().get(0);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipes/" + recipe.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pasta"));
    }

    @Test
    public void testCreateRecipe() throws Exception {
        String newRecipeJson = "{ \"name\": \"Spaghetti\" }";

        mockMvc.perform(MockMvcRequestBuilders.post("/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newRecipeJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Spaghetti"));
    }

    @Test
    public void testUpdateRecipe() throws Exception {
        Recipe recipe = recipeRepository.findAll().get(0);

        String updatedRecipeJson = "{ \"name\": \"Updated Pasta\" }";

        mockMvc.perform(MockMvcRequestBuilders.put("/recipes/" + recipe.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRecipeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Pasta"));

        Optional<Recipe> updatedRecipe = recipeRepository.findById(recipe.getId());
        assert updatedRecipe.isPresent();
        assert "Updated Pasta".equals(updatedRecipe.get().getName());
    }

    @Test
    public void testDeleteRecipe() throws Exception {
        Recipe recipe = recipeRepository.findAll().get(0);

        mockMvc.perform(MockMvcRequestBuilders.delete("/recipes/" + recipe.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Optional<Recipe> deletedRecipe = recipeRepository.findById(recipe.getId());
        assert deletedRecipe.isEmpty();
    }
}
