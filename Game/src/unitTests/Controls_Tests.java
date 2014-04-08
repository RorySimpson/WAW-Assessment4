package unitTests;

import static org.junit.Assert.*;

import java.awt.Font;

import logicClasses.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;

import stateContainer.Game;

public class Controls_Tests {
	
	private Airspace airspace;
	private Controls controls;
	private Flight flight;

	@Before
	public void Setup(){
		
    	airspace = new Airspace();
    	airspace.newWaypoint(350, 150, "A");
    	airspace.newWaypoint(400, 470, "B");
    	airspace.newWaypoint(700, 60,  "C");
    	airspace.newWaypoint(800, 320, "D");
    	airspace.newWaypoint(600, 418, "E");
    	airspace.newWaypoint(500, 220, "F");
    	airspace.newWaypoint(950, 188, "G");
    	airspace.newWaypoint(1050, 272,"H");
    	airspace.newWaypoint(900, 420, "I");
    	airspace.newWaypoint(240, 250, "J");
    	airspace.newEntryPoint(150, 400);
    	airspace.newEntryPoint(1200, 200);
    	airspace.newEntryPoint(600, 0);
    	airspace.newEntryPoint(760, 405);
    	airspace.newExitPoint(800, 0, "1");
    	airspace.newExitPoint(150, 200, "2");
    	airspace.newExitPoint(1200, 300, "3");
    	airspace.newExitPoint(590,195,"4");
    	
		flight = new Flight(airspace);
		
		controls = airspace.getControls();

	}

	@Test
	public void testChangeModeByClickingOnFlight() {
		airspace.getControls().setSelectedFlight(flight);
		controls.getSelectedFlight().getFlightPlan().setChangingPlan(true);
		controls.changeModeByClickingOnFlight();
		assertTrue(controls.getSelectedFlight().getFlightPlan().getChangingPlan());
	}

	@Test
	public void testCheckSelected() {
		fail("Not yet implemented");
	}

	@Test
	public void testGiveHeadingWithMouse() {
		fail("Not yet implemented");
	}
	
	@Test // This also tests 'setSelectedFlight()'
	// 		 No need to repeat test!
	public void testGetSelectedFlight() throws SlickException {			
		Airspace newAirspace = new Airspace();
		newAirspace.addEntryPoint(new EntryPoint(10, 10));
		newAirspace.addEntryPoint(new EntryPoint(20, 20));
		newAirspace.addEntryPoint(new EntryPoint(20, 0));
		newAirspace.addEntryPoint(new EntryPoint(0, 20));
		Flight newFlight = new Flight(newAirspace);
		
		controls.setSelectedFlight(newFlight);
			
		assertEquals(newFlight, controls.getSelectedFlight());
	}

	@Test
	public void testSetDifficultyValueOfGame() {		
		Airspace airspace = new Airspace();
		airspace.setDifficultyValueOfGame(1);
		airspace.getControls().setDifficultyValueOfGame(1);// Set the difficulty of the airspace so can retrieve it
				
		int actualDifficulty = airspace.getDifficultyValueOfGame();
		
		assertEquals(1, actualDifficulty);
	}
	
	@After
	public void tearDown(){
		controls = null;
	}

}

