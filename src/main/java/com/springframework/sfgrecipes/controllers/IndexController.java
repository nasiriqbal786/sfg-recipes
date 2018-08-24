package com.springframework.sfgrecipes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springframework.sfgrecipes.service.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IndexController {

	private final RecipeService recipeService;
	
	public IndexController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	@RequestMapping({"", "/", "/index", "/index.html"})
	public String getIndexPage(Model model) {
		
		log.info("Accessing index controller...");
		
		model.addAttribute("recipes", recipeService.getRecipes());

		return "index";
	}
}
