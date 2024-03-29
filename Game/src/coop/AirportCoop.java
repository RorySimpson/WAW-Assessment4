package coop;

import org.newdawn.slick.GameContainer;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import stateContainer.Game;
import logicClasses.Airport;
import logicClasses.Airspace;

public class AirportCoop extends Airport {

	public AirspaceCoop airspace;

	AirportCoop(int airportNumber, AirspaceCoop airspace) {
		super(airportNumber, airspace);
		this.airspace = airspace;
	}

	/**
	 * render: Render all of the graphics for the AirportCoop
	 * 
	 * @param g
	 *            Graphics
	 * @param gc
	 *            GameContainer
	 * 
	 * @throws SlickException
	 */

	@Override
	public void render(Graphics g, GameContainer gc) throws SlickException {

		g.setWorldClip(11, 0, Game.MAXIMUMWIDTH - 11, Game.MAXIMUMHEIGHT - 40);

		getAirportImage().setRotation(getRunwayHeading());

		// Airport image drawn on left side of screen
		if (getAirportNumber() == 1) {
			getAirportImage().drawCentered(1120, 495);
		}

		// Airport image drawn on right side of screen
		else {
			getAirportImage().drawCentered(91, 495);
		}

		if (this.airspace.getControls().getSelectedFlight1() != null) {
			// Landing approach triangle is drawn in selected flight
			// player 1 needs to land
			if (this.airspace.getControls().getSelectedFlight1()
					.getFlightPlan().getCurrentRoute().get(0) == this
					.getBeginningOfRunway()) {
				if (getAirportNumber() == 1)
					getLandingApproachImageRight().drawCentered(900, 495);
				if (getAirportNumber() == 2)
					getLandingApproachImageLeft().drawCentered(311, 495);

			}
		}
		if (this.airspace.getControls().getSelectedFlight2() != null) {
			// Landing approach triangle is drawn in selected flight
			// player 2 needs to land
			if (this.airspace.getControls().getSelectedFlight2()
					.getFlightPlan().getCurrentRoute().get(0) == this
					.getBeginningOfRunway()) {
				if (getAirportNumber() == 1)
					getLandingApproachImageRight().drawCentered(900, 495);
				if (getAirportNumber() == 2)
					getLandingApproachImageLeft().drawCentered(311, 495);

			}
		}

		g.setWorldClip(0, 0, Game.MAXIMUMWIDTH, Game.MAXIMUMHEIGHT);

	}

}
