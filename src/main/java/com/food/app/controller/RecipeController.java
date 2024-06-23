package com.food.app.controller;

import com.food.app.dto.RecipeDto;
import com.food.app.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
@Tag(name = "Recipe Controller", description = "REST api for managing food recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @Operation(summary = "Get a list of available food recipes")
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a food recipe by Id")
    public ResponseEntity<RecipeDto> getRecipeById(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.getRecipeById(id));
    }

    @PostMapping
    @Operation(summary = "Add a new food recipe")
    public ResponseEntity<RecipeDto> createRecipe(@Valid @RequestBody RecipeDto recipeDTO) {
        RecipeDto recipe = recipeService.saveRecipe(recipeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(recipe);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing food recipe")
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable Long id,
                                                  @RequestBody RecipeDto recipeDTO) {
        RecipeDto updatedRecipe = recipeService.updateRecipe(id, recipeDTO);
        return ResponseEntity.ok(updatedRecipe);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a food recipe")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
}
