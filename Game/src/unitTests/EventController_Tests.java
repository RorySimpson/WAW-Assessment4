package unitTests;

import static org.junit.Assert.*;

import java.awt.Font;

import logicClasses.*;
import events.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;

import stateContainer.Game;

public class EventController_Tests {
	
	private Airspace airspace;
	private Flight flight;
	private EventController eventController;
	
	@Before
	public void setUp(){
		
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
		eventController = new EventController(airspace);
	}
	
	@Test
	public void newRadioMalfunctionTimeTest(){
		int time = eventController.newRadioMalfunctionTime();
		assertTrue(time >= 7200 && time < 21999);
	}

	@Test
	public void newHunterFlightTimeTest(){
		int time = eventController.newHunterFlightTime();
		assertTrue(time >= 7200 && time < 21599);
	}
	
	@Test
	public void updateRadioMalfunctionEventTest(){
		eventController.setTimeUntilRadioMalfunction(1);
		eventController.updateRadioMalfunctionEvent();
		int time = eventController.getTimeUntilRadioMalfunction();
		assertTrue(time >= 7200 && time < 21999);
		
		airspace.addFlight(flight);
		eventController.setTimeUntilRadioMalfunction(1);
		eventController.updateRadioMalfunctionEvent();
		
		
	}
	
	@Test
	public void newTornadoTimeTest(){
		int time = eventController.newTornadoTime();
		assertTrue(1800 <= time && time < 5400);
	}
	
	@Test
	public void spawnHunterFlightTest(){
		eventController.setNextHunterFlightTime(1);
		try {
			assertTrue(eventController.spawnHunterFlight());
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		eventController.setNextHunterFlightTime(2);
		try {
			assertFalse(eventController.spawnHunterFlight());
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void spawnTornadoTest(){
		eventController.setNextTornadoTime(1);
		try {
			assertTrue(eventController.spawnTornado());
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		eventController.setNextTornadoTime(2);
		try {
			assertFalse(eventController.spawnTornado());
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
