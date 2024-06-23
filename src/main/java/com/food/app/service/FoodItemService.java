package com.food.app.service;

import com.food.app.dto.FoodItemDto;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface FoodItemService {

    Page<FoodItemDto> getAllFoodItems(int page, int size, String sortBy, String sortOrder);

    Optional<FoodItemDto> getFoodItemById(Long id);

    FoodItemDto saveFoodItem(FoodItemDto foodItemDTO);

    void deleteFoodItem(Long id);

    FoodItemDto updateFoodItem(Long id, FoodItemDto itemDto);

    FoodItemDto addRecipeToFoodItem(Long foodItemId, Long recipeId);
}
