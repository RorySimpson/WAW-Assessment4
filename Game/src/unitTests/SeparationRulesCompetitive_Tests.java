package unitTests;

import static org.junit.Assert.*;
import logicClasses.*;
import competitive.*;

import org.junit.Test;
import org.junit.Before;

public class SeparationRulesCompetitive_Tests {
	

	private AirspaceCompetitive airspace;
	private  FlightCompetitive flight1, flight2;
	
	
	@Before
	public void setUp() throws Exception {
    	airspace = new AirspaceCompetitive();
    	//Adding EntryPoints
    	airspace.newEntryPoint(1200, 100);
    	airspace.newEntryPoint(11, 150);

    	// Create Two Flights Flight
    	flight1 = new FlightCompetitive(airspace, true);
    	flight2 = new FlightCompetitive(airspace, true);
    	airspace.createAndSetSeparationRules();
    	airspace.getListOfFlights().add(flight1);
    	airspace.getListOfFlights().add(flight2);

		
	}
	
	@Test
	public void lateralDistanceBetweenFlightAndCargoTest1(){
		airspace.getCargo().getLocation().setLocation(0,0);
		flight1.setX(4);
		flight1.setY(3);
		assertEquals(5, airspace.getSeparationRules().lateralDistanceBetweenFlightAndCargo(flight1, airspace.getCargo()), 0);
		
	}
	
	@Test
	public void detectCargoPickUpTest1(){
		airspace.getCargo().getLocation().setLocation(0,0);
		flight1.setX(4);
		flight1.setY(3);
		flight2.setX(500);
		flight2.setY(500);
		airspace.getSeparationRules().detectCargoPickUp(airspace);
		assertTrue(airspace.getCargo().getCurrentHolder() == flight1);
		
	}
	
	@Test
	public void detectCargoPickUpTest2(){
		airspace.getCargo().getLocation().setLocation(0,0);
		flight1.setX(500);
		flight1.setY(500);
		flight2.setX(500);
		flight2.setY(500);
		airspace.getSeparationRules().detectCargoPickUp(airspace);
		assertNull(airspace.getCargo().getCurrentHolder());
		
	}
	
	@Test
	public void detectCargoPickUpTest3(){
		airspace.getCargo().getLocation().setLocation(0,0);
		flight1.setX(4);
		flight1.setY(3);
		flight2.setX(500);
		flight2.setY(500);
		airspace.getSeparationRules().detectCargoPickUp(airspace);
		assertTrue(airspace.getCargo().getCurrentHolder() == flight1);
		flight1.setX(500);
		flight1.setY(500);
		flight2.setX(4);
		flight2.setY(3);
		airspace.getSeparationRules().detectCargoPickUp(airspace);
		assertTrue(airspace.getCargo().getCurrentHolder() == flight1);
		
	}
	
	//Test: checkViolation for collisions between flights
	@Test
	public void checkViolationTest1(){
		// Tests that game over is achieved when two flights are too close.
		flight1.setX(1);
		flight2.setX(1);
		flight1.setY(1);
		flight2.setY(1);
		flight1.setCurrentAltitude(3000);
		flight2.setCurrentAltitude(3000);
		
		airspace.getSeparationRules().checkFlightOnFlightViolation(airspace);
		assertTrue(airspace.getSeparationRules().getGameOverViolation());
	}
	
	@Test
	public void checkViolationTest2(){
		// Tests that game over is not achieved when two flights aren't too close.
		flight1.setX(1);
		flight2.setX(1);
		flight1.setY(1000);
		flight2.setY(5000);
		flight1.setCurrentAltitude(4000);
		flight2.setCurrentAltitude(3000);
		
		airspace.getSeparationRules().checkFlightOnFlightViolation(airspace);
		assertFalse(airspace.getSeparationRules().getGameOverViolation());
	}
	
	@Test
	public void checkViolationTest3(){
		// Tests that game over is not achieved when two flights aren't too close.
		flight1.setX(1000);
		flight2.setX(1);
		flight1.setY(1000);
		flight2.setY(1000);
		flight1.setCurrentAltitude(3000);
		flight2.setCurrentAltitude(3000);
		
		airspace.getSeparationRules().checkFlightOnFlightViolation(airspace);
		assertFalse(airspace.getSeparationRules().getGameOverViolation());

	}
	
	@Test
	public void checkViolationTest4(){
		
		flight1.setX(1000);
		flight2.setX(1000);
		flight1.setY(1000);
		flight2.setY(1000);
		flight1.setCurrentAltitude(3000);
		flight2.setCurrentAltitude(3000);
		airspace.getCargo().setCurrentHolder(flight1);
		
		airspace.getSeparationRules().checkFlightOnFlightViolation(airspace);
		assertTrue(airspace.getSeparationRules().getGameOverViolation());
		assertNull(airspace.getCargo().getCurrentHolder());
		assertEquals(flight1.getX(), airspace.getCargo().getLocation().getX(), 0);
		assertEquals(flight1.getY(), airspace.getCargo().getLocation().getY(), 0);
	}
	
	@Test
	public void removeCrashTest1(){
		
		// Checks that a crash can be added and removed correctly
		
		flight1.setPlayer2(false);
		flight2.setPlayer2(true);
		airspace.getControls().setSelectedFlight1(flight1);
		airspace.getControls().setSelectedFlight2(flight2);
		flight1.setX(1000);
		flight2.setX(1000);
		flight1.setY(1000);
		flight2.setY(1000);
		flight1.setCurrentAltitude(3000);
		flight2.setCurrentAltitude(3000);
		airspace.getSeparationRules().checkFlightOnFlightViolation(airspace);
		airspace.getSeparationRules().removeCrash(airspace, airspace.getSeparationRules().getListOfActiveCrashes().get(0));
		assertFalse(airspace.getListOfFlights().contains(flight1));
		assertFalse(airspace.getListOfFlights().contains(flight2));
		assertFalse(airspace.getListOfFlightsPlayer1().contains(flight1));
		assertFalse(airspace.getListOfFlightsPlayer2().contains(flight2));
		assertNull(airspace.getControls().getSelectedFlight1());
		assertNull(airspace.getControls().getSelectedFlight2());
	}
	
	
	
	
	
	
	

}
