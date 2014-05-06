package logicClasses;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.geom.*;

import stateContainer.Game;
import util.DeferredFile;

public class Airport {

	// FIELDS
	/* Image for the airport */
	protected static Image airportImage;

	/* Name is requested by Eclipse, but no used practically */
	protected String airportName = "Nothing";

	/* Airport coordinates */
	protected float x, y;
	/* The heading of the runway */
	protected float runwayHeading;
	/* For multiple airports */
	protected int runwayLength, airportNumber;
	/* Whether it has a plane landing at that moment */
	protected boolean flightLanding;
	/* Whether it has a plane waiting to take off */
	protected Flight planeWaitingtoTakeoff;
	/* The waypoint that corresponds to the beginning of the runway */
	protected ExitPoint beginningOfRunway;
	/* The waypoint that corresponds to the end of the runway */
	protected EntryPoint endOfRunway;
	/* The landing area */
	protected Polygon landingApproachArea;
	/* Airspace instance */
	protected Airspace airspace;
	/* The 2 polygons for both airports */
	protected Image landingApproachImageRight, landingApproachImageLeft;

	/**
	 * Constructor that creates the required objects
	 * 
	 * @param airportNumber
	 *            Which airport (1 for right airport, 2 for left airport)
	 * @param airspace
	 *            The airspace
	 */
	public Airport(int airportNumber, Airspace airspace) {

		// Placing the airport off screen as the airport graphics are
		// part of the graphics already
		this.airspace = airspace;
		this.airportNumber = airportNumber;

		// Airport status
		this.flightLanding = false;
		this.planeWaitingtoTakeoff = null;

		// Creating the runway waypoints
		/* If it's the right airport */
		if (this.airportNumber == 1) {

			runwayHeading = 270;
			/* The runway points */
			this.beginningOfRunway = new ExitPoint(1060, 495, "APR");
			this.endOfRunway = new EntryPoint(1180, 495);

			// Creating the landing area. This is the triangle that
			// appears
			// when a flight needs to land. It
			// is used to check whether the flights have the right
			// approach.
			landingApproachArea = new Polygon();
			landingApproachArea.addPoint(1025, 495);
			landingApproachArea.addPoint(775, 405);
			landingApproachArea.addPoint(775, 585);
		}

		/* If it's the left airport */
		else if (this.airportNumber == 2) {
			runwayHeading = 90;
			this.beginningOfRunway = new ExitPoint(151, 495, "APL");
			this.endOfRunway = new EntryPoint(31, 495);

			// Creating the landing area. This is the triangle that
			// appears when a flight needs to land. It
			// is used to check whether the flights have the right
			// approach.
			landingApproachArea = new Polygon();
			landingApproachArea.addPoint(184, 495);
			landingApproachArea.addPoint(434, 405);
			landingApproachArea.addPoint(434, 585);
		}
	}

	// SETTERS AND GETTERS

	public ExitPoint getBeginningOfRunway() {
		return beginningOfRunway;
	}

	public void setBeginningOfRunway(ExitPoint beginningOfRunway) {
		this.beginningOfRunway = beginningOfRunway;
	}

	public EntryPoint getEndOfRunway() {
		return endOfRunway;
	}

	public void setEndOfRunway(EntryPoint endOfRunway) {
		this.endOfRunway = endOfRunway;
	}

	/**
	 * Initialises the game resources
	 * 
	 * @param gc
	 *            the game container
	 * @throws SlickException
	 */
	public void init(GameContainer gc) throws SlickException {
		/* Deferred loading of the airport for better performances */
		LoadingList.get().add(new DeferredFile("res/graphics/new/airport.png") {
			public void loadFile(String filename) throws SlickException {
				airportImage = new Image(filename);

				/* Put the airport in the middle */
				x = (stateContainer.Game.MAXIMUMWIDTH) / 2;
				y = (stateContainer.Game.MAXIMUMHEIGHT) / 2;
				runwayLength = airportImage.getHeight();

			}
		});

		/* The airport indicators */
		LoadingList.get()
				.add(new DeferredFile(
						"res/graphics/new/airspaceIndicatorGreen.png") {
					public void loadFile(String filename) throws SlickException {
						landingApproachImageRight = new Image(filename);

					}
				});

		LoadingList.get().add(
				new DeferredFile(
						"res/graphics/new/airspaceIndicatorGreenLeft.png") {
					public void loadFile(String filename) throws SlickException {
						landingApproachImageLeft = new Image(filename);

					}
				});
	}

