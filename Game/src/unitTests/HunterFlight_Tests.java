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

public class HunterFlight_Tests {

	Airspace airspace;
	Flight flight;
	HunterFlight hunterFlight;
	
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
    	
		hunterFlight = new HunterFlight(airspace);
		flight = new Flight(airspace);
	}
	
	@Test
	public void generateVictimTest(){
		
		airspace.addFlight(flight);
		assertFalse(hunterFlight.getHasVictim());
		hunterFlight.generateVictim(airspace);
		assertTrue(hunterFlight.getHasVictim());
	}
	
	@Test
	public void generateXTest(){
		int value = hunterFlight.generateX();
		assertTrue(value == 0 || value == 1200);
	}
	
	@Test
	public void calculateHeadingToVictimTest(){
		
		//Test: Angle > 0
		airspace.addFlight(flight);
		hunterFlight.generateVictim(airspace);
		hunterFlight.setTargetHeading(0);
		double originalTargetHeading = hunterFlight.getTargetHeading();
		hunterFlight.setX(600);
		hunterFlight.setY(300);
		flight.setX(650);
		flight.setY(350);
		hunterFlight.calculateHeadingToVictim();
		assertTrue(originalTargetHeading != hunterFlight.getTargetHeading());
		
		//Test: Angle < 0
		hunterFlight.setTargetHeading(0);
		originalTargetHeading = hunterFlight.getTargetHeading();
		flight.setX(450);
		flight.setY(250);
		hunterFlight.calculateHeadingToVictim();
		assertTrue(originalTargetHeading != hunterFlight.getTargetHeading());
	}
	
	@Test
	public void updateXYCoordinatesTest(){
		double originalX = hunterFlight.getX();
		double originalY = hunterFlight.getY();
		hunterFlight.updateXYCoordinates();
		assertTrue(originalX != hunterFlight.getX() && originalY != hunterFlight.getY());
	}
	
	@Test 
	public void inAirspaceFalseTest(){
		hunterFlight.setX(1500);
		hunterFlight.setY(1500);
		assertFalse(hunterFlight.inAirspace());
	}
	
	@Test 
	public void inAirspaceTrueTest(){
		hunterFlight.setX(600);
		hunterFlight.setY(300);
		assertTrue(hunterFlight.inAirspace());
	}
	
	@Test
	public void updateCurrentHeadingTest(){
		
		airspace.addFlight(flight);
		hunterFlight.generateVictim(airspace);
		hunterFlight.setTargetHeading(0);
		hunterFlight.setCurrentHeading(0);
		hunterFlight.setX(600);
		hunterFlight.setY(300);
		flight.setX(600);
		flight.setY(550);
		hunterFlight.updateCurrentHeading();
		assertFalse(hunterFlight.getTurningRight());
		
	
		hunterFlight.setTargetHeading(0);
		hunterFlight.setCurrentHeading(0);
		flight.setX(350);
		flight.setY(450);
		hunterFlight.updateCurrentHeading();
		assertFalse(hunterFlight.getTurningRight());
		
		hunterFlight.setTargetHeading(0);
		hunterFlight.setCurrentHeading(0);
		flight.setX(250);
		flight.setY(250);
		hunterFlight.updateCurrentHeading();
		assertFalse(hunterFlight.getTurningRight());
		
		hunterFlight.setTargetHeading(0);
		hunterFlight.setCurrentHeading(0);
		flight.setX(650);
		flight.setY(450);
		hunterFlight.updateCurrentHeading();
		assertFalse(hunterFlight.getTurningRight());
		
		hunterFlight.setTargetHeading(0);
		hunterFlight.setCurrentHeading(0);
		flight.setX(650);
		flight.setY(650);
		hunterFlight.updateCurrentHeading();
		assertFalse(hunterFlight.getTurningRight());
	}
}
