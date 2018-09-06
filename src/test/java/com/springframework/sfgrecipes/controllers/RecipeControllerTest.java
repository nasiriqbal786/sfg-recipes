package com.springframework.sfgrecipes.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.springframework.sfgrecipes.commands.RecipeCommand;
import com.springframework.sfgrecipes.service.RecipeService;

@RunWith(SpringRunner.class)
public class RecipeControllerTest {

	@Mock
	RecipeService recipeService;
	
	RecipeController recipeController;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		
		recipeController = new RecipeController(recipeService);
		
		mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
		
	}

	@Test
	public void testShowById() throws Exception {
		RecipeCommand recipe = new RecipeCommand();
		recipe.setId(1L);
		
		when(recipeService.findRecipeCommandById(anyLong())).thenReturn(recipe);
		
		mockMvc.perform(get("/recipe/1/show"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/show"))
			.andExpect(model().attributeExists("recipe"));
	}
	
	@Test
	public void testNewRecipe() throws Exception {
		
		mockMvc.perform(get("/recipe/new"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/recipeform"))
				.andExpect(model().attributeExists("recipe"));
	}
	
	@Test
	public void testUpdateRecipe() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId(2L);
		
		when(recipeService.findRecipeCommandById(anyLong())).thenReturn(command);
		
		mockMvc.perform(get("/recipe/2/update"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/recipeform"))
				.andExpect(model().attributeExists("recipe"));
	}
	
	@Test
	public void testSaveOrUpdate() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId(2L);
		
		when(recipeService.saveRecipeCommand(any())).thenReturn(command);
		
		mockMvc.perform(post("/recipe")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("id", "")
						.param("description", "some recipe description"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/2/show"));
				
	}
	
	@Test
	public void testDeleteRecipe() throws Exception {
		mockMvc.perform(get("/recipe/1/delete"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/"));
		
		verify(recipeService, times(1)).deleteById(anyLong());
	}

}
