package com.springframework.sfgrecipes.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.springframework.sfgrecipes.commands.RecipeCommand;
import com.springframework.sfgrecipes.converters.RecipeCommandToRecipe;
import com.springframework.sfgrecipes.converters.RecipeToRecipeCommand;
import com.springframework.sfgrecipes.model.Recipe;
import com.springframework.sfgrecipes.model.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	private final RecipeToRecipeCommand recipeToRecipeCommand;
	
	public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
			RecipeToRecipeCommand recipeToRecipeCommand) {
		this.recipeRepository = recipeRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
	public Set<Recipe> getRecipes() {
		Set<Recipe> setRecipe = new HashSet<>();
		recipeRepository.findAll().iterator().forEachRemaining(setRecipe::add);;
		return setRecipe;
	}
	@Override
	public Recipe findById(Long id) {
		log.info("Recipe ID: " + id);
		Optional<Recipe> recipeOptional = recipeRepository.findById(id);
		if(!recipeOptional.isPresent()) {
			throw new RuntimeException("Recipe Not Found");
		}
		
		return recipeOptional.get();
	}

	@Override
	public RecipeCommand saveRecipeCommand(RecipeCommand recipe) {
		Recipe detachedRecipe = recipeCommandToRecipe.convert(recipe);
		
		Recipe savedRecipe = recipeRepository.save(detachedRecipe);
		log.debug("Saved Recipe ID: " + savedRecipe.getId());
		return recipeToRecipeCommand.convert(savedRecipe);
	}

}
