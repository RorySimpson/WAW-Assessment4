package unitTests;

import static org.junit.Assert.*;
import logicClasses.Flight;
import coop.AirspaceCoop;
import coop.FlightCoop;

import org.junit.Test;
import org.junit.Before;

public class AirspaceCoop_Tests {

	private AirspaceCoop airspace;

	@Before
	public void setUp() {
		airspace = new AirspaceCoop();

		airspace.createAndSetSeparationRules();

		airspace.newWaypoint(350, 150, "A");
		airspace.newWaypoint(400, 470, "B");
		airspace.newWaypoint(700, 60, "C");
		airspace.newWaypoint(800, 320, "D");
		airspace.newWaypoint(600, 418, "E");
		airspace.newWaypoint(500, 220, "F");
		airspace.newWaypoint(950, 188, "G");
		airspace.newWaypoint(1050, 272, "H");
		airspace.newWaypoint(900, 420, "I");
		airspace.newWaypoint(240, 250, "J");
		// EntryPoints
		airspace.newEntryPoint(150, 400);
		airspace.newEntryPoint(1200, 200);
		airspace.newEntryPoint(600, 0);
		// Exit Points
		airspace.newExitPoint(800, 0, "1");
		airspace.newExitPoint(150, 200, "2");
		airspace.newExitPoint(1200, 300, "3");
		FlightCoop flight = new FlightCoop(airspace);
		airspace.addFlight(flight);
	}

	// ACO.1
	@Test
	public void newWaypointTest() {
		assertTrue(airspace.newWaypoint(151, 500, "TEST"));
		assertFalse(airspace.newWaypoint(-10000, 151, "TEST2"));
	}

	// ACO.2
	@Test
	public void resetAirspaceTest() {
		airspace.resetAirspace();
		assertEquals(0, airspace.getListOfFlights().size());
		assertEquals(0, airspace.getListOfFlightsPlayer1().size());
		assertEquals(0, airspace.getListOfFlightsPlayer2().size());
		assertEquals(0, airspace.getNumberOfGameLoopsSinceLastFlightAdded());
		assertEquals(0, airspace.getNumberOfGameLoops());
		assertEquals(3600,
				airspace.getNumberOfGameLoopsWhenDifficultyIncreases());
		assertEquals(0, airspace.getNumberOfGameLoopsSinceLastFlightAdded());
		assertFalse(airspace.getSeparationRules().getGameOverViolation());
		assertNull(airspace.getControls().getSelectedFlight1());
		assertNull(airspace.getControls().getSelectedFlight2());

	}

	// ACO.3
	@Test
	public void testRemoveSpecificFlight() {
		airspace.removeSpecificFlight(0);
		assertTrue(airspace.getListOfFlights().isEmpty());
	}

}
