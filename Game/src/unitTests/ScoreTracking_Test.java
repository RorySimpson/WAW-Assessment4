package unitTests;

import static org.junit.Assert.*;
import logicClasses.Airspace;
import logicClasses.ScoreTracking;
import logicClasses.Flight;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ScoreTracking_Test {
	private ScoreTracking scoreTrackingInstance;
	private Flight flight1;
	private Airspace airspace;

	@Before
	public void setUp() throws Exception {
		scoreTrackingInstance = new ScoreTracking();
		airspace = new Airspace();
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
		airspace.newEntryPoint(760, 405);
		// Exit Points
		airspace.newExitPoint(800, 0, "1");
		airspace.newExitPoint(150, 200, "2");
		airspace.newExitPoint(1200, 300, "3");
		airspace.newExitPoint(590, 195, "4");
		flight1 = new Flight(airspace);
	}

	@After
	public void tearDown() throws Exception {
		scoreTrackingInstance = null;
		airspace = null;
		flight1 = null;
	}

	// ST.1
	@Test
	public void testUpdateWaypointScore() {
		// Test values for each interval of the way point scoring system
		int closestDistance1 = 7;
		int closestDistance2 = 20;
		int closestDistance3 = 40;

		int waypointScore;

		if (closestDistance1 >= 0 && closestDistance1 <= 14) { // checks to see
																// if the plane
																// is within 14
																// pixels
			waypointScore = 100; // if yes, the score given is 100
									// points
			int actualWaypointScore = scoreTrackingInstance
					.updateWaypointScore(closestDistance1);
			assertEquals(waypointScore, actualWaypointScore);
		}

		if (closestDistance2 >= 15 && closestDistance2 <= 28) {
			waypointScore = 50;
			int actualWaypointScore = scoreTrackingInstance
					.updateWaypointScore(closestDistance2);
			assertEquals(waypointScore, actualWaypointScore);
		}

		if (closestDistance3 >= 29 && closestDistance3 <= 42) {
			waypointScore = 20;
			int actualWaypointScore = scoreTrackingInstance
					.updateWaypointScore(closestDistance3);
			assertEquals(waypointScore, actualWaypointScore);
		}

	}

	// ST.2
	@Test
	public void testUpdateScore() {
		int updatedScore = 50;
		int anotherUpdatedScore = 50;
		int actualUpdatedScore;

		actualUpdatedScore = scoreTrackingInstance.updateScore(updatedScore,
				flight1.isBonus());
		actualUpdatedScore = scoreTrackingInstance.updateScore(
				anotherUpdatedScore, flight1.isBonus());

		assertEquals(100, actualUpdatedScore);
	}

	// ST.3
	@Test
	public void testUpdateTimeScore() {
		int actualUpdateTimeScore = scoreTrackingInstance.updateTimeScore();
		assertEquals(2, actualUpdateTimeScore);
	}

	// ST.4
	@Test
	public void testReduceScoreOnFlightplanChange() {
		int reducedScore = -10;
		int actualReducedScore = scoreTrackingInstance
				.reduceScoreOnFlightplanChange();
		assertEquals(reducedScore, actualReducedScore);
	}

	// ST.5
	@Test
	public void testReduceScoreOnFlightLost() {
		int actualReducedScore = scoreTrackingInstance
				.reduceScoreOnFlightLost();
		assertEquals(-50, actualReducedScore);
	}

	// ST.6
	@Test
	public void testResetScore() {
		scoreTrackingInstance.updateScore(100, flight1.isBonus());
		scoreTrackingInstance.resetScore();
		assertEquals(0, scoreTrackingInstance.getScore());
	}

	// ST.7
	@Test
	public void testGetScore() {
		scoreTrackingInstance.updateScore(100, flight1.isBonus());
		assertEquals(100, scoreTrackingInstance.getScore());
	}

	// ST.8
	@Test
	public void testToString() {
		scoreTrackingInstance.updateScore(100, flight1.isBonus());
		assertEquals("100", scoreTrackingInstance.toString());
	}

	@Test
	public void reduceMultiplierOnFlightLostTest() {

	}

}
