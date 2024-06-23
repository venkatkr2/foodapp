package com.food.app.controller;

import com.food.app.dto.FoodItemDto;
import com.food.app.service.FoodItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/food-items")
@Tag(name = "Food Item Controller", description = "REST api for managing food items")
public class FoodItemController {

    @Autowired
    private FoodItemService foodItemService;

    @GetMapping
    @Operation(summary = "Get all food items with paging and sorting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
    })
    public ResponseEntity<Page<FoodItemDto>> getAllFoodItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Page<FoodItemDto> foodItems = foodItemService.getAllFoodItems(page, size, sortBy, sortOrder);
        return ResponseEntity.ok(foodItems);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a food item by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved food item"),
            @ApiResponse(responseCode = "404", description = "Food item not found")
    })
    public ResponseEntity<FoodItemDto> getFoodItemById(@PathVariable Long id) {
        return foodItemService.getFoodItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Add a new food item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created food item"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<FoodItemDto> createFoodItem(@Valid @RequestBody FoodItemDto foodItemDto) {

        FoodItemDto foodItem = foodItemService.saveFoodItem(foodItemDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(foodItem.getId()).toUri();
        return ResponseEntity.created(location).body(foodItem);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing food item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated food item"),
            @ApiResponse(responseCode = "404", description = "Food item not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<FoodItemDto> updateFoodItem(@PathVariable Long id,
                                                      @RequestBody FoodItemDto foodItemDTO) {
        return ResponseEntity.ok(foodItemService.updateFoodItem(id, foodItemDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a food item")
    public ResponseEntity<Void> deleteFoodItem(@PathVariable Long id) {
        foodItemService.deleteFoodItem(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{foodItemId}/recipes/{recipeId}")
    @Operation(summary = "Add a recipe to a food item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added recipe to food item"),
            @ApiResponse(responseCode = "404", description = "Food item or recipe not found")
    })
    public ResponseEntity<FoodItemDto> addRecipeToFoodItem(@PathVariable Long foodItemId, @PathVariable Long recipeId) {
        FoodItemDto foodItem = foodItemService.addRecipeToFoodItem(foodItemId, recipeId);
        return ResponseEntity.ok(foodItem);
    }
}
