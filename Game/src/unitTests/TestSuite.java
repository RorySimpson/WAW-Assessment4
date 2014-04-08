package unitTests;




import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({Airport_Tests.class, Airspace_Tests.class, Controls_Tests.class, DeferredFile_Test.class,
					EventController_Tests.class, Flight_Tests.class, FlightMenu_Tests.class,
					FlightPlan_Tests.class, HoverImage_Test.class, HunterFlight_Tests.class, 
					PlayState_Test.class, ScoreTracking_Test.class, SeparationRules_Tests.class,
					Tornado_Tests.class, Volcano_Tests.class, VolcanoProjectile_Tests.class, 				
					})

public class TestSuite{
	// Runs all tests
}

