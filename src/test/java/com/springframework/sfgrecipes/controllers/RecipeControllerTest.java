package com.springframework.sfgrecipes.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.anyLong;

import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.springframework.sfgrecipes.model.Recipe;
import com.springframework.sfgrecipes.service.RecipeService;

@RunWith(SpringRunner.class)
public class RecipeControllerTest {

	@Mock
	RecipeService recipeService;
	
	RecipeController recipeController;
	
	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		
		recipeController = new RecipeController(recipeService);
	}

	@Test
	public void testShowById() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
		
		when(recipeService.findById(anyLong())).thenReturn(recipe);
		
		mockMvc.perform(get("/recipe/show/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/show"))
			.andExpect(model().attributeExists("recipe"));
	}

}
