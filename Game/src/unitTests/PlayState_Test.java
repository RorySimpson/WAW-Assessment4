package unitTests;

import static org.junit.Assert.*;
import logicClasses.Airspace;

import org.junit.Before;
import org.junit.Test;

import states.PlayState;

public class PlayState_Test {
	private Airspace airspaceInstance;
	private PlayState playStateInstance;

	@Before
	public void setup() {
		int playState = 2;
		playStateInstance = new PlayState(playState);
		airspaceInstance = new Airspace();
	}

	@Test
	public void testGetID() {
		int actualID = playStateInstance.getID();
		assertEquals(2, actualID);
	}

	// This test also tests getAirspace
	@Test
	public void testSetAirspace() {
		Airspace airspace = new Airspace();
		playStateInstance.setAirspace(airspaceInstance);

		Airspace actualAirspace = playStateInstance.getAirspace();
		assertEquals(airspace.toString(), actualAirspace.toString());
	}

}
