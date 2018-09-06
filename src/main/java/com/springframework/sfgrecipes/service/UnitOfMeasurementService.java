package com.springframework.sfgrecipes.service;

import java.util.Set;

import com.springframework.sfgrecipes.commands.UnitOfMeasurementCommand;

public interface UnitOfMeasurementService {

	Set<UnitOfMeasurementCommand> listAllUoms();
}
