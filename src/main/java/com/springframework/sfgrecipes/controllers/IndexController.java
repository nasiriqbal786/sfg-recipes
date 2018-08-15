package com.springframework.sfgrecipes.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springframework.sfgrecipes.model.Category;
import com.springframework.sfgrecipes.model.UnitOfMeasurement;
import com.springframework.sfgrecipes.model.repositories.CategoryRepository;
import com.springframework.sfgrecipes.model.repositories.UnitOfMeasurementRepository;

@Controller
public class IndexController {

	private CategoryRepository categoryRepository;
	private UnitOfMeasurementRepository unitOfMeasurementRepository;
	
	
	public IndexController(CategoryRepository categoryRepository,
			UnitOfMeasurementRepository unitOfMeasurementRepository) {
		this.categoryRepository = categoryRepository;
		this.unitOfMeasurementRepository = unitOfMeasurementRepository;
	}


	@RequestMapping({"", "/", "/index", "/index.html"})
	public String getIndexPage() {
		Optional<Category> optionalCategory = categoryRepository.findByDescription("American");
		Optional<UnitOfMeasurement> OptionalUOM = unitOfMeasurementRepository.findByDescription("Tablespoon");
		
		System.out.println("Cat ID is: " + optionalCategory.get().getId());
		System.out.println("UOM ID is: " + OptionalUOM.get().getId());
		return "index";
	}
}
