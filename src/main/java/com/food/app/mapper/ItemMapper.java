package com.food.app.mapper;

import com.food.app.dto.FoodItemDto;
import com.food.app.entity.FoodItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ItemMapper {

    @Mapping(source = "name", target = "name")
    FoodItemDto itemToItemDto(FoodItem foodItem);

    @Mapping(source = "name", target = "name")
    FoodItem itemDtoToItem(FoodItemDto foodItemDto);
}