	/**
	 * The render method to show our resources
	 * 
	 * @param g
	 *            Slick graphcs
	 * @param gc
	 *            the game container
	 * @throws SlickException
	 */
	public void render(Graphics g, GameContainer gc) throws SlickException {

		/*
		 * Sets the rendering space so planes do not render over the bottom bar
		 */
		g.setWorldClip(11, 0, Game.MAXIMUMWIDTH - 11, Game.MAXIMUMHEIGHT - 40);

		/* Draws the airport images on both sides */
		airportImage.setRotation(runwayHeading);
		if (airportNumber == 1) {
			airportImage.drawCentered(1120, 495);
		} else {
			airportImage.drawCentered(91, 495);
		}

		/* Draws the landing area if the selected plane needs landing */
		if (this.airspace.getControls().getSelectedFlight() != null) {
			if (this.airspace.getControls().getSelectedFlight().getFlightPlan()
					.getCurrentRoute().get(0) == this.beginningOfRunway) {
				if (airportNumber == 1)
					landingApproachImageRight.drawCentered(900, 495);
				if (airportNumber == 2)
					landingApproachImageLeft.drawCentered(311, 495);
			}
		}

		/* Sets the rendering space */
		g.setWorldClip(0, 0, Game.MAXIMUMWIDTH, Game.MAXIMUMHEIGHT);

	}

	/**
	 * Adds the airport name
	 */
	@Override
	public String toString() {
		String s = "Airport Name: " + airportName;
		return s;
	}

	/* Setters and Getters */

	public float getRunwayHeading() {
		return runwayHeading;
	}

	public int getRunwayLength() {
		return runwayLength - 10;
	}

	public boolean isFlightLanding() {
		return flightLanding;
	}

	public void setFlightLanding(boolean flightLanding) {
		this.flightLanding = flightLanding;
	}

	public Flight getPlaneWaitingtoTakeoff() {
		return planeWaitingtoTakeoff;
	}

	public void setPlaneWaitingtoTakeoff(Flight planeWaitingtoTakeoff) {
		this.planeWaitingtoTakeoff = planeWaitingtoTakeoff;
	}

	public Polygon getLandingApproachArea() {
		return landingApproachArea;
	}

	public void setLandingApproachArea(Polygon landingApproachArea) {
		this.landingApproachArea = landingApproachArea;
	}

	public Image getLandingApproachImage() {
		return landingApproachImageRight;
	}

	public void setLandingApproachImage(Image landingApproachImage) {
		this.landingApproachImageRight = landingApproachImage;
	}

	public static Image getAirportImage() {
		return airportImage;
	}

	public static void setAirportImage(Image airportImage) {
		Airport.airportImage = airportImage;
	}

	public int getAirportNumber() {
		return airportNumber;
	}

	public void setAirportNumber(int airportNumber) {
		this.airportNumber = airportNumber;
	}

	public Airspace getAirspace() {
		return airspace;
	}

	public void setAirspace(Airspace airspace) {
		this.airspace = airspace;
	}

	public Image getLandingApproachImageRight() {
		return landingApproachImageRight;
	}

	public void setLandingApproachImageRight(Image landingApproachImageRight) {
		this.landingApproachImageRight = landingApproachImageRight;
	}

	public Image getLandingApproachImageLeft() {
		return landingApproachImageLeft;
	}

	public void setLandingApproachImageLeft(Image landingApproachImageLeft) {
		this.landingApproachImageLeft = landingApproachImageLeft;
	}
}
