package com.springframework.sfgrecipes.service;

import com.springframework.sfgrecipes.commands.IngredientCommand;

public interface IngredientService {

	IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
}
