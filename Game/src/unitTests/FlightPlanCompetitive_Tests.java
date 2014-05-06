package unitTests;

import static org.junit.Assert.*;
import logicClasses.*;
import competitive.*;

import org.junit.Test;
import org.junit.Before;

public class FlightPlanCompetitive_Tests {

	private AirspaceCompetitive airspace;
	private FlightCompetitive flight1;

	@Before
	public void setUp() throws Exception {
		airspace = new AirspaceCompetitive();
		// Adding EntryPoints
		airspace.newEntryPoint(1200, 100);
		airspace.newEntryPoint(11, 150);

		// Get a Flight
		flight1 = new FlightCompetitive(airspace, true);
		airspace.createAndSetSeparationRules();

	}

	// FPC.1
	@Test
	public void assignEntryPointTest1() {
		flight1.setPlayer2(true);
		flight1.getFlightPlan().assignEntryPoint(airspace);
		assertTrue(airspace.getListOfEntryPoints().get(0) == flight1
				.getFlightPlan().getEntryPoint());
		flight1.setPlayer2(false);
		flight1.getFlightPlan().assignEntryPoint(airspace);
		assertTrue(airspace.getListOfEntryPoints().get(1) == flight1
				.getFlightPlan().getEntryPoint());

	}

}
