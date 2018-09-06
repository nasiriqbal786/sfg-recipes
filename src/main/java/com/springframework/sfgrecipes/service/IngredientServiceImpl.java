package com.springframework.sfgrecipes.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.springframework.sfgrecipes.commands.IngredientCommand;
import com.springframework.sfgrecipes.converters.IngredientToIngredientCommand;
import com.springframework.sfgrecipes.model.Recipe;
import com.springframework.sfgrecipes.model.repositories.RecipeRepository;

@Service
public class IngredientServiceImpl implements IngredientService {

	private final RecipeRepository recipeRepository;
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	
	private static final Logger logger = LogManager.getLogger(IngredientServiceImpl.class);


	public IngredientServiceImpl(RecipeRepository recipeRepository,
			IngredientToIngredientCommand ingredientToIngredientCommand) {
		this.recipeRepository = recipeRepository;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
	}

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		
		if(!recipeOptional.isPresent()) {
			logger.info("Recipe Not Found For ID: " + recipeId);
		} else {
			Recipe r = recipeOptional.get();
			logger.info("Ingredients Count: " + r.getCategories().size());
			r.getIngredients().forEach(i -> logger.info("Ingredient ID: " + i.getId()));
		}
		
		Recipe recipe = recipeOptional.get();
		Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();
		
		if(!ingredientCommandOptional.isPresent()) {
			logger.info("Ingredient Not Found For ID: " + ingredientId);
		}
		
		return ingredientCommandOptional.get();
	}

}
