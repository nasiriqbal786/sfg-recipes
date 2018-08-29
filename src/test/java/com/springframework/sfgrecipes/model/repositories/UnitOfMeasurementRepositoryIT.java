package com.springframework.sfgrecipes.model.repositories;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.springframework.sfgrecipes.model.UnitOfMeasurement;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasurementRepositoryIT {

	@Autowired
	UnitOfMeasurementRepository unitOfMeasurementRepository;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	@DirtiesContext
	public void testFindByDescription() {
		Optional<UnitOfMeasurement> uomOptional = unitOfMeasurementRepository.findByDescription("Teaspoon");
		assertEquals("Teaspoon", uomOptional.get().getDescription());
	}
	
	@Test
	public void testFindByDescriptionCup() {
		Optional<UnitOfMeasurement> uomOptional = unitOfMeasurementRepository.findByDescription("Cup");
		assertEquals("Cup", uomOptional.get().getDescription());
	}


}
