package unitTests;

import static org.junit.Assert.*;
import logicClasses.*;
import competitive.*;

import org.junit.Test;
import org.junit.Before;

public class FlightCompetitive_Tests {
	
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
	
	// Testing generate_altitude()

	@Test
	public void generateAltitudeTest1() {
		// Testing the function returns an altitude within a certain range.
		int result = flight1.generateAltitude();
		assertTrue(result >=3000 && result<= 4000);

	}
	
	@Test
	public void checkIfFlightAtWaypointTest1(){
		// Test that waypoint detection works at within 15 pixels.
		Waypoint waypoint = new Waypoint(350, 150, "TEST");
		flight1.setX(350);
		flight1.setY(150);
		assertTrue(flight1.checkIfFlightAtWaypoint(waypoint));
	}
	
	
	@Test
	public void checkIfFlightAtWaypointTest2(){
		// // Test that waypoint detection doesn't work further than 15 pixels away.
		Waypoint waypoint = new Waypoint(350, 150, "TEST");
		flight1.setX(1000);
		flight1.setY(1000);
		assertFalse(flight1.checkIfFlightAtWaypoint(waypoint));
	}
	
	@Test
	public void checkIfFlightAtWaypointTest3(){
		// Test that waypoint detection works when close enough in terms of 
		// Y coordinate but too far away in terms of X cooordinate
		Waypoint waypoint = new Waypoint(350, 150, "TEST");
		flight1.setX(1000);
		flight1.setY(150);
		assertFalse(flight1.checkIfFlightAtWaypoint(waypoint));
	}
	
	@Test
	public void checkIfFlightAtWaypointTest4(){
		// Test that waypoint detection works when close enough in terms of 
		// X coordinate but too far away in terms of Y cooordinate
		Waypoint waypoint = new Waypoint(350, 150, "TEST");
		flight1.setX(350);
		flight1.setY(1000);
		assertFalse(flight1.checkIfFlightAtWaypoint(waypoint));
	}
	
	@Test
	public void turnRightTest1(){
		flight1.setCurrentHeading(180);
		flight1.turnRight();
		assertEquals(183, flight1.getCurrentHeading(), 0);
		
	}
	
	@Test
	public void turnRightTest2(){
		flight1.setCurrentHeading(359);
		flight1.turnRight();
		assertEquals(2, flight1.getCurrentHeading(), 0);
		
	}
	
	@Test
	public void turnLeftTest1(){
		flight1.setCurrentHeading(180);
		flight1.turnLeft();
		assertEquals(177, flight1.getCurrentHeading(), 0);
		
	}
	
	@Test
	public void turnLeftTest2(){
		flight1.setCurrentHeading(0);
		flight1.turnLeft();
		assertEquals(359, flight1.getCurrentHeading(), 0);
		
	}
	
	@Test
	public void landTest1(){
		flight1.setPlayer2(false);
		airspace.getCargo().setCurrentHolder(flight1);
		airspace.getControls().setSelectedFlight1(flight1);
		flight1.setX(600);
		flight1.setY(400);
		flight1.setCurrentHeading(200);
		flight1.setAltitude(2000);
		flight1.land();
		assertTrue(flight1.isLanding());
		assertEquals(flight1.getVelocity(), flight1.getTargetVelocity(), 0);
		assertNull(airspace.getControls().getSelectedFlight1());
	}
	
	@Test
	public void landTest2(){
		flight1.setPlayer2(false);
		airspace.getControls().setSelectedFlight1(flight1);
		flight1.setX(600);
		flight1.setY(400);
		flight1.setCurrentHeading(200);
		flight1.setAltitude(2000);
		flight1.land();
		assertFalse(flight1.isLanding());
		assertNotNull(airspace.getControls().getSelectedFlight1());
	}
	
	@Test
	public void landTest3(){
		flight1.setPlayer2(true);
		airspace.getCargo().setCurrentHolder(flight1);
		airspace.getControls().setSelectedFlight2(flight1);
		flight1.setX(600);
		flight1.setY(400);
		flight1.setCurrentHeading(200);
		flight1.setAltitude(2000);
		flight1.land();
		assertTrue(flight1.isLanding());
		assertEquals(flight1.getVelocity(), flight1.getTargetVelocity(), 0);
		assertNull(airspace.getControls().getSelectedFlight2());
	}
	
	@Test
	public void landTest4(){
		flight1.setPlayer2(true);
		airspace.getControls().setSelectedFlight2(flight1);
		flight1.setX(600);
		flight1.setY(400);
		flight1.setCurrentHeading(200);
		flight1.setAltitude(2000);
		flight1.land();
		assertFalse(flight1.isLanding());
		assertNotNull(airspace.getControls().getSelectedFlight2());
	}
	




}
