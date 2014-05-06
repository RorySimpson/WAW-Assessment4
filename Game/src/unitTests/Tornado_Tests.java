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

public class Tornado_Tests {

	private Airspace airspace;
	private Tornado tornado;

	@Before
	public void setUp() {
		airspace = new Airspace();
		tornado = new Tornado(airspace);
	}

	// T.1
	@Test
	public void randomiseLocationTest() {
		tornado.setOriginalX(600);
		tornado.setOriginalY(300);
		double originalXVal = tornado.getOriginalX();
		double originalYVal = tornado.getOriginalY();
		tornado.randomiseLocation();
		assertTrue(tornado.getOriginalX() != originalXVal
				&& tornado.getOriginalY() != originalYVal);
	}

	// T.2
	@Test
	public void attackTest() {
		tornado.attack();
		assertTrue(tornado.getAttacking());
	}

	// T.3
	@Test
	public void inAirspaceFalseTest() {
		tornado.setX(1500);
		tornado.setY(1500);
		assertFalse(tornado.inAirspace());
	}

	// T.4
	@Test
	public void inAirspaceTrueTest() {
		tornado.setX(600);
		tornado.setY(300);
		assertTrue(tornado.inAirspace());
	}

	// T.5
	@Test
	public void updateXYTest() {
		double originalX = tornado.getX();
		double originalY = tornado.getY();
		tornado.updateXY();
		assertTrue(originalX != tornado.getX() && originalY != tornado.getY());
	}

}
