package com.springframework.sfgrecipes.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.springframework.sfgrecipes.model.Recipe;
import com.springframework.sfgrecipes.model.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;
	public RecipeServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}
	@Override
	public Set<Recipe> getRecipes() {
		Set<Recipe> setRecipe = new HashSet<>();
		recipeRepository.findAll().iterator().forEachRemaining(setRecipe::add);;
		return setRecipe;
	}

}
