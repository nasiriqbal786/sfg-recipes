package com.springframework.sfgrecipes.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.springframework.sfgrecipes.commands.UnitOfMeasurementCommand;
import com.springframework.sfgrecipes.model.UnitOfMeasurement;

@Component
public class UnitOfMeasurementCommandToUnitOfMeasurement implements Converter<UnitOfMeasurementCommand, UnitOfMeasurement> {

	@Override
	@Nullable
	public UnitOfMeasurement convert(UnitOfMeasurementCommand source) {
		if(source == null) {
			return null;
		}
		UnitOfMeasurement unitOfMeasurement = new UnitOfMeasurement();
		unitOfMeasurement.setId(source.getId());
		unitOfMeasurement.setDescription(source.getDescription());
		return unitOfMeasurement;
	}

}
