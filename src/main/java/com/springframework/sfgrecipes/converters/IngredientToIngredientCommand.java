package com.springframework.sfgrecipes.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.springframework.sfgrecipes.commands.IngredientCommand;
import com.springframework.sfgrecipes.model.Ingredient;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

	private final UnitOfMeasurementToUnitOfMeasurementCommand uomConverter;
	
	public IngredientToIngredientCommand(UnitOfMeasurementToUnitOfMeasurementCommand uomConverter) {
		this.uomConverter = uomConverter;
	}

	@Override
	@Nullable
	public IngredientCommand convert(Ingredient source) {
		if(source == null) {
			return null;
		}
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(source.getId());
		if(source.getRecipe() != null) {
			ingredientCommand.setRecipeId(source.getRecipe().getId());
		}
		ingredientCommand.setDescription(source.getDescription());
		ingredientCommand.setAmount(source.getAmount());
		ingredientCommand.setUom(uomConverter.convert(source.getUom()));
		return ingredientCommand;
	}

}
