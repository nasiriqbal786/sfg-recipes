package com.springframework.sfgrecipes.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.springframework.sfgrecipes.commands.UnitOfMeasurementCommand;
import com.springframework.sfgrecipes.converters.UnitOfMeasurementToUnitOfMeasurementCommand;
import com.springframework.sfgrecipes.model.repositories.UnitOfMeasurementRepository;

@Service
public class UnitOfMeasurementServiceImpl implements UnitOfMeasurementService {
	
	private static final Logger logger = LogManager.getLogger(UnitOfMeasurementServiceImpl.class);

	private final UnitOfMeasurementRepository uomRepository;
	private final UnitOfMeasurementToUnitOfMeasurementCommand uomToUomCommand;
	
	public UnitOfMeasurementServiceImpl(UnitOfMeasurementRepository uomRepository,
			UnitOfMeasurementToUnitOfMeasurementCommand uomToUomCommand) {
		this.uomRepository = uomRepository;
		this.uomToUomCommand = uomToUomCommand;
	}
	@Override
	public Set<UnitOfMeasurementCommand> listAllUoms() {
		Set<UnitOfMeasurementCommand> uoms = new HashSet<>();
		uomRepository.findAll().forEach(uom -> {
				logger.info("getting all UOMs :: UOM id:: " + uom.getId());
				uoms.add(uomToUomCommand.convert(uom));
			});
		logger.info("Uom set size is:: " + uoms.size());
		return uoms;
	}

}
