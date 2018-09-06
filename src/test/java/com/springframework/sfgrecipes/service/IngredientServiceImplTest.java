package com.springframework.sfgrecipes.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.springframework.sfgrecipes.commands.IngredientCommand;
import com.springframework.sfgrecipes.converters.IngredientToIngredientCommand;
import com.springframework.sfgrecipes.converters.UnitOfMeasurementToUnitOfMeasurementCommand;
import com.springframework.sfgrecipes.model.Ingredient;
import com.springframework.sfgrecipes.model.Recipe;
import com.springframework.sfgrecipes.model.repositories.RecipeRepository;

public class IngredientServiceImplTest {

	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	
	@Mock
	RecipeRepository recipeRepository;
	
	IngredientService ingredientService;
	
	public IngredientServiceImplTest() {
		this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasurementToUnitOfMeasurementCommand());;
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientCommand);
	}

	@Test
	public void testFindByRecipeIdAndIngredientId() {
		//given
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		Ingredient ingredient = new Ingredient();
		ingredient.setId(1L);
		
		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId(2L);
		
		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(3L);
		
		recipe.addIngredient(ingredient);
		recipe.addIngredient(ingredient1);
		recipe.addIngredient(ingredient2);
		
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		//when
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		
		//then
		IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(3L, 3L);
		
		assertEquals(Long.valueOf(3L), ingredientCommand.getId());
		assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
		verify(recipeRepository, times(1)).findById(anyLong());
		

	}

}
