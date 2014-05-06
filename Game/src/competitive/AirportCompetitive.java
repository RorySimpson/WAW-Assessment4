package competitive;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.loading.LoadingList;

import util.DeferredFile;
import coop.ControlsCoop;
import logicClasses.Airport;
import logicClasses.Airspace;
import logicClasses.EntryPoint;
import logicClasses.ExitPoint;

public class AirportCompetitive extends Airport {

	private Image landingApproachImage;
	private AirspaceCompetitive airspace;

	/**
	 * Constructor to load initial values
	 * 
	 * @param airportNumber
	 * @param airspace
	 */
	AirportCompetitive(int airportNumber, AirspaceCompetitive airspace) {
		super(airportNumber, airspace);
		this.airspace = airspace;
		/* The only exit point in competitive is the airport landing */
		this.beginningOfRunway = new ExitPoint(620, 505, "APR");
		this.endOfRunway = new EntryPoint(620, 555);
		landingApproachArea = new Polygon();
		landingApproachArea.addPoint(620, 465);
		landingApproachArea.addPoint(675, 315);
		landingApproachArea.addPoint(565, 315);
	}

	/**
	 * init: Initialises all the resources required for the airport class, and
	 * any other classes that are rendered within it
	 * 
	 * @param gc
	 *            GameContainer
	 * @throws SlickException
	 */

	@Override
	public void init(GameContainer gc) throws SlickException {
		LoadingList.get().add(new DeferredFile("res/graphics/new/airport.png") {
			public void loadFile(String filename) throws SlickException {
				airportImage = new Image(filename);

				/* Airport in the middle of the screen */
				x = (stateContainer.Game.MAXIMUMWIDTH) / 2;
				y = stateContainer.Game.MAXIMUMHEIGHT / 2;
				runwayLength = airportImage.getHeight();

			}
		});

		LoadingList.get().add(
				new DeferredFile(
						"res/graphics/new/airspaceIndicatorCompetitive.png") {
					public void loadFile(String filename) throws SlickException {
						landingApproachImage = new Image(filename);
					}
				});
	}

	/**
	 * render: Render all of the graphics for the airport
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

		getAirportImage().setRotation(0);

		// Airport image centred in middle of airspace
		getAirportImage().drawCentered(620, 600);

		// Draw landing approach if player 1's selected flight has the
		// cargo
		if ((this.airspace.getControls().getSelectedFlight1() != null)
				&& this.airspace.getCargo().getCurrentHolder() != null) {
			if (this.airspace.getControls().getSelectedFlight1() == this.airspace
					.getCargo().getCurrentHolder()) {
				landingApproachImage.drawCentered(620, 390);

			}
		}

		// Draw landing approach if player 2's selected flight has the
		// cargo
		if ((this.airspace.getControls().getSelectedFlight2() != null)
				&& this.airspace.getCargo().getCurrentHolder() != null) {
			if (this.airspace.getControls().getSelectedFlight2() == this.airspace
					.getCargo().getCurrentHolder()) {
				landingApproachImage.drawCentered(620, 390);
			}
		}
	}

}