package com.springframework.sfgrecipes.controllers;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springframework.sfgrecipes.SfgRecipesAppApplication;
import com.springframework.sfgrecipes.model.Recipe;
import com.springframework.sfgrecipes.service.RecipeService;

@Controller
public class IndexController {

	private static final Logger logger = LogManager.getLogger(SfgRecipesAppApplication.class);

	private final RecipeService recipeService;
	
	public IndexController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	@RequestMapping({"", "/", "/index", "/index.html"})
	public String getIndexPage(Model model) {
		Set<Recipe> recipes = recipeService.getRecipes();
		model.addAttribute("recipes", recipes);
		
		logger.info("Total " + recipes.size() + " recipes found.");

		return "index";
	}
}
