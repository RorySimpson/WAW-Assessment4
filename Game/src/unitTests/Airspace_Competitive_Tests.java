package unitTests;

import static org.junit.Assert.*;
import logicClasses.*;
import competitive.*;

import org.junit.Test;
import org.junit.Before;

public class Airspace_Competitive_Tests {
	
	private AirspaceCompetitive airspace;
	private  Flight flight1;
	
	@Before
	public void setUp() throws Exception {
    	airspace = new AirspaceCompetitive();
    	//Adding EntryPoints
    	airspace.newEntryPoint(1200, 100);
    	airspace.newEntryPoint(11, 150);

    	// Get a Flight
    	flight1 = new FlightCompetitive(airspace, true);
    	airspace.createAndSetSeparationRules();

		
	}
	
	@Test
	public void resetAirspaceTest(){
		airspace.resetAirspace();
		assertEquals(0, airspace.getListOfFlights().size());
		assertEquals(0, airspace.getListOfFlightsPlayer1().size());
		assertEquals(0, airspace.getListOfFlightsPlayer2().size());
		assertEquals(0, airspace.getNumberOfGameLoopsSinceLastFlightAdded());
		assertEquals(0, airspace.getNumberOfGameLoops());
		assertEquals(3600, airspace.getNumberOfGameLoopsWhenDifficultyIncreases());
		assertEquals(300, airspace.getNumberOfGameLoopsSinceLastPlayer1FlightAdded());
		assertEquals(300, airspace.getNumberOfGameLoopsSinceLastPlayer2FlightAdded());
		assertFalse(airspace.getSeparationRules().getGameOverViolation());
		assertNull(airspace.getControls().getSelectedFlight1());
		assertNull(airspace.getControls().getSelectedFlight2());
		assertNull(airspace.getCargo().getCurrentHolder());
		assertEquals(0, airspace.getPlayer1Score());
		assertEquals(0, airspace.getPlayer2Score());
		
		
	}
	
	@Test
	public void createAndSetSeparationRulesTest(){
		airspace.setSeparationRules(null);
		airspace.createAndSetSeparationRules();
		assertNotNull(airspace.getSeparationRules());
	}
	
	@Test
	public void checkWhatPlayerNeedsFlightTest(){
		airspace.getListOfFlightsPlayer1().add(new FlightCompetitive(airspace, true));
	}

}
