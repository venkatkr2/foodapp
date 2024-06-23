package com.food.app.service;

import com.food.app.dto.RecipeDto;

import java.util.List;

public interface RecipeService {

    List<RecipeDto> getAllRecipes();

    RecipeDto getRecipeById(Long id);

    RecipeDto saveRecipe(RecipeDto recipeDTO);

    void deleteRecipe(Long id);

    RecipeDto updateRecipe(Long id, RecipeDto recipeDTO);
}
