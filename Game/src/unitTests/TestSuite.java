package unitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ Airspace_Tests.class, Controls_Tests.class,
		DeferredFile_Test.class, EventController_Tests.class,
		Flight_Tests.class, FlightPlan_Tests.class, HunterFlight_Tests.class,
		PlayState_Test.class, ScoreTracking_Test.class,
		SeparationRules_Tests.class, Tornado_Tests.class, Volcano_Tests.class,
		VolcanoProjectile_Tests.class, AirspaceCompetitive_Tests.class,
		CargoCompetitive_Tests.class, FlightCompetitive_Tests.class,
		FlightPlanCompetitive_Tests.class,
		SeparationRulesCompetitive_Tests.class })
public class TestSuite {
	// Runs all tests
}
