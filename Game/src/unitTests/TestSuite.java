package unitTests;




import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({Airspace_Tests.class, Controls_Tests.class, DeferredFile_Test.class,
					Flight_Tests.class, FlightMenu_Tests.class, FlightPlan_Tests.class,
					PlayState_Test.class, ScoreTracking_Test.class, SeparationRules_Tests.class,
						
						})
public class TestSuite{
	// Runs all tests
}

