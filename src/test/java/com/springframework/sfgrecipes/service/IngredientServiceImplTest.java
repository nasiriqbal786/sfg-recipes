package com.springframework.sfgrecipes.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.springframework.sfgrecipes.commands.IngredientCommand;
import com.springframework.sfgrecipes.commands.UnitOfMeasurementCommand;
import com.springframework.sfgrecipes.converters.IngredientCommandToIngredient;
import com.springframework.sfgrecipes.converters.IngredientToIngredientCommand;
import com.springframework.sfgrecipes.converters.UnitOfMeasurementCommandToUnitOfMeasurement;
import com.springframework.sfgrecipes.converters.UnitOfMeasurementToUnitOfMeasurementCommand;
import com.springframework.sfgrecipes.model.Ingredient;
import com.springframework.sfgrecipes.model.Recipe;
import com.springframework.sfgrecipes.model.UnitOfMeasurement;
import com.springframework.sfgrecipes.model.repositories.IngredientRepository;
import com.springframework.sfgrecipes.model.repositories.RecipeRepository;
import com.springframework.sfgrecipes.model.repositories.UnitOfMeasurementRepository;

public class IngredientServiceImplTest {

	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	
	@Mock
	RecipeRepository recipeRepository;
	
	@Mock
	UnitOfMeasurementRepository uomRepository;
	
	@Mock
	IngredientRepository ingredientRepository;
	
	IngredientService ingredientService;
	
	public IngredientServiceImplTest() {
		this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasurementToUnitOfMeasurementCommand());
		this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasurementCommandToUnitOfMeasurement());
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientCommand, uomRepository, ingredientCommandToIngredient, ingredientRepository);
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
	
	@Test
	public void testSaveIngredientCommand() throws Exception {
		IngredientCommand command = new IngredientCommand();
		command.setId(10L);
		command.setRecipeId(13L);

		
		
		
		Optional<Recipe> recipeOptional = Optional.of(new Recipe());
		Recipe savedRecipe = new Recipe();
		savedRecipe.addIngredient(new Ingredient());
		savedRecipe.getIngredients().iterator().next().setId(10L);
		
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		when(recipeRepository.save(any())).thenReturn(savedRecipe);
		
		//when
		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
		
		//verify
		assertEquals(command.getId(), savedCommand.getId());
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, times(1)).save(any(Recipe.class));
		

	}
	
	@Test
	public void testSaveNewIngredientCommand() throws Exception {
		
		IngredientCommand newCommand = new IngredientCommand();
		newCommand.setDescription("some description");
		newCommand.setAmount(BigDecimal.valueOf(12));
		UnitOfMeasurementCommand uomCommand = new UnitOfMeasurementCommand();
		uomCommand.setId(1L);
		newCommand.setUom(uomCommand);
		newCommand.setRecipeId(13L);

		Optional<Recipe> recipeOptional = Optional.of(new Recipe());
		Recipe savedRecipe = new Recipe();
		savedRecipe.addIngredient(new Ingredient());
		savedRecipe.getIngredients().iterator().next().setId(10L);
		savedRecipe.getIngredients().iterator().next().setDescription("some description");
		savedRecipe.getIngredients().iterator().next().setAmount(BigDecimal.valueOf(12));
		UnitOfMeasurement uom = new UnitOfMeasurement();
		uom.setId(1L);
		savedRecipe.getIngredients().iterator().next().setUom(uom);
		
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		when(recipeRepository.save(any())).thenReturn(savedRecipe);
		
		IngredientCommand newSavedCommand = ingredientService.saveIngredientCommand(newCommand);

		assertEquals("some description", newSavedCommand.getDescription());
		
	}
	
	@Test
	public void testDeleteById() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		Ingredient ingredient = new Ingredient();
		ingredient.setId(2L);
		recipe.addIngredient(ingredient);
		ingredient.setRecipe(recipe);
		
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		
		ingredientService.deleteById(1L, 2L);
		
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, times(1)).save(any(Recipe.class));
		
	}

}
