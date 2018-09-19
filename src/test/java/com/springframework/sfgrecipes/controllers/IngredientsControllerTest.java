package com.springframework.sfgrecipes.controllers;


import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
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
	
	@Test
	public void testUpdateIngredientForm() throws Exception {
		//given
		IngredientCommand command = new IngredientCommand();
		
		//when
		when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(command);
		when(uomService.listAllUoms()).thenReturn(new HashSet<>());
		
		//then
		mockMvc.perform(get("/recipe/1/ingredient/2/update"))
				.andExpect(status().isOk())
				.andExpect(view().name("/recipe/ingredient/ingredientform"))
				.andExpect(model().attributeExists("ingredient"))
				.andExpect(model().attributeExists("uomList"));
			
	}
	
	@Test
	public void testSaveOrUpdate() throws Exception {
		//given
		IngredientCommand command = new IngredientCommand();
		command.setId(3L);
		command.setRecipeId(2L);
		
		//when
		when(ingredientService.saveIngredientCommand(any())).thenReturn(command);
		
		//then
		mockMvc.perform(post("/recipe/2/ingredient")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "")
				.param("desscription", "some string"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));
				
	}
	
	@Test
	public void testNewRecipeIngredient() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(2L);
		
		when(recipeService.findRecipeCommandById(anyLong())).thenReturn(recipeCommand);
		when(uomService.listAllUoms()).thenReturn(new HashSet<>());
		
		mockMvc.perform(get("/recipe/2/ingredient/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("/recipe/ingredient/ingredientform"))
			.andExpect(model().attributeExists("ingredient"))
			.andExpect(model().attributeExists("uomList"));
		
		verify(recipeService, times(1)).findRecipeCommandById(anyLong());
		verify(uomService, times(1)).listAllUoms();
	}
	
	@Test
	public void testDeleteIngredientById() throws Exception {
		mockMvc.perform(get("/recipe/1/ingredient/2/delete"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/1/ingredients/"));
		
		verify(ingredientService, times(1)).deleteById(anyLong(), anyLong());
		
	}

}
