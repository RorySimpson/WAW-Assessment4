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
	private FlightCoop flight1;
	private FlightCoop flight2;

	@Before
	public void setUp() {
		airspace = new AirspaceCoop();
		// Adding EntryPoints
		airspace.newEntryPoint(1200, 100);
		airspace.newEntryPoint(11, 150);

		// Get a Flight
		flight1 = new FlightCoop(airspace);
		airspace.createAndSetSeparationRules();
		flight2 = new FlightCoop(airspace);
		airspace.addFlight(flight1);
		airspace.getControls().setSelectedFlight1(flight2);
	}

	// FCO.1.1
	@Test
	public void landTest1() {
		flight1.setPlayer2(false);
		airspace.getControls().setSelectedFlight1(flight1);
		flight1.getFlightPlan().getCurrentRoute()
				.add(airspace.getAirportRight().getBeginningOfRunway());
		flight1.setX(800);
		flight1.setY(500);
		flight1.setCurrentHeading(90);
		flight1.setAltitude(2000);
		flight1.land();
		assertTrue(flight1.isLanding());
		assertEquals(flight1.getVelocity(), flight1.getTargetVelocity(), 0);
		assertNull(airspace.getControls().getSelectedFlight1());
	}

	// FCO.1.2
	@Test
	public void landTest2() {
		flight1.setPlayer2(false);
		flight1.getFlightPlan().getCurrentRoute()
				.add(airspace.getAirportRight().getBeginningOfRunway());
		airspace.getControls().setSelectedFlight1(flight1);
		flight1.setX(600);
		flight1.setY(400);
		flight1.setCurrentHeading(200);
		flight1.setAltitude(2000);
		flight1.land();
		assertFalse(flight1.isLanding());
		assertNotNull(airspace.getControls().getSelectedFlight1());
	}

	// FCO.1.3
	@Test
	public void landTest3() {
		flight1.setPlayer2(true);
		airspace.getControls().setSelectedFlight2(flight1);
		flight1.getFlightPlan().getCurrentRoute()
				.add(airspace.getAirportLeft().getBeginningOfRunway());
		flight1.setX(250);
		flight1.setY(500);
		flight1.setCurrentHeading(270);
		flight1.setAltitude(2000);
		flight1.land();
		assertTrue(flight1.isLanding());
		assertEquals(flight1.getVelocity(), flight1.getTargetVelocity(), 0);
		assertNull(airspace.getControls().getSelectedFlight2());
	}

	// FCO.1.4
	@Test
	public void landTest4() {
		flight1.setPlayer2(true);
		flight1.getFlightPlan().getCurrentRoute()
				.add(airspace.getAirportLeft().getBeginningOfRunway());
		airspace.getControls().setSelectedFlight2(flight1);
		flight1.setX(600);
		flight1.setY(400);
		flight1.setCurrentHeading(200);
		flight1.setAltitude(2000);
		flight1.land();
		assertFalse(flight1.isLanding());
		assertNotNull(airspace.getControls().getSelectedFlight2());
	}

	// FCO.2
	@Test
	public void testTakeOff() {
		flight1.setTakingOff(false);
		flight1.takeOff();
		assertTrue(flight1.getVelocity() == 100);
		assertTrue(flight1.getTargetVelocity() == 300);
		assertTrue(flight1.getTargetAltitude() == 2000);
	}

}
