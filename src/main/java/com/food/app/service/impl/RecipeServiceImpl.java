package com.food.app.service.impl;

import com.food.app.dto.RecipeDto;
import com.food.app.entity.FoodItem;
import com.food.app.entity.Recipe;
import com.food.app.exception.ResourceNotFoundException;
import com.food.app.repository.FoodItemRepository;
import com.food.app.repository.RecipeRepository;
import com.food.app.service.RecipeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final FoodItemRepository foodItemRepository;
    private final ModelMapper modelMapper;

    public RecipeServiceImpl(RecipeRepository recipeRepository, FoodItemRepository foodItemRepository, ModelMapper modelMapper) {
        this.recipeRepository = recipeRepository;
        this.foodItemRepository = foodItemRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RecipeDto> getAllRecipes() {
        return recipeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RecipeDto getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with id " + id));
    }

    @Override
    public RecipeDto saveRecipe(RecipeDto recipeDTO) {
        Recipe recipe = convertToEntity(recipeDTO);
        if (Objects.nonNull(recipeDTO.getFoodItems()) && !recipeDTO.getFoodItems().isEmpty()) {
            Set<FoodItem> foodItems = recipeDTO.getFoodItems().stream()
                    .map(item -> foodItemRepository.findById(item.getId()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            recipe.setFoodItems(foodItems);
        }
        return convertToDTO(recipeRepository.save(recipe));
    }

    @Override
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public RecipeDto updateRecipe(Long id, RecipeDto recipeDTO) {
        return recipeRepository.findById(id).map(recipe -> {
            recipe.setName(recipeDTO.getName());
            return convertToDTO(recipeRepository.save(recipe));
        }).orElseThrow(() -> new RuntimeException("Recipe not found with id " + id));
    }

    private RecipeDto convertToDTO(Recipe recipe) {
        return modelMapper.map(recipe, RecipeDto.class);
    }

    private Recipe convertToEntity(RecipeDto recipeDTO) {
        return modelMapper.map(recipeDTO, Recipe.class);
    }
}
