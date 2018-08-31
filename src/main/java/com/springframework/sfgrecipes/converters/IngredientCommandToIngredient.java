package com.springframework.sfgrecipes.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.springframework.sfgrecipes.commands.IngredientCommand;
import com.springframework.sfgrecipes.model.Ingredient;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

	private final UnitOfMeasurementCommandToUnitOfMeasurement uomConverter;
	public IngredientCommandToIngredient(UnitOfMeasurementCommandToUnitOfMeasurement uomConverter) {
		this.uomConverter = uomConverter;
	}
	@Nullable
	@Override
	public Ingredient convert(IngredientCommand source) {
		if(source == null) {
			return null;
		}
		Ingredient ingredient = new Ingredient();
		ingredient.setId(source.getId());
		ingredient.setDescription(source.getDescription());
		ingredient.setAmount(source.getAmount());
		ingredient.setUom(uomConverter.convert(source.getUom()));
		return ingredient;
	}

}
