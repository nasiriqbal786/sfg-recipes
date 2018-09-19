package com.springframework.sfgrecipes.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.springframework.sfgrecipes.commands.IngredientCommand;
import com.springframework.sfgrecipes.converters.IngredientCommandToIngredient;
import com.springframework.sfgrecipes.converters.IngredientToIngredientCommand;
import com.springframework.sfgrecipes.model.Ingredient;
import com.springframework.sfgrecipes.model.Recipe;
import com.springframework.sfgrecipes.model.repositories.IngredientRepository;
import com.springframework.sfgrecipes.model.repositories.RecipeRepository;
import com.springframework.sfgrecipes.model.repositories.UnitOfMeasurementRepository;

@Service
public class IngredientServiceImpl implements IngredientService {

	private final RecipeRepository recipeRepository;
	private final UnitOfMeasurementRepository uomRepository;
	private final IngredientRepository ingredientRepository; 
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	
	private static final Logger logger = LogManager.getLogger(IngredientServiceImpl.class);


	public IngredientServiceImpl(RecipeRepository recipeRepository,
			IngredientToIngredientCommand ingredientToIngredientCommand,
			UnitOfMeasurementRepository uomRepository,
			IngredientCommandToIngredient ingredientCommandToIngredient,
			IngredientRepository ingredientRepository) {
		this.recipeRepository = recipeRepository;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.uomRepository = uomRepository;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.ingredientRepository = ingredientRepository;
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

	@Override
	public IngredientCommand saveIngredientCommand(IngredientCommand command) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
		logger.info("Ingredient ID: " + command.getId());
		logger.info("Recipe ID: " + command.getRecipeId());
		if(!recipeOptional.isPresent()) {
			//throw exception if not found
			logger.error("Recipe Not Found for ID: " + command.getRecipeId());
			return new IngredientCommand();
		} else {
			Recipe recipe = recipeOptional.get();
			
			Optional<Ingredient> ingredientOptional = recipe
					.getIngredients()
					.stream()
					.filter(ingredient -> ingredient.getId().equals(command.getId()))
					.findFirst();
			
			if(ingredientOptional.isPresent()) {
				Ingredient ingredientFound = ingredientOptional.get();
				ingredientFound.setDescription(command.getDescription());
				ingredientFound.setAmount(command.getAmount());
				ingredientFound.setUom(uomRepository
						.findById(command.getUom().getId())
						.orElseThrow(() -> new RuntimeException("UOM not found.")));
			} else {
				Ingredient ingredient = ingredientCommandToIngredient.convert(command);
				ingredient.setRecipe(recipe);
				recipe.addIngredient(ingredient);
			}
			
			Recipe savedRecipe = recipeRepository.save(recipe);
			Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients()
										.stream()
										.filter(ingredient -> ingredient.getId().equals(command.getId()))
										.findFirst();
			
			if(!savedIngredientOptional.isPresent()) {
				savedIngredientOptional = savedRecipe.getIngredients()
						.stream()
						.filter(ingredient -> ingredient.getDescription().equals(command.getDescription()))
						.filter(ingredient -> ingredient.getAmount().equals(command.getAmount()))
						.filter(ingredient -> ingredient.getUom().getId().equals(command.getUom().getId()))
						.findAny();
			}
			
			return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
					
		}
	}

	@Override
	public void deleteById(Long recipeId, Long ingredientId) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		logger.debug("Delete Recipe Ingredient by ID: " + ingredientId + " for Recipe ID: " + recipeId);
		if(recipeOptional.isPresent()) {
			logger.debug("Recipe Found For Recipe ID: " + recipeId);
			Recipe recipe = recipeOptional.get();
			
			Optional<Ingredient> ingredientOptional = recipe.getIngredients()
				.stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.findFirst();
			
			if(ingredientOptional.isPresent()) {
				logger.debug("Found Ingredient for ID: " + ingredientId);
				Ingredient ingredientToDelete = ingredientOptional.get();
				ingredientToDelete.setRecipe(null);
				recipe.getIngredients().remove(ingredientToDelete);
				recipeRepository.save(recipe);
			} else {
				logger.debug("Ingredient not found");
			}
		} else {
			logger.debug("Recipe not found");
		}
		
	}

}
