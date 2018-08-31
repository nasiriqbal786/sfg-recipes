package com.springframework.sfgrecipes.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.springframework.sfgrecipes.commands.CategoryCommand;
import com.springframework.sfgrecipes.model.Category;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {
	@Nullable
	@Override
	public CategoryCommand convert(Category source) {
		if(source ==  null) {
			return null;
		}
		final CategoryCommand commandCategory = new CategoryCommand();
		commandCategory.setId(source.getId());
		commandCategory.setDescription(source.getDescription());
		
		return commandCategory;
	}

}
