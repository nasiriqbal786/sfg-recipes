package com.springframework.sfgrecipes.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.springframework.sfgrecipes.commands.UnitOfMeasurementCommand;
import com.springframework.sfgrecipes.model.UnitOfMeasurement;

@Component
public class UnitOfMeasurementToUnitOfMeasurementCommand implements Converter<UnitOfMeasurement, UnitOfMeasurementCommand> {

	@Override
	@Nullable
	public UnitOfMeasurementCommand convert(UnitOfMeasurement source) {
		if(source == null) {
			return null;
		}
		UnitOfMeasurementCommand unitOfMeasurementCommand = new UnitOfMeasurementCommand();
		unitOfMeasurementCommand.setId(source.getId());
		unitOfMeasurementCommand.setDescription(source.getDescription());
		return unitOfMeasurementCommand;
	}

}
