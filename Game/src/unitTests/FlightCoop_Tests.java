package unitTests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import competitive.AirspaceCompetitive;
import competitive.FlightCompetitive;
import coop.AirspaceCoop;
import coop.FlightCoop;
public class FlightCoop_Tests {
	
	private AirspaceCoop airspace;
	
	@Before
	public void setUp() {
		airspace = new AirspaceCoop();
    	//Adding EntryPoints
    	airspace.newEntryPoint(1200, 100);
    	airspace.newEntryPoint(11, 150);

    	// Get a Flight
    	FlightCoop flight = new FlightCoop(airspace);
    	airspace.createAndSetSeparationRules();
	}
	
	@Test
	public void testLand() {
		
	}
	
	@Test
	public void testTakeOff() {
		
	}

}
