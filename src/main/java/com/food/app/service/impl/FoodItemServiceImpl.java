package com.food.app.service.impl;

import com.food.app.dto.FoodItemDto;
import com.food.app.dto.RecipeDto;
import com.food.app.entity.FoodItem;
import com.food.app.exception.ResourceNotFoundException;
import com.food.app.repository.FoodItemRepository;
import com.food.app.service.FoodItemService;
import com.food.app.service.RecipeService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class FoodItemServiceImpl implements FoodItemService {

    private static final Logger logger = LoggerFactory.getLogger(FoodItemServiceImpl.class);

    private final FoodItemRepository foodItemRepository;

    private final ModelMapper modelMapper;

    private final RecipeService recipeService;

    public FoodItemServiceImpl(FoodItemRepository foodItemRepository,
                               ModelMapper modelMapper,
                               RecipeService recipeService) {
        this.foodItemRepository = foodItemRepository;
        this.modelMapper = modelMapper;
        this.recipeService = recipeService;
    }

    @Override
    public Page<FoodItemDto> getAllFoodItems(int page, int size, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<FoodItem> foodItemsPage = foodItemRepository.findAll(pageable);
        return foodItemsPage.map(this::convertToDTO);
    }

    @Override
    public Optional<FoodItemDto> getFoodItemById(Long id) {
        logger.info("Get FoodItem with id : {}", id);
        return foodItemRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public FoodItemDto saveFoodItem(FoodItemDto foodItemDTO) {
        logger.info("Saving FoodItem: {}", foodItemDTO);
        FoodItem foodItem = convertToEntity(foodItemDTO);
        return convertToDTO(foodItemRepository.save(foodItem));
    }

    @Override
    public void deleteFoodItem(Long id) {
        logger.info("Deleting FoodItem with id: {}", id);
        if (!foodItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("FoodItem not found with id " + id);
        }
        foodItemRepository.deleteById(id);
    }

    @Override
    public FoodItemDto updateFoodItem(Long id, FoodItemDto itemDto) {
        logger.info("Updating FoodItem with id: {}", id);
        Optional<FoodItem> foodItemOptional = foodItemRepository.findById(id);
        return foodItemOptional.map(item -> {
            item.setName(itemDto.getName());
            return convertToDTO(foodItemRepository.save(item));
        }).orElseThrow(() -> new ResourceNotFoundException("FoodItem not found with id " + id));
    }

    @Override
    public FoodItemDto addRecipeToFoodItem(Long foodItemId, Long recipeId) {
        Optional<FoodItemDto> foodItemOptional = this.getFoodItemById(foodItemId);
        RecipeDto recipe = recipeService.getRecipeById(recipeId);
        return foodItemOptional.map(itemDto -> {
            itemDto.setRecipes(Set.of(recipe));
            return this.saveFoodItem(itemDto);
        }).orElseThrow(() -> new ResourceNotFoundException("FoodItem not found with id " + foodItemId));
    }

    private FoodItemDto convertToDTO(FoodItem foodItem) {
        return modelMapper.map(foodItem, FoodItemDto.class);
    }

    private FoodItem convertToEntity(FoodItemDto foodItemDto) {
        return modelMapper.map(foodItemDto, FoodItem.class);
    }
}
