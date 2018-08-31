package com.springframework.sfgrecipes.service;

import java.util.Set;

import com.springframework.sfgrecipes.commands.RecipeCommand;
import com.springframework.sfgrecipes.model.Recipe;

public interface RecipeService {

	Set<Recipe> getRecipes();
	Recipe findById(Long id);
	RecipeCommand saveRecipeCommand(RecipeCommand recipe);
}
