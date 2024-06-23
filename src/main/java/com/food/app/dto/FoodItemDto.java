package com.food.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class FoodItemDto {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 255, message = "Name cannot be longer than 255 characters")
    private String name;

    @JsonIgnoreProperties("foodItems")
    private Set<RecipeDto> recipes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<RecipeDto> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<RecipeDto> recipes) {
        this.recipes = recipes;
    }
}
