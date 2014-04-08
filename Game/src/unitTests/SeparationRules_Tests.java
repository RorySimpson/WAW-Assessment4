package unitTests;

import static org.junit.Assert.*;
import logicClasses.*;
import events.*;

import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

public class SeparationRules_Tests {

	private Airspace airspace;
	private SeparationRules separationRules;
	private Flight flight1;
	private Flight flight2;
	private HunterFlight hunterFlight;
	private VolcanoProjectile projectile;
	private Tornado tornado;
	int originalAltitude;
	double originalHeading;
	
	@Before
	public void setUp(){
		
		separationRules = new SeparationRules(1);
		
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
    	airspace.newExitPoint(800, 0, "1");
    	airspace.newExitPoint(150, 200, "2");
    	airspace.newExitPoint(1200, 300, "3");
    	
		flight1 = new Flight(airspace);
		flight2 = new Flight(airspace);
		
		hunterFlight = new HunterFlight(airspace);
		
		projectile = new VolcanoProjectile();
		
		airspace.getEventController().getVolcano().getListOfProjectilesLaunched().add(projectile);
		
		tornado = new Tornado(airspace, airspace.getEventController());
		
		airspace.addFlight(flight1);
		airspace.addFlight(flight2);
		
		flight1.setX(0);
		flight2.setX(25);
		
		flight1.setY(0);
		flight2.setY(25);
		
		flight1.setAltitude(27000);
		flight2.setAltitude(27000);
		
	}
	
	//Test: lateralDistanceBetweenFlightAndTornado
	@Test
	public void lateralDistanceBetweenFlightAndTornadoTest(){
		assertTrue(separationRules.lateralDistanceBetweenFlightAndTornado(flight1, tornado) >= 0);
	}
	
	//Test: lateralDistanceBetweenFlightAndProjectile
	@Test
	public void lateralDistanceBetweenFlightAndProjectileTest(){
		assertTrue(separationRules.lateralDistanceBetweenFlightAndProjectile(flight1, projectile) >= 0);
	}
	
	//Test: lateralDistanceBetweenFlightAndHunterFlights
	@Test
	public void lateralDistanceBetweenFlightAndHunterFlightsTest(){
		assertTrue(separationRules.lateralDistanceBetweenFlightAndHunterFlight(flight1, hunterFlight) >= 0);
	}
	
	//Test: lateralDistanceBetweenFlights
	@Test
	public void lateralDistanceBetweenFLightsTest() {
		assertTrue(separationRules.lateralDistanceBetweenFlights(flight1, flight2) >= 0);
	}
	
	//Test: verticalDistanceBetweenFlights
	@Test
	public void verticalDistanceBetweenFlightsTest(){
		assertTrue(separationRules.verticalDistanceBetweenFlights(flight1, flight2) >= 0);
	}
	
	//Test: checkViolation for collisions between flights
	@Test
	public void checkViolationTrueTest(){
		// Tests that game over is achieved when two flights are too close.
		flight1.setX(1);
		flight2.setX(1);
		flight1.setY(1);
		flight2.setY(1);
		
		separationRules.checkFlightOnFlightViolation(airspace);
		assertTrue(separationRules.getGameOverViolation());
	}
	
	@Test
	public void checkViolationFalseVerticalTest(){
		// Tests that game over is not achieved when two flights aren't too close.
		flight1.setX(1);
		flight2.setX(1);
		flight1.setY(1000);
		flight2.setY(5000);
		
		separationRules.checkFlightOnFlightViolation(airspace);
		assertFalse(separationRules.getGameOverViolation());
	}
	
	@Test
	public void checkViolationFalseLateralTest(){
		// Tests that game over is not achieved when two flights aren't too close.
		flight1.setX(1000);
		flight2.setX(1);
		flight1.setY(1000);
		flight2.setY(1000);
		
		separationRules.checkFlightOnFlightViolation(airspace);
		assertFalse(separationRules.getGameOverViolation());
	}
	
	//Test: checkTornadoOnFlightCollision for collisions between flights and tornados
	
	@Test
	public void checkTornadoOnFlightCollisionTrueTest(){
		// Test that random heading gave when flight and tornado are too close
		flight1.setX(1);
		flight1.setY(1);
		tornado.setX(1);
		tornado.setY(1);
		
		separationRules.checkTornadoOnFlightCollision(airspace);
		assertTrue(flight1.getAltitude() >= 2000 && flight1.getAltitude() <= 5000
				   && flight1.getCurrentHeading() >= 0 && flight1.getCurrentHeading() <= 360);
	}
	
	@Test
	public void checkTornadoOnFlightCollisionFalseTest(){
		// Test that random heading gave when flight and tornado are too close
		flight1.setX(1);
		flight1.setY(1);
		tornado.setX(600);
		tornado.setY(300);
		
		originalAltitude = flight1.getAltitude();
		originalHeading = flight1.getCurrentHeading();
		
		separationRules.checkTornadoOnFlightCollision(airspace);
		assertTrue(originalAltitude == flight1.getAltitude() && originalHeading == flight1.getCurrentHeading());
	}
	
	//Test: checkVolcanoProjectileOnFlightCollision for collisions between flights and projectiles
	
	@Test
	public void checkVolcanoProjectileOnFlightCollisionTrueTest(){
		// Test that gameOverViolation set to true when flight and projectile are too close
		flight1.setX(1);
		flight1.setY(1);
		projectile.setX(1);
		projectile.setY(1);
		
		separationRules.checkVolcanoProjectileOnFlightCollision(airspace);
		assertTrue(separationRules.getGameOverViolation());
	}
		
	@Test
	public void checkVolcanoProjectileOnFlightCollisionFalseTest(){
		// Test that gameOverViolation set to false when flight and projectile aren't too close
		flight1.setX(1);
		flight1.setY(1);
		projectile.setX(600);
		projectile.setY(300);
		
		separationRules.checkVolcanoProjectileOnFlightCollision(airspace);
		assertFalse(separationRules.getGameOverViolation());
	}
	
	
}
