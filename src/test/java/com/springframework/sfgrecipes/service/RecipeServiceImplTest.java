package com.springframework.sfgrecipes.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.never;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.springframework.sfgrecipes.converters.RecipeCommandToRecipe;
import com.springframework.sfgrecipes.converters.RecipeToRecipeCommand;
import com.springframework.sfgrecipes.model.Recipe;
import com.springframework.sfgrecipes.model.repositories.RecipeRepository;

public class RecipeServiceImplTest {

	RecipeServiceImpl recipeService;
	
	@Mock
	RecipeRepository recipeRepository;
	
	@Mock
	RecipeToRecipeCommand recipeToRecipeCommand;
	
	@Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
	
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
	}

	@Test
	public void testGetRecipes() throws Exception{
		Recipe recipe = new Recipe();
		Set<Recipe> recipesData = new HashSet<Recipe>();
		recipesData.add(recipe);
		
		when(recipeService.getRecipes()).thenReturn(recipesData);
		
		Set<Recipe> recipes = recipeService.getRecipes();
		assertEquals(recipes.size(), 1);
		verify(recipeRepository, times(1)).findAll();
	}
	@Test
	public void testGetRecipeById() throws Exception {
		Recipe recipe = new Recipe();
//		recipe.setId(0L);
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		
		System.out.println("Going to find recipe by id 3L");

		Recipe recipeReturned = recipeService.findById(51111L);
		
		assertNotNull("Null recipe returned", recipeReturned);
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, never()).findAll();
	}
	
}
