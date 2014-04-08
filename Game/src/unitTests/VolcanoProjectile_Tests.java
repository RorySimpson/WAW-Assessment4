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

public class VolcanoProjectile_Tests {
	
	private VolcanoProjectile projectile;
	
	@Before
	public void setUp(){
		projectile = new VolcanoProjectile();
	}
	
	@Test
	public void generateRandomHeadingTest(){
		int heading = projectile.generateRandomHeading();
		assertTrue(heading >= 0 && heading < 360);
	}
	
	@Test
	public void updateAltitudeTest(){
		
		projectile.setCurrentAltitude(5000);
		projectile.updateAltitude();
		assertTrue(projectile.getTargetAltitude() == 100);
		
		projectile.setCurrentAltitude(0);
		projectile.setTargetAltitude(5000);
		projectile.updateAltitude();
		assertTrue(projectile.getCurrentAltitude() > 0);
	}
	
	@Test
	public void updateXYCoordinatesTest(){
		
		double originalX = projectile.getX();
		double originalY = projectile.getY();
		projectile.updateXYCoordinates();
		assertTrue(originalX != projectile.getX() && originalY != projectile.getY());
	}
	
	

}
