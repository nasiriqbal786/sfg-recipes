package com.springframework.sfgrecipes.service;

import com.springframework.sfgrecipes.commands.IngredientCommand;

public interface IngredientService {

	IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
	IngredientCommand saveIngredientCommand(IngredientCommand command);
	void deleteById(Long receiptId, Long ingredientId);
}
