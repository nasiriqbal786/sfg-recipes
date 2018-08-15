package com.springframework.sfgrecipes.model.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.springframework.sfgrecipes.model.UnitOfMeasurement;

public interface UnitOfMeasurementRepository extends CrudRepository<UnitOfMeasurement, Long> {

	Optional<UnitOfMeasurement> findByDescription(String description);
}
