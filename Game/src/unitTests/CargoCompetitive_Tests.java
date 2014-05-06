package unitTests;


import static org.junit.Assert.*;
import competitive.*;

import org.junit.Test;
import org.junit.Before;

public class CargoCompetitive_Tests {
	
	private CargoCompetitive cargo;

	@Before
	public void setUp() throws Exception {
    	cargo = new CargoCompetitive();
	}
	
	// CC.1
	@Test
	public void generateRandomCargoLocationTest(){
		cargo.setLocation(cargo.generateRandomCargoLocation());
		assertTrue(cargo.getLocation().getX() >= 50 && cargo.getLocation().getX() <= 1150 &&
					cargo.getLocation().getY() >= 50 && cargo.getLocation().getY() <= 500);
	}
	
	

}
