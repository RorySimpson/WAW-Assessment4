package unitTests;

import static org.junit.Assert.*;
import logicClasses.*;
import competitive.*;

import org.junit.Test;
import org.junit.Before;

public class AirspaceCompetitive_Tests {
	
	private AirspaceCompetitive airspace;
	private  FlightCompetitive flight1;
	
	
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
	public void checkWhatPlayerNeedsFlightTest1(){
		airspace.getListOfFlightsPlayer2().add(new FlightCompetitive(airspace, true));
		airspace.checkWhatPlayerNeedsFlight();
		assertTrue(airspace.isAddPlayer1FlightNext());
	}
	
	@Test
	public void checkWhatPlayerNeedsFlightTest2(){
		airspace.getListOfFlightsPlayer1().add(new FlightCompetitive(airspace, true));
		airspace.checkWhatPlayerNeedsFlight();
		assertFalse(airspace.isAddPlayer1FlightNext());
	}
	
	@Test
	public void checkWhatPlayerNeedsFlightTest3(){
		airspace.checkWhatPlayerNeedsFlight();
		assertTrue(airspace.isAddPlayer1FlightNext());
	}
	
	@Test 
	public void updateScoreTest1(){
		flight1.setPlayer2(true);
		airspace.updateScore(flight1);
		assertEquals(1, airspace.getPlayer2Score());
		assertEquals(0, airspace.getPlayer1Score());
		
	}
	
	@Test 
	public void updateScoreTest2(){
		flight1.setPlayer2(false);
		airspace.updateScore(flight1);
		assertEquals(0, airspace.getPlayer2Score());
		assertEquals(1, airspace.getPlayer1Score());
		
	}
	
	@Test
	public void throwbackIntoAirspaceTest(){
		
		flight1.setTargetHeading(0);
		airspace.throwbackIntoAirspace(flight1);
		assertTrue(flight1.getTargetHeading() >135 && flight1.getTargetHeading() < 225);
	}
	
	@Test 
	public void removeSpecificFlightTest1(){
		assertEquals(0, airspace.getListOfFlightsPlayer2().size());
		airspace.getListOfFlightsPlayer2().add(flight1);
		airspace.getListOfFlights().add(flight1);
		assertEquals(1, airspace.getListOfFlightsPlayer2().size());
		airspace.removeSpecificFlight(0);
		assertEquals(0, airspace.getListOfFlightsPlayer2().size());
		
		
	}

}
