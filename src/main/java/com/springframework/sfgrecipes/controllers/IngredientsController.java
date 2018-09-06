package com.springframework.sfgrecipes.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springframework.sfgrecipes.commands.RecipeCommand;
import com.springframework.sfgrecipes.service.IngredientService;
import com.springframework.sfgrecipes.service.RecipeService;
import com.springframework.sfgrecipes.service.UnitOfMeasurementService;

@Controller
public class IngredientsController {

	private final RecipeService recipeService;
	
	private final IngredientService ingredientService;
	
	private final UnitOfMeasurementService unitOfMeasurementService;
	
	private static final Logger logger = LogManager.getLogger(IngredientsController.class);

	public IngredientsController(RecipeService recipeService, IngredientService ingredientService,
			UnitOfMeasurementService unitOfMeasurementService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.unitOfMeasurementService = unitOfMeasurementService;
	}
	
	@RequestMapping("/recipe/{recipeid}/ingredients")
	public String listIngredients(@PathVariable String recipeid, Model model) {
		
		RecipeCommand recipeCommand = recipeService.findRecipeCommandById(Long.valueOf(recipeid));
		model.addAttribute("recipe", recipeCommand);
		
		logger.info("listing ingredients for recipe:: " + recipeCommand.getDescription());
		return "recipe/ingredient/list";
	}
	
	@GetMapping
	@RequestMapping("/recipe/{recipeId}/ingredient/{ignredientId}/show")
	public String showIngredient(@PathVariable String recipeId, 
									@PathVariable String ignredientId, Model model) {
		model.addAttribute("ingredient", 
				ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), 
						Long.valueOf(ignredientId)));
		return "recipe/ingredient/show";
	}
	
	@RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
	public String updateRecipeIngredient(@PathVariable String recipeId,
										@PathVariable String ingredientId,
										Model model) {
		model.addAttribute("ingredient", 
				ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), 
						Long.valueOf(ingredientId)));
		model.addAttribute("uomList", unitOfMeasurementService.listAllUoms());
		return "/recipe/ingredient/ingredientform";
	}

}
