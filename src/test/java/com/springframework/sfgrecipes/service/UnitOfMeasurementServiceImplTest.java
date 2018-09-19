package com.springframework.sfgrecipes.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.springframework.sfgrecipes.commands.UnitOfMeasurementCommand;
import com.springframework.sfgrecipes.converters.UnitOfMeasurementToUnitOfMeasurementCommand;
import com.springframework.sfgrecipes.model.UnitOfMeasurement;
import com.springframework.sfgrecipes.model.repositories.UnitOfMeasurementRepository;

public class UnitOfMeasurementServiceImplTest {

	UnitOfMeasurementToUnitOfMeasurementCommand uomToUomCommand = new UnitOfMeasurementToUnitOfMeasurementCommand();
	UnitOfMeasurementService service;
	
	@Mock
	UnitOfMeasurementRepository uomRepository;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		service = new UnitOfMeasurementServiceImpl(uomRepository, uomToUomCommand);
	}

	@Test
	public void testListAllUoms() {
		//given
		Set<UnitOfMeasurement> uoms = new HashSet<>();
		UnitOfMeasurement uom1 = new UnitOfMeasurement();
		uom1.setId(1L);
		uoms.add(uom1);
		
		UnitOfMeasurement uom2 = new UnitOfMeasurement();
		uom2.setId(1L);
		uoms.add(uom2);
		
		when(uomRepository.findAll()).thenReturn(uoms);
		
		//when
		Set<UnitOfMeasurementCommand> uomCommands = service.listAllUoms();
		
		//then
		assertEquals(2,  uomCommands.size());
		verify(uomRepository, times(1)).findAll();
		
	}

}
