package com.springframework.sfgrecipes.controllers;


import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.springframework.sfgrecipes.commands.IngredientCommand;
import com.springframework.sfgrecipes.commands.RecipeCommand;
import com.springframework.sfgrecipes.service.IngredientService;
import com.springframework.sfgrecipes.service.RecipeService;
import com.springframework.sfgrecipes.service.UnitOfMeasurementService;

public class IngredientsControllerTest {

	@Mock
	RecipeService recipeService;
	
	@Mock
	IngredientService ingredientService;
	
	@Mock
	UnitOfMeasurementService uomService;
	
	IngredientsController ingredientsController;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		ingredientsController = new IngredientsController(recipeService, ingredientService, uomService);
		
		mockMvc = MockMvcBuilders.standaloneSetup(ingredientsController).build();
	}

	@Test
	public void testListIngredients() throws Exception {
		//given
		RecipeCommand recipeCommand = new RecipeCommand();
		when(recipeService.findRecipeCommandById(anyLong())).thenReturn(recipeCommand);
		
		//when
		mockMvc.perform(get("/recipe/1/ingredients"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/list"))
				.andExpect(model().attributeExists("recipe"));
		
		//then
		verify(recipeService, times(1)).findRecipeCommandById(anyLong());
	}
	
	@Test
	public void testShowIngredient() throws Exception {
		//given
		IngredientCommand ingredientCommand = new IngredientCommand();
		
		//when
		when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
		
		//then
		mockMvc.perform(get("/recipe/2/ingredient/3/show"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/show"))
				.andExpect(model().attributeExists("ingredient"));
	}

}
