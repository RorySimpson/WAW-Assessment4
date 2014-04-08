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

public class Volcano_Tests {
	
	private Volcano volcano;
	private VolcanoProjectile projectile;

	@Before
	public void setUp(){
		
		volcano = new Volcano();
		projectile = new VolcanoProjectile();
		volcano.getListOfProjectilesLaunched().add(projectile);
	}
	
	@Test
	public void checkIfProjectileHasLeftAirspaceTest(){
		projectile.setX(600);
		projectile.setY(300);
		assertFalse(volcano.checkIfProjectileHasLeftAirspace(projectile));
		
		projectile.setX(1500);
		projectile.setY(1500);
		assertTrue(volcano.checkIfProjectileHasLeftAirspace(projectile));
		
	}
}
