package com.springframework.sfgrecipes.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springframework.sfgrecipes.commands.RecipeCommand;
import com.springframework.sfgrecipes.service.RecipeService;

@Controller
public class RecipeController {

	private final RecipeService recipeService;
	private static final Logger logger = LogManager.getLogger(RecipeController.class);

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@RequestMapping("/recipe/{id}/show")
	public String showById(@PathVariable String id, Model model) {
		
		RecipeCommand command = recipeService.findRecipeCommandById(new Long(id));
		
		model.addAttribute("recipe", command);
		
		logger.info("Finished ShowById for Recipe ID: " + id);
		
		return "recipe/show";
	}
	
	
	@RequestMapping("/recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		RecipeCommand recipe = recipeService.findRecipeCommandById(new Long(id));
		model.addAttribute("recipe", recipe);
		
		if(recipe != null) {
			logger.info("Finished ShowById for Recipe ID: " + id);
		} else {
			System.out.println("Null Recipe for ID: " + id);
		}
		
		return "recipe/recipeform";
	}
	
	@RequestMapping("/recipe/new")
	public String newRecipe(Model model) {
		
		model.addAttribute("recipe", new RecipeCommand());
		
		return "recipe/recipeform";
	}
	
	@PostMapping
	@RequestMapping("/recipe")
	public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
		return "redirect:/recipe/" + savedCommand.getId() + "/show";
	}
	
	@RequestMapping("/recipe/{id}/delete")
	public String deleteRecipe(@PathVariable Long id) {
		
		logger.info("Deleting Recipe - ID: " + id);
		
		recipeService.deleteById(id);
		
		return "redirect:/";
	}
}
