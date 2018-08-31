package com.springframework.sfgrecipes.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.springframework.sfgrecipes.commands.RecipeCommand;
import com.springframework.sfgrecipes.model.Recipe;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

	private final NotesCommandToNotes notesConverter;
	private final CategoryCommandToCategory categoryConverter;
	private final IngredientCommandToIngredient ingredientConverter;
	
	public RecipeCommandToRecipe(NotesCommandToNotes notesConverter, CategoryCommandToCategory categoryConverter,
			IngredientCommandToIngredient ingredientConverter) {
		this.notesConverter = notesConverter;
		this.categoryConverter = categoryConverter;
		this.ingredientConverter = ingredientConverter;
	}

	@Override
	@Nullable
	public Recipe convert(RecipeCommand source) {
		if(source == null) {
			return null;
		}
		Recipe recipe = new Recipe();
		recipe.setId(source.getId());
		recipe.setCookTime(source.getCookTime());
		recipe.setDescription(source.getDescription());
		recipe.setDifficulty(source.getDifficulty());
		recipe.setDirections(source.getDirections());
		recipe.setImage(source.getImage());
		recipe.setPrepTime(source.getPrepTime());
		recipe.setServings(source.getServings());
		recipe.setSource(source.getSource());
		recipe.setUrl(source.getUrl());
		if(source.getCategories() != null && source.getCategories().size() > 0) {
			source.getCategories().forEach(category -> recipe.getCategories().add(categoryConverter.convert(category)));
		}
		if(source.getIngredients() != null && source.getIngredients().size() > 0) {
			source.getIngredients().forEach(ingredient -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));
		}
		
		recipe.setNotes(notesConverter.convert(source.getNotes()));
		return recipe;
	}

}
